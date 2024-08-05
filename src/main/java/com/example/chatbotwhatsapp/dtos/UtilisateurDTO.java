package com.example.chatbotwhatsapp.dtos;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UtilisateurDTO {

    private String phoneNumber;
    private String nom;
    private String prenom;
    private String email;
    private String currentState;
    private LocalDateTime lastInteractionTime;

}
