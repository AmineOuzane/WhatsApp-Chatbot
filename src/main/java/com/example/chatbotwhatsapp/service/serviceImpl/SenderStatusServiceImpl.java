package com.example.chatbotwhatsapp.service.serviceImpl;

import com.example.chatbotwhatsapp.dtos.OpportuniteDTO;
import com.example.chatbotwhatsapp.dtos.ProjetDTO;
import com.example.chatbotwhatsapp.enums.statutOpp;
import com.example.chatbotwhatsapp.enums.statutPrj;
import com.example.chatbotwhatsapp.service.OpportuniteService;
import com.example.chatbotwhatsapp.service.ProjetService;
import com.example.chatbotwhatsapp.service.SenderStatusService;
import com.example.chatbotwhatsapp.service.messageSenderService;
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

import java.util.List;


@Service
public class SenderStatusServiceImpl implements SenderStatusService {

    @Autowired
    private OpportuniteService opportuniteService;

    @Autowired
    private ProjetService projetService;

    @Autowired
    private messageSenderService messsageService;

    @Value("${whatsapp.api.url}")
    private String whatsappApiUrl;

    @Value("${whatsapp.api.token}")
    private String whatsappApiToken;

    @Value("${recipient_number}")
    private String recipientNumber;

    public SenderStatusServiceImpl(RestTemplate restTemplate) {
    }

    @Override
    public ResponseEntity<String> sendNoOpportuniteStatus(statutOpp status) {
        List<OpportuniteDTO> opportunitieList = opportuniteService.searchByStatus(status);

        if (opportunitieList.isEmpty()) {
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
                    .put("name", "no_opportunite_per_status")
                    .put("language", new JSONObject()
                            .put("code", "en")
                            .put("policy", "deterministic"))
                    .put("components", new JSONArray()
                            .put(new JSONObject()
                                    .put("type", "body")
                                    .put("parameters", new JSONArray()
                                            .put(new JSONObject()
                                                    .put("type", "text")
                                                    .put("text", status.name()))))));

            HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
            String response = restTemplate.postForObject(whatsappApiUrl, request, String.class);
            return ResponseEntity.ok(response);
        } else {
            // Return a 404 error or a custom error message if opportunities are found
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> sendNoProjetStatus(statutPrj status) {
        List<ProjetDTO> projetList = projetService.searchByStatus(status);

        if (projetList.isEmpty()) {
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
                    .put("name", "no_projet_per_statut")
                    .put("language", new JSONObject()
                            .put("code", "en")
                            .put("policy", "deterministic"))
                    .put("components", new JSONArray()
                            .put(new JSONObject()
                                    .put("type", "body")
                                    .put("parameters", new JSONArray()
                                            .put(new JSONObject()
                                                    .put("type", "text")
                                                    .put("text", status.name()))))));

            HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
            String response = restTemplate.postForObject(whatsappApiUrl, request, String.class);
            return ResponseEntity.ok(response);
        } else {

            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> sendOpportuniteStatus(statutOpp status) {
        List<OpportuniteDTO> opportunitieList = opportuniteService.searchByStatus(status);
        if (!opportunitieList.isEmpty()) {
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
            template.put("name", "opportunite_per_statut");
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
                            .put("text", status.name())));
            components.put(header);

            // Add the body component with opportunity list
            JSONObject body = new JSONObject();
            body.put("type", "body");
            body.put("parameters", new JSONArray()
                    .put(new JSONObject()
                            .put("type", "text")
                            .put("text", messsageService.getOpportuniteListText(opportunitieList))));
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
    public ResponseEntity<String> sendProjetStatus(statutPrj status) {
        List<ProjetDTO> projetList = projetService.searchByStatus(status);
        if (!projetList.isEmpty()) {
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
            template.put("name", "projet_per_status");
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
                            .put("text", status.name())));
            components.put(header);

            // Add the body component with opportunity list
            JSONObject body = new JSONObject();
            body.put("type", "body");
            body.put("parameters", new JSONArray()
                    .put(new JSONObject()
                            .put("type", "text")
                            .put("text", messsageService.getProjetListText(projetList))));
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
    public ResponseEntity<String> saisirOpportuniteStatus() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + whatsappApiToken);

        JSONObject requestBody = new JSONObject();
        requestBody.put("messaging_product","whatsapp");
        requestBody.put("to", recipientNumber);
        requestBody.put("type", "template");
        requestBody.put("template", new JSONObject()
                .put("name", "saisir_status_opportunite")
                .put("language", new JSONObject()
                        .put("code", "en")));

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        return restTemplate.postForEntity(whatsappApiUrl, request, String.class);
    }

    @Override
    public ResponseEntity<String> saisirProjetStatus() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + whatsappApiToken);

        JSONObject requestBody = new JSONObject();
        requestBody.put("messaging_product","whatsapp");
        requestBody.put("to", recipientNumber);
        requestBody.put("type", "template");
        requestBody.put("template", new JSONObject()
                .put("name", "saisir_status_projet")
                .put("language", new JSONObject()
                        .put("code", "en")));

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        return restTemplate.postForEntity(whatsappApiUrl, request, String.class);
    }
}


