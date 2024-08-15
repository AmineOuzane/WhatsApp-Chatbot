package com.example.chatbotwhatsapp.service;


import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ValidateOpportunity {

    ResponseEntity<String> validateOpportunity(Integer opportuniteId, String phoneNumber);
    Map<String, String> parseWhatsAppMessageIds(String responseBody);
    String extractMessageIdFromResponse(String jsonResponse);
    String extractContextIdFromResponse(String jsonResponse);
}
