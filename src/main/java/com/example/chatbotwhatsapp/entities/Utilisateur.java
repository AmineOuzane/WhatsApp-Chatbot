package com.example.chatbotwhatsapp.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {

    @Id
    private String phoneNumber;
    private String nom;
    private String prenom;
    private String email;
    private String currentState;
    private LocalDateTime lastInteractionTime;

    private boolean isChefProjet;
    private boolean isCommercial;

    @OneToMany(mappedBy = "commercial")
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Opportunite> opportuniteList;

    @OneToMany(mappedBy = "chefProjet")
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Projet> chefProjetList;

    @OneToMany(mappedBy = "commercial")
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Projet> commercialProjetList;

}
