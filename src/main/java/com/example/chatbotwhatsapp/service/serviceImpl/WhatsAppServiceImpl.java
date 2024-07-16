package com.example.chatbotwhatsapp.service.serviceImpl;

import org.springframework.http.*;
import com.example.chatbotwhatsapp.service.WhatsAppService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service

public class WhatsAppServiceImpl implements WhatsAppService {

    @Value("${whatsapp.api.url}")
    private String apiUrl;

    @Value("${whatsapp.api.token}")
    private String apiToken;

    // Used to make HTTP requests to the WhatsApp API.
    private final RestTemplate restTemplate;

    public WhatsAppServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


//    @Override
//    public void sendMessage(String to) {
//
//        // Creating HttpHeaders for the request
//        HttpHeaders headers = new HttpHeaders();
//        // Sets the Authorization header with the WhatsApp API token for authentication.
//        headers.setBearerAuth(apiToken);
//        // Sets the Content-Type header to indicate JSON format for the message payload.
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // Creating the message payload in JSON format
//        String messagePayload = String.format(
//                "{\"messaging_product\": \"whatsapp\", \"to\": \"%s\", \"type\": \"template\", \"template\": { \"name\": \"hello_world\", \"language\": { \"code\": \"en_US\" } } }",
//                to
//        );
//
//        // Creating an HttpEntity with headers and message payload which represents the entire HTTP request
//        HttpEntity<String> entity = new HttpEntity<>(messagePayload, headers);
//
//        // Uses RestTemplate to send a POST request to the WhatsApp API endpoint (apiUrl), include HttpEntity and expect a String response
//        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
//
//        // Checking the response status
//        if (response.getStatusCode() == HttpStatus.OK) {
//            System.out.println("Message sent successfully: " + response.getBody());
//        } else {
//            System.err.println("Failed to send message. Status code: " + response.getStatusCode() + ", Response: " + response.getBody());
//        }
//
//
//    }
}
