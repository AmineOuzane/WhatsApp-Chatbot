package com.example.utils;

import com.example.chatbotwhatsapp.dtos.OpportuniteDTO;
import com.example.chatbotwhatsapp.dtos.UtilisateurDTO;
import com.example.chatbotwhatsapp.entities.Opportunite;
import com.example.chatbotwhatsapp.entities.Utilisateur;

public class DtoConverter {

    public static Utilisateur convertUtilisateurDTOToUtilisateur(UtilisateurDTO utilisateurDTO) {

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPhoneNumber(utilisateurDTO.getPhoneNumber());
        utilisateur.setNom(utilisateurDTO.getNom());
        utilisateur.setEmail(utilisateurDTO.getEmail());

        return utilisateur;
    }

    public Opportunite convertOpportuniteDTOToOpportunite(OpportuniteDTO opportuniteDTO) {
        Opportunite opportunite = new Opportunite();
        opportunite.setId(opportuniteDTO.getId());
        opportunite.setNom(opportuniteDTO.getNom());
        opportunite.setDescription(opportuniteDTO.getDescription());
        opportunite.setClient(opportuniteDTO.getClient());
        opportunite.setDate(opportuniteDTO.getDate());
        UtilisateurDTO utilisateurDTO = opportuniteDTO.getCommercial();
        Utilisateur commercial = convertUtilisateurDTOToUtilisateur(utilisateurDTO);
        opportunite.setCommercial(commercial);
        opportunite.setStatut(opportuniteDTO.getStatut());
        return opportunite;
    }

}