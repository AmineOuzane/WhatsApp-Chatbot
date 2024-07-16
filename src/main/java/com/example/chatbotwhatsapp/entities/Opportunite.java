package com.example.chatbotwhatsapp.entities;


import com.example.chatbotwhatsapp.enums.statutOpp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Optional;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Opportunite {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private String client;
    private Date date;

    @ManyToOne
    private Utilisateur commercial;

    @Enumerated(EnumType.STRING)
    private statutOpp statut;


    public Optional<Utilisateur> setCommercial(Optional<Utilisateur> commercialUser) {
        return commercialUser;
    }

//    @ManyToOne
//    private Chatbot chatbot;
}
