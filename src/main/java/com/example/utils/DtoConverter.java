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

    public static Opportunite convertOpportuniteDTOToOpportunite(OpportuniteDTO opportuniteDTO) {
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

    public static OpportuniteDTO convertOpportuniteToOpportuniteDTO(Opportunite opportunite) {
        OpportuniteDTO opportuniteDTO = new OpportuniteDTO();
        opportuniteDTO.setId(opportunite.getId());
        opportuniteDTO.setNom(opportunite.getNom());
        opportuniteDTO.setDescription(opportunite.getDescription());
        opportuniteDTO.setClient(opportunite.getClient());
        opportuniteDTO.setDate(opportunite.getDate());
        Utilisateur commercial = opportunite.getCommercial();
        UtilisateurDTO utilisateurDTO = convertUtilisateurToUtilisateurDTO(commercial);
        opportuniteDTO.setCommercial(utilisateurDTO);
        opportuniteDTO.setStatut(opportunite.getStatut());
        return opportuniteDTO;
    }

    // Converter method
    public static UtilisateurDTO convertUtilisateurToUtilisateurDTO(Utilisateur utilisateur) {
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setPhoneNumber(utilisateur.getPhoneNumber());
        utilisateurDTO.setNom(utilisateur.getNom());
        utilisateurDTO.setPrenom(utilisateur.getPrenom());
        // Set other UtilisateurDTO fields as needed
        return utilisateurDTO;
    }

}