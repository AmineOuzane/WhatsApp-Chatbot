package com.example.chatbotwhatsapp.service.serviceImpl;

import com.example.chatbotwhatsapp.dtos.UtilisateurDTO;
import com.example.chatbotwhatsapp.service.AboutMeService;
import com.example.chatbotwhatsapp.service.UtilisateurService;
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
public class AboutMeServiceImpl implements AboutMeService {

    @Value("${whatsapp.api.url}")
    private String whatsappApiUrl;

    @Value("${whatsapp.api.token}")
    private String whatsappApiToken;

    @Value("${recipient_number}")
    private String recipientNumber;

    @Autowired
    private UtilisateurService utilisateurService;


    @Override
        public ResponseEntity<String> sendTemplateMessageWithUser(String phoneNumber) {
            // Ensure the phone number includes the '+'
            String formattedPhoneNumber = phoneNumber.startsWith("+") ? phoneNumber : "+" + phoneNumber;

            // Retrieve user information
            UtilisateurDTO user = utilisateurService.getUtilisateurByTelephone(formattedPhoneNumber);

            if (user == null) {
                System.err.println("User not found for phone number: " + formattedPhoneNumber);
                return ResponseEntity.notFound().build();
            }

            // Log retrieved user information for debugging
            System.out.println("Retrieved user info: " + user);


        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Set the HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + whatsappApiToken);

// Create the request body JSON object
        JSONObject requestBody = new JSONObject();
        requestBody.put("messaging_product", "whatsapp");
        requestBody.put("to", user.getPhoneNumber());
        requestBody.put("type", "template");

// Create the template JSON object
        JSONObject template = new JSONObject();
        template.put("name", "information_personnelle");
        template.put("language", new JSONObject()
                .put("code", "en")
                .put("policy", "deterministic"));
        template.put("components", new JSONArray()
                .put(new JSONObject()
                        .put("type", "body")
                        .put("parameters", new JSONArray()
                                .put(new JSONObject()
                                        .put("type", "text")
                                        .put("text", user.getPhoneNumber()))
                                .put(new JSONObject()
                                        .put("type", "text")
                                        .put("text", user.getNom()))
                                .put(new JSONObject()
                                        .put("type", "text")
                                        .put("text", user.getPrenom()))
                                .put(new JSONObject()
                                        .put("type", "text")
                                        .put("text", user.getEmail())))));

        requestBody.put("template", template); // Add the template to the request body

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        return restTemplate.postForEntity(whatsappApiUrl, request, String.class);

    }
}
