package com.example.chatbotwhatsapp.service;

import org.springframework.http.ResponseEntity;

public interface DetailsProjetService {

    ResponseEntity<String> NoProjetFound(String nom);
    ResponseEntity<String> sendProjetDetails(String nom);
    ResponseEntity<String> saisirNomProjet();


}
