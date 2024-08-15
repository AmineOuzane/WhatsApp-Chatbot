package com.example.chatbotwhatsapp.service.serviceImpl;

import com.example.chatbotwhatsapp.dtos.OpportuniteDTO;
import com.example.chatbotwhatsapp.dtos.UtilisateurDTO;
import com.example.chatbotwhatsapp.service.MessageIdMappingService;
import com.example.chatbotwhatsapp.service.OpportuniteService;
import com.example.chatbotwhatsapp.service.UtilisateurService;
import com.example.chatbotwhatsapp.service.ValidateOpportunity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service

public  class ValidateOpportunityImpl implements ValidateOpportunity {

    @Value("${whatsapp.api.url}")
    private String whatsappApiUrl;

    @Value("${whatsapp.api.token}")
    private String whatsappApiToken;

    @Value("${recipient_number}")
    private String recipientNumber;

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private OpportuniteService opportuniteService;
    @Autowired
    private MessageIdMappingService messageIdMappingService;

    private final Map<String, Integer> messageIdToOpportunityIdMap = new ConcurrentHashMap<>();


    @Override
    public ResponseEntity<String> validateOpportunity(Integer opportuniteId, String phoneNumber) {
        String formattedPhoneNumber = phoneNumber.startsWith("+") ? phoneNumber : ("+" + phoneNumber);
        UtilisateurDTO user = utilisateurService.getUtilisateurByTelephone(formattedPhoneNumber);
        if (user == null) {
            System.err.println("User not found for phone number: " + formattedPhoneNumber);
            return ResponseEntity.notFound().build();
        }
        System.out.println("Retrieved user info: " + user);

        OpportuniteDTO opportunite = opportuniteService.getOpportuniteById(opportuniteId);
        String statutString = opportunite.getStatut() != null ? opportunite.getStatut().toString() : "NULL";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + whatsappApiToken);

        // Create the request body JSON object
        JSONObject requestBody = new JSONObject();
        requestBody.put("messaging_product", "whatsapp");
        requestBody.put("to", phoneNumber);
        requestBody.put("type", "template");

        // Create the template JSON object
        JSONObject template = new JSONObject();
        template.put("name", "valider_opportunite");
        template.put("language", new JSONObject().put("code", "en"));

        // Create the components JSON array
        JSONArray components = new JSONArray();

        // Body Component
        JSONObject bodyComponent = new JSONObject();
        bodyComponent.put("type", "body");
        bodyComponent.put("parameters", new JSONArray()
                .put(new JSONObject().put("type", "text").put("text", String.valueOf(opportunite.getId())))
                .put(new JSONObject().put("type", "text").put("text", opportunite.getNom()))
                .put(new JSONObject().put("type", "text").put("text", opportunite.getDescription()))
                .put(new JSONObject().put("type", "text").put("text", opportunite.getClient()))
                .put(new JSONObject().put("type", "text").put("text", opportunite.getDate().toString()))
                .put(new JSONObject().put("type", "text").put("text", opportunite.getCommercial().getNom()))
                .put(new JSONObject().put("type", "text").put("text", statutString)));
        components.put(bodyComponent);

        // Create the button components
        JSONArray buttons = new JSONArray();

        // Approve Button
        JSONObject approveButton = new JSONObject();
        approveButton.put("type", "button");
        approveButton.put("button", new JSONObject()
                .put("title", "Approuver")
                .put("payload", "APPROVE_" + opportuniteId));
        buttons.put(approveButton);

        // Reject Button
        JSONObject rejectButton = new JSONObject();
        rejectButton.put("type", "button_reply");
        rejectButton.put("button_reply", new JSONObject()
                .put("id", "REJECT_" + opportuniteId)
                .put("title", "Rejeter"));
        buttons.put(rejectButton);

        // Add the components to the template
        template.put("components", components);

        // Add the template to the request body
        requestBody.put("template", template);

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(whatsappApiUrl, request, String.class);


