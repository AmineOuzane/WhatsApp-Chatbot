package com.example.chatbotwhatsapp.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

    // Endpoint to handle POST requests from the webhook
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhookEvent(@RequestBody String payload) {
        // Process the webhook payload here
        System.out.println("Received webhook payload: " + payload);

        // Add your processing logic here, e.g., update database, trigger actions, etc.

        // Send a response to acknowledge receipt (optional, depending on webhook requirements)
        return ResponseEntity.ok("Webhook received successfully");
    }
}

//     @PostMapping("/sendTemplateWithOpportunite/{phoneNumber}")
//    public ResponseEntity<String> sendTemplateMessageWithOpportunite(@PathVariable String phoneNumber, @RequestBody Utilisateur commercial) {
//        return messageService.sendTemplateMessageWithUserAndOpportunite(phoneNumber, commercial);
//     }