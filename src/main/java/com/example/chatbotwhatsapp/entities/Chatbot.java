package com.example.chatbotwhatsapp.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Chatbot {
    @Id
    private int id;
    private String version;

//    @OneToMany(mappedBy = "chatbot", fetch = FetchType.LAZY)
//    private List<Opportunite> opportunitesList;
//
//    @OneToMany(mappedBy = "chatbot", fetch = FetchType.LAZY)
//    private List<Projet> projetList;
}
