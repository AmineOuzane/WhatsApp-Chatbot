package com.example.chatbotwhatsapp.dtos;

import com.example.chatbotwhatsapp.entities.Utilisateur;
import com.example.chatbotwhatsapp.enums.statutPrj;
import lombok.Data;

@Data
public class ProjetDTO {

    private int id;
    private String description;
    private String client;
    private Utilisateur chefProjet;
    private String commercial;
    private statutPrj statut;

}
