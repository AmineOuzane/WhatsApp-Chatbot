package com.example.chatbotwhatsapp.dtos;

import com.example.chatbotwhatsapp.enums.statutOpp;
import lombok.Data;

@Data
public class OpportuniteDTO {
    private int id;
    private String nom;
    private String description;
    private statutOpp statut;
    private String client;

}
