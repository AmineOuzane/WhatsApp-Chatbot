package com.example.chatbotwhatsapp.entities;


import com.example.chatbotwhatsapp.enums.statutPrj;
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
public class Projet {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String description;
    private String client;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "chef_projet_phone_number")
    private Utilisateur chefProjet;

    @ManyToOne
    @JoinColumn(name = "projet_commercial_phone_number")
    private Utilisateur commercial;

    @Enumerated(EnumType.STRING)
    private statutPrj statut;

}
