package com.example.chatbotwhatsapp.service.serviceImpl;

import com.example.chatbotwhatsapp.dtos.ProjetDTO;
import com.example.chatbotwhatsapp.service.DetailsProjetService;
import com.example.chatbotwhatsapp.service.ProjetService;
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
public class DetailsProjetServiceImpl implements DetailsProjetService {

    @Value("${whatsapp.api.url}")
    private String whatsappApiUrl;

    @Value("${whatsapp.api.token}")
    private String whatsappApiToken;

    @Value("${recipient_number}")
    private String recipientNumber;


    @Autowired
    private ProjetService projetService;

    public DetailsProjetServiceImpl(RestTemplate restTemplate) {
    }


    @Override
    public ResponseEntity<String> NoProjetFound(String nom) {
        ProjetDTO projet = projetService.searchByName(nom);

        if (projet == null) {

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
                    .put("name", "no_projet_per_nom")
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
    public ResponseEntity<String> sendProjetDetails(String nom) {
        ProjetDTO projet = projetService.searchByName(nom);
        if (projet!= null) {
            if (projet.getNom() == null || projet.getDescription() == null || projet.getClient() == null ||
                    projet.getCommercial() == null || projet.getCommercial().getNom() == null ||
                    projet.getChefProjet() == null || projet.getChefProjet().getNom() == null) {
                return ResponseEntity.badRequest().body("Some fields are missing.");
            }
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
            template.put("name", "projet_per_nom");
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
                            .put("text", projet.getNom())));
            components.put(header);


            // Add the body component
            JSONObject body = new JSONObject();
            body.put("type", "body");
            body.put("parameters", new JSONArray()
                    .put(new JSONObject().put("type", "text").put("text", projet.getNom()))
                    .put(new JSONObject().put("type", "text").put("text", projet.getDescription()))
                    .put(new JSONObject().put("type", "text").put("text", projet.getClient()))
                    .put(new JSONObject().put("type", "text").put("text", projet.getDate().toString()))
                    .put(new JSONObject().put("type", "text").put("text", projet.getCommercial().getNom()))
                    .put(new JSONObject().put("type", "text").put("text", projet.getChefProjet().getNom()))
                    .put(new JSONObject().put("type", "text").put("text", projet.getStatut().toString())));
            components.put(body);

            // Add the components to the template
            template.put("components", components);

            // Add the template to the request body
            requestBody.put("template", template);

            HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
            return restTemplate.postForEntity(whatsappApiUrl, request, String.class);
        } else {
            // Return a 404 error or a custom error message if the user is not found
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> saisirNomProjet() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + whatsappApiToken);

        JSONObject requestBody = new JSONObject();
        requestBody.put("messaging_product","whatsapp");
        requestBody.put("to", recipientNumber);
        requestBody.put("type", "template");
        requestBody.put("template", new JSONObject()
                .put("name", "saisir_nom_projet")
                .put("language", new JSONObject()
                        .put("code", "en")));

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        return restTemplate.postForEntity(whatsappApiUrl, request, String.class);
    }
}

