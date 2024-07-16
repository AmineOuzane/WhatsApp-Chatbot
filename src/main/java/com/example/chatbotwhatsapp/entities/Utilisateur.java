package com.example.chatbotwhatsapp.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String email;

    private String telephone;

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
