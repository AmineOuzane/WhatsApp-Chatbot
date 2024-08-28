package com.example.chatbotwhatsapp.web;


import com.example.chatbotwhatsapp.enums.statutOpp;
import com.example.chatbotwhatsapp.service.InteractionService;
import com.example.chatbotwhatsapp.service.MessageIdMappingService;
import com.example.chatbotwhatsapp.service.OpportuniteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private final InteractionService interactionService;
    private final OpportuniteService opportuniteService;
    private final MessageIdMappingService messageIdMappingService;

    // Map to store the relationship between message ID and opportunity ID
    private final Map<String, Integer> messageOpportunityMap = new HashMap<>();

    public WebhookController(InteractionService interactionService, OpportuniteService opportuniteService, MessageIdMappingService messageIdMappingService) {
        this.interactionService = interactionService;
        this.opportuniteService = opportuniteService;
        this.messageIdMappingService = messageIdMappingService;
    }

    @PostMapping // Handles POST requests to /webhook
    public ResponseEntity<Map<String, String>> handleWebhook(@RequestBody Map<String, Object> payload) {
        System.out.println("Webhook received!");
        System.out.println("Full payload: " + payload);

        Object entryObj = payload.get("entry");

        if (entryObj instanceof List) {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> entries = (List<Map<String, Object>>) entryObj;

            for (Map<String, Object> entry : entries) {
                Object changesObj = entry.get("changes");

                if (changesObj instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> changes = (List<Map<String, Object>>) changesObj;

                    for (Map<String, Object> change : changes) {
                        Object valueObj = change.get("value");

                        if (valueObj instanceof Map) {
                            @SuppressWarnings("unchecked")
                            Map<String, Object> value = (Map<String, Object>) valueObj;
                            Object messagesObj = value.get("messages");

                            if (messagesObj instanceof List) {
                                @SuppressWarnings("unchecked")
                                List<Map<String, Object>> messages = (List<Map<String, Object>>) messagesObj;

                                for (Map<String, Object> message : messages) {
                                    String messageType = (String) message.get("type");
                                    System.out.println("Message type: " + messageType);

                                    // Handle Button Messages
                                    if ("button".equals(messageType)) {
                                        System.out.println("Processing button message");

                                        @SuppressWarnings("unchecked")
                                        Map<String, Object> button = (Map<String, Object>) message.get("button");
                                        if (button == null) {
                                            System.out.println("Button object is null");
                                        } else {
                                            String buttonPayload = (String) button.get("payload");
                                            String buttonText = (String) button.get("text");
                                            System.out.println("\nButton clicked: " + buttonText + ", Payload: " + buttonPayload);

                                            @SuppressWarnings("unchecked")
                                            Map<String, Object> context = (Map<String, Object>) message.get("context");
                                            String originalMessageId = context != null ? (String) context.get("id") : null;
                                            System.out.println("Original Message ID: " + originalMessageId);

                                            messageIdMappingService.logAllMappings();

                                            Integer opportunityId = messageIdMappingService.getOpportunityId(originalMessageId);
                                            System.out.println("Opportunity ID retrieved: " + opportunityId);

                                            if (opportunityId == null) {
                                                System.out.println("No opportunity found for original message ID: " + originalMessageId);
                                            } else {

                                                // Opportunity set to EN_ATTENTE after sending the message
                                                if ("Approuver".equalsIgnoreCase(buttonPayload)) {
                                                    opportuniteService.updateOpportunityStatus(opportunityId, statutOpp.APPROUVE);
                                                    System.out.println("Opportunity " + opportunityId + " est approuvé !");
                                                } else if ("Rejeter".equalsIgnoreCase(buttonPayload)) {
                                                    opportuniteService.updateOpportunityStatus(opportunityId, statutOpp.REJETE);
                                                    System.out.println("Opportunity " + opportunityId + " est rejeté !");
                                                }
                                            }
                                        }
                                    }
                                    /*
                                         Restarting the project re-initialize the status of the opportunities like in the Main
                                         To avoid that change "create" to "update" in application properties
                                    */

                                    // Handle Text Messages
                                    Object textObj = message.get("text");
                                    if (textObj instanceof Map) {
                                        @SuppressWarnings("unchecked")
                                        Map<String, Object> text = (Map<String, Object>) textObj;
                                        String messageBody = (String) text.get("body");

                                        if (messageBody != null) {
                                            messageBody = messageBody.trim().toLowerCase();
                                            String sender = (String) message.get("from");
                                            System.out.println("Sender phone number :" + sender);

                                            interactionService.handleInteraction(sender, messageBody);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return ResponseEntity.ok(Collections.singletonMap("message", "Processed"));
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("error", "No valid message received"));
    }
}