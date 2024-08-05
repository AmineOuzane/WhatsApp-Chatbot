package com.example.chatbotwhatsapp.service;

import com.example.chatbotwhatsapp.enums.statutOpp;
import com.example.chatbotwhatsapp.enums.statutPrj;
import org.springframework.http.ResponseEntity;

public interface SenderStatusService {

    ResponseEntity<String> sendNoOpportuniteStatus(statutOpp status);
    ResponseEntity<String> sendNoProjetStatus(statutPrj status);
    ResponseEntity<String> sendOpportuniteStatus(statutOpp status);
    ResponseEntity<String> sendProjetStatus(statutPrj status);
    ResponseEntity<String> saisirOpportuniteStatus();
    ResponseEntity<String> saisirProjetStatus();


}
