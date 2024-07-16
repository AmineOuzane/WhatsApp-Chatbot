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

    private String description;
    private String client;
    private Date date;

    @ManyToOne
    private Utilisateur chefProjet;

    @ManyToOne
    private Utilisateur commercial;

    @Enumerated(EnumType.STRING)
    private statutPrj statut;

    public Optional<Utilisateur> setChefProjet(Optional<Utilisateur> chefProjetlUser) {
        return chefProjetlUser;
    }

    public Optional<Utilisateur> setCommercial(Optional<Utilisateur> commercialUserProjet) {
        return commercialUserProjet;
    }

//    @ManyToOne
//    private Chatbot chatbot;
}
