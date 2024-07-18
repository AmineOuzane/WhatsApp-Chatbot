package com.example.chatbotwhatsapp.service;

import com.example.chatbotwhatsapp.entities.Utilisateur;
import org.springframework.http.ResponseEntity;

public interface messageSenderService {
    ResponseEntity<String> sendTemplateMessage();
    ResponseEntity<String> sendTemplateMessageWithUser(String user);
    ResponseEntity<String> sendTemplateMessageWithUserAndOpportunite(String phoneNumber);
}
