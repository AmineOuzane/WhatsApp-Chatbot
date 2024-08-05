package com.example.chatbotwhatsapp.dtos;

import com.example.chatbotwhatsapp.enums.statutPrj;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Date;

@Data
public class ProjetDTO {

    private int id;
    private String nom;
    private String description;
    private String client;
    private Date date;

    @JsonProperty("chefProjetDTO")
    private UtilisateurDTO chefProjet;

    @JsonProperty("commercialDTO")
    private UtilisateurDTO commercial;

    @Enumerated(EnumType.STRING)
    private statutPrj statut;

    public ProjetDTO() {
        this.chefProjet = new UtilisateurDTO();
        this.commercial = new UtilisateurDTO();  // Ensure both are initialized
    }


}