        String messageId = extractContextIdFromResponse(response.getBody());
        System.out.println("Extracted Message ID: " + messageId);
        if (messageId != null) {
            System.out.println("Storing mapping: Message ID = " + messageId + ", Opportunity ID = " + opportuniteId);
            messageIdMappingService.storeMapping(messageId, opportuniteId); // Store mapping
            System.out.println("Map size after storing: " + messageIdMappingService.getMapSize());

        } else {
            System.out.println("Storing mapping: Message ID is null!");
        }
        return response;
    }

    @Override
    public String extractMessageIdFromResponse(String jsonResponse) {
        System.out.println("Received JSON response: " + jsonResponse);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // Check if 'entry' array is present and not empty
            if (rootNode.has("entry") && rootNode.get("entry").isArray() && !rootNode.get("entry").isEmpty()) {
                JsonNode entryNode = rootNode.get("entry").get(0);
                System.out.println("Entry node found: " + entryNode.toString());

                // Check if 'changes' array is present and not empty
                if (entryNode.has("changes") && entryNode.get("changes").isArray() && !entryNode.get("changes").isEmpty()) {
                    JsonNode changesNode = entryNode.get("changes").get(0);
                    System.out.println("Changes node found: " + changesNode.toString());

                    // Check if 'value' object is present
                    if (changesNode.has("value")) {
                        JsonNode valueNode = changesNode.get("value");
                        System.out.println("Value node found: " + valueNode.toString());

                        // Check if 'messages' array is present and not empty
                        if (valueNode.has("messages") && valueNode.get("messages").isArray() && !valueNode.get("messages").isEmpty()) {
                            JsonNode messagesNode = valueNode.get("messages").get(0);
                            System.out.println("Messages node found: " + messagesNode.toString());

                            // Extract the current message ID
                            String messageId = messagesNode.path("id").asText();
                            if (!messageId.isEmpty()) {
                                System.out.println("Extracted message ID: " + messageId);
                                return messageId;
                            } else {
                                System.out.println("Message ID is empty in the JSON response.");
                                return null;
                            }
                        } else {
                            System.out.println("No messages found in the JSON response or messages array is empty.");
                        }
                    } else {
                        System.out.println("No 'value' object found in the JSON response.");
                    }
                } else {
                    System.out.println("No 'changes' array found in the JSON response or changes array is empty.");
                }
            } else {
                System.out.println("No 'entry' array found in the JSON response or entry array is empty.");
            }
            return null;
        } catch (JsonProcessingException e) {
            System.out.println("Error parsing JSON response: " + e.getMessage());
            return null;
        }
    }

        @Override
        public String extractContextIdFromResponse(String jsonResponse) {
            System.out.println("Received JSON response: " + jsonResponse);
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(jsonResponse);

                // Check if 'messages' array is present and not empty
                if (rootNode.has("messages") && rootNode.get("messages").isArray() && !rootNode.get("messages").isEmpty()) {
                    JsonNode messagesNode = rootNode.get("messages").get(0);
                    System.out.println("Messages node found: " + messagesNode.toString());

                    // Extract the message ID
                    String messageId = messagesNode.path("id").asText();
                    if (!messageId.isEmpty()) {
                        System.out.println("Extracted message ID: " + messageId);
                        return messageId;
                    } else {
                        System.out.println("Message ID is empty in the JSON response.");
                        return null;
                    }
                } else {
                    System.out.println("No messages found in the JSON response or messages array is empty.");
                    return null;
                }
            } catch (JsonProcessingException e) {
                System.out.println("Error parsing JSON response: " + e.getMessage());
                return null;
            }
        }


    @Override
    public Map<String, String> parseWhatsAppMessageIds(String responseBody) {
        JSONObject responseJson = new JSONObject(responseBody);
        Map<String, String> ids = new HashMap<>();

        // Extract the 'messages' array
        JSONArray entries = responseJson.optJSONArray("entry");
        if (entries != null && !entries.isEmpty()) {
            JSONObject entry = entries.optJSONObject(0);
            if (entry != null) {
                JSONArray changes = entry.optJSONArray("changes");
                if (changes != null && !changes.isEmpty()) {
                    JSONObject change = changes.optJSONObject(0);
                    if (change != null) {
                        JSONObject value = change.optJSONObject("value");
                        if (value != null) {
                            JSONArray messages = value.optJSONArray("messages");
                            if (messages != null && !messages.isEmpty()) {
                                JSONObject message = messages.optJSONObject(0);
                                if (message != null) {
                                    // Extract the 'context' object to get the original message ID
                                    JSONObject context = message.optJSONObject("context");
                                    if (context != null) {
                                        String originalMessageId = context.optString("id", null);
                                        if (originalMessageId != null) {
                                            ids.put("originalMessageId", originalMessageId);
                                        }
                                    }

                                    // Extract the current message ID
                                    String currentMessageId = message.optString("id", null);
                                    if (currentMessageId != null) {
                                        ids.put("currentMessageId", currentMessageId);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return ids;
    }
}





