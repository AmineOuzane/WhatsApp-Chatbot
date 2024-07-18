package com.example.utils;

import com.example.chatbotwhatsapp.dtos.UtilisateurDTO;
import com.example.chatbotwhatsapp.entities.Utilisateur;

public class DtoConverter {

    public static Utilisateur convertUtilisateurDTOToUtilisateur(UtilisateurDTO utilisateurDTO) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPhoneNumber(utilisateurDTO.getPhoneNumber());
        utilisateur.setNom(utilisateurDTO.getNom());
        utilisateur.setEmail(utilisateurDTO.getEmail());
        // map other fields
        return utilisateur;
    }
}