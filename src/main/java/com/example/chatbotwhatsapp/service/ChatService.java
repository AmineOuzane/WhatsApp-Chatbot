package com.example.chatbotwhatsapp.service;

import com.example.chatbotwhatsapp.dtos.OpportuniteDTO;
import com.example.chatbotwhatsapp.dtos.ProjetDTO;
import com.example.chatbotwhatsapp.dtos.UtilisateurDTO;
import com.example.chatbotwhatsapp.entities.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface ChatService {

     Optional<UtilisateurDTO> authenticateUser(String telephone);
     List<ProjetDTO> listProjectsForUserChefProjet(Utilisateur chefProjet);
     List<ProjetDTO> listProjectsForUserCommercial(Utilisateur commercial);
     List<OpportuniteDTO> listOpportunitesForUser(Utilisateur commercial);

}
