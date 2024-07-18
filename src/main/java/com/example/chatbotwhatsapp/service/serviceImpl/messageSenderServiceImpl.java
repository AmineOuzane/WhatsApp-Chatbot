package com.example.chatbotwhatsapp.service.serviceImpl;


import com.example.chatbotwhatsapp.dtos.OpportuniteDTO;
import com.example.chatbotwhatsapp.dtos.UtilisateurDTO;
import com.example.chatbotwhatsapp.entities.Utilisateur;
import com.example.chatbotwhatsapp.repositories.UtilisateurRepository;
import com.example.chatbotwhatsapp.service.OpportuniteService;
import com.example.chatbotwhatsapp.service.UtilisateurService;
import com.example.chatbotwhatsapp.service.messageSenderService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class messageSenderServiceImpl implements messageSenderService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    @Value("${whatsapp.api.url}")
    private String whatsappApiUrl;

    @Value("${whatsapp.api.token}")
    private String whatsappApiToken;

    @Value("${recipient_number}")
    private String recipientNumber;

    private final RestTemplate restTemplate;

    @Autowired
    private OpportuniteService opportuniteService;

    public messageSenderServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Message no Variable
    public ResponseEntity<String> sendTemplateMessage(){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + whatsappApiToken);

        JSONObject requestBody = new JSONObject();
        requestBody.put("messaging_product", "whatsapp");
        requestBody.put("to", recipientNumber);
        requestBody.put("type", "template");
        requestBody.put("template", new JSONObject()
                .put("name", "bonjour_menu")        // Template name bonjour_menu
                .put("language", new JSONObject()
                        .put("code", "en_US")));

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(whatsappApiUrl, request, String.class);
        return response;
    }

    // Message variable in Header
    public ResponseEntity<String> sendTemplateMessageWithUser(String phoneNumber) {
        UtilisateurDTO user = utilisateurService.getUtilisateurByTelephone(phoneNumber);

        if (user != null) { // Check for null instead of isPresent()
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " + whatsappApiToken);

            JSONObject requestBody = new JSONObject();
            requestBody.put("messaging_product", "whatsapp");
            requestBody.put("to", recipientNumber);
            requestBody.put("type", "template");
            requestBody.put("template", new JSONObject()
                    .put("name", "no_projet")       // Template name no_projet
                    .put("language", new JSONObject()
                            .put("code", "en")
                            .put("policy", "deterministic"))
                    .put("components", new JSONArray()
                            .put(new JSONObject()
                                    .put("type", "header")
                                    .put("parameters", new JSONArray()
                                            .put(new JSONObject()
                                                    .put("type", "text")
                                                    .put("text", user.getNom()))))));

            //Create HttpEntity object that represents the HTTP request entity, ncludes the JSON payload (requestBody) and HTTP headers (headers) in the request entity
            HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);

            // Makes a POST request to the WhatsApp API with the specified request entity (request), Returns the response as a ResponseEntity with a string response body
            return restTemplate.postForEntity(whatsappApiUrl, request, String.class);

        } else {
            // Return a 404 error or a custom error message if the user is not found
            return ResponseEntity.notFound().build();
        }
    }

    // Message Variable Header and variable List of max 10 or whatsapp split it to multiple messages
    public ResponseEntity<String> sendTemplateMessageWithUserAndOpportunite(String phoneNumber) {
        // Get the user object by phone number
        UtilisateurDTO commercialDTO = utilisateurService.getUtilisateurByTelephone(phoneNumber);

        // Convert the UtilisateurDTO to Utilisateur
        Utilisateur commercial = com.example.utils.DtoConverter.convertUtilisateurDTOToUtilisateur(commercialDTO);

        // Get the list of opportunities based on the commercial user
        List<OpportuniteDTO> opportuniteDTOList = opportuniteService.findBycommercial(commercial);

        if (!opportuniteDTOList.isEmpty()) {
            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            // Set the HTTP headers
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
            template.put("name", "list_opportunite");
            template.put("language", new JSONObject()
                    .put("code", "en")
                    .put("policy", "deterministic"));

            // Create the components JSON array
            JSONArray components = new JSONArray();

            // Add the header component
            JSONObject header = new JSONObject();
            header.put("type", "header");
            header.put("parameters", new JSONArray()
                    .put(new JSONObject()
                            .put("type", "text")
                            .put("text", commercial.getNom())));
            components.put(header);

            // Add the body component with opportunity list
            JSONObject body = new JSONObject();
            body.put("type", "body");
            body.put("parameters", new JSONArray()
                    .put(new JSONObject()
                            .put("type", "text")
                            .put("text", getOpportuniteListText(opportuniteDTOList))));
            components.put(body);

            // Add the components to the template
            template.put("components", components);

            // Add the template to the request body
            requestBody.put("template", template);

            // Create the HTTP entity
            HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);

            // Make the POST request to the WhatsApp API
            return restTemplate.postForEntity(whatsappApiUrl, request, String.class);
        } else {
            // Return a 404 error or a custom error message if the user is not found
            return ResponseEntity.notFound().build();
        }
    }

    // the type text facebook api only accept a string but we have a list so we create these methode to stringify it
    private String getOpportuniteListText(List<OpportuniteDTO> opportuniteDTOList) {
        StringBuilder text = new StringBuilder();
        for (OpportuniteDTO opportunite : opportuniteDTOList) {
            text.append(opportunite.getNom()).append(" // "); // or append(" ");
        }
        return text.toString().trim(); // remove trailing comma or space
    }}


