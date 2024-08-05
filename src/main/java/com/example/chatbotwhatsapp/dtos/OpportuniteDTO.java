package com.example.chatbotwhatsapp.dtos;

import com.example.chatbotwhatsapp.entities.Utilisateur;
import com.example.chatbotwhatsapp.enums.statutOpp;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Date;

@Data
public class OpportuniteDTO {
    private int id;
    private String nom;
    private String description;
    private Date date;
    private UtilisateurDTO commercial;
    @Enumerated(EnumType.STRING)
    private statutOpp statut;
    private String client;

}
