package com.example.chatbotwhatsapp.service;

import com.example.chatbotwhatsapp.enums.statutOpp;
import org.springframework.http.ResponseEntity;

public interface DetailsOpportuniteService {

    ResponseEntity<String> NoOpportuniteFound(String nom);
    ResponseEntity<String> sendOpportuniteDetails(String nom);
        ResponseEntity<String> saisirNomOpportunite();


}
