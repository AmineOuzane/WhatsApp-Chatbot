package com.example.chatbotwhatsapp.service;

import com.example.chatbotwhatsapp.dtos.UtilisateurDTO;
import com.example.chatbotwhatsapp.entities.Utilisateur;

public interface UtilisateurService {

    UtilisateurDTO getUtilisateurByTelephone(String telephone);
    void updateConversationState(String phoneNumber, String newState);
    void saveUtilisateur(Utilisateur utilisateur);

}
