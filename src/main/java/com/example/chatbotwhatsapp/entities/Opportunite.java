package com.example.chatbotwhatsapp.entities;


import com.example.chatbotwhatsapp.enums.statutOpp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

    @Entity
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Opportunite {

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String nom;
        private String description;
        private String client;
        private Date date;

        @ManyToOne
        @JoinColumn(name = "opportunite_commercial_phone_number")
        private Utilisateur commercial;

        @Enumerated(EnumType.STRING)
        private statutOpp statut;

    }
