package com.example.chatbotwhatsapp.service.serviceImpl;

import com.example.chatbotwhatsapp.dtos.OpportuniteDTO;
import com.example.chatbotwhatsapp.service.DetailsOpportuniteService;
import com.example.chatbotwhatsapp.service.OpportuniteService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DetailsOpportuniteServiceImpl implements DetailsOpportuniteService {

    @Value("${whatsapp.api.url}")
    private String whatsappApiUrl;

    @Value("${whatsapp.api.token}")
    private String whatsappApiToken;

    @Value("${recipient_number}")
    private String recipientNumber;

    @Autowired
    private OpportuniteService opportuniteService;

    public DetailsOpportuniteServiceImpl(RestTemplate restTemplate) {
    }


    @Override
    public ResponseEntity<String> NoOpportuniteFound(String nom) {
        OpportuniteDTO opportunite = opportuniteService.searchByName(nom);

        if (opportunite == null) {

            // Send template message to user via WhatsApp Business API
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " + whatsappApiToken);

            JSONObject requestBody = new JSONObject();
            requestBody.put("messaging_product", "whatsapp");
            requestBody.put("to", recipientNumber);
            requestBody.put("type", "template");
            requestBody.put("template", new JSONObject()
                    .put("name", "no_opp_per_nom")
                    .put("language", new JSONObject()
                            .put("code", "en")
                            .put("policy", "deterministic"))
                    .put("components",new JSONArray()
                            .put(new JSONObject()
                                    .put("type", "body")
                                    .put("parameters", new JSONArray()
                                            .put(new JSONObject()
                                                    .put("type", "text")
                                                    .put("text", nom))))));

            HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
            return restTemplate.postForEntity(whatsappApiUrl, request, String.class);
        } else {
            // Return a 404 error or a custom error message if the user is not found
            return ResponseEntity.notFound().build();
        }
    }


        @Override
        public ResponseEntity<String> sendOpportuniteDetails(String nom) {

            OpportuniteDTO opportunite = opportuniteService.searchByName(nom);
            String statutString = opportunite.getStatut() != null ? opportunite.getStatut().toString() : "Unknown";
            if (opportunite != null) {
                // Logging each field

                // Send template message to user via WhatsApp Business API
                    RestTemplate restTemplate = new RestTemplate();
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("Authorization", "Bearer " + whatsappApiToken);

                    // Create the request body JSON object
                    JSONObject requestBody = new JSONObject();
                    requestBody.put("messaging_product", "whatsapp");
                    requestBody.put("to", recipientNumber);
                    requestBody.put("type", "template");

                    // Create the template JSON object
                    JSONObject template = new JSONObject();
                    template.put("name", "opportunite_per_nom");
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
                                    .put("text", opportunite.getNom())));
                    components.put(header);

                // Add the body component
                JSONObject body = new JSONObject();
                body.put("type", "body");
                body.put("parameters", new JSONArray()
                        .put(new JSONObject()
                                .put("type", "text")
                                .put("text", opportunite.getNom()))
                        .put(new JSONObject()
                                .put("type", "text")
                                .put("text", opportunite.getDescription()))
                        .put(new JSONObject()
                                .put("type", "text")
                                .put("text",opportunite.getClient()))
                        .put(new JSONObject()
                                .put("type", "text")
                                .put("text", opportunite.getDate()))
                        .put(new JSONObject()
                                .put("type", "text")
                                .put("text", opportunite.getCommercial().getNom()))
                        .put(new JSONObject()
                                .put("type", "text")
                                .put("text",statutString)));
                components.put(body);

                // Add the components to the template
                template.put("components", components);

                // Add the template to the request body
                requestBody.put("template", template);


                HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
                return restTemplate.postForEntity(whatsappApiUrl, request, String.class);
            }
            else {
                return ResponseEntity.notFound().build();
            }

        }

    @Override
    public ResponseEntity<String> saisirNomOpportunite() {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + whatsappApiToken);

        JSONObject requestBody = new JSONObject();
        requestBody.put("messaging_product","whatsapp");
        requestBody.put("to", recipientNumber);
        requestBody.put("type", "template");
        requestBody.put("template", new JSONObject()
                .put("name", "saisir_nom_opportunite")
                .put("language", new JSONObject()
                        .put("code", "en")));

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        return restTemplate.postForEntity(whatsappApiUrl, request, String.class);
    }
}
