package com.example.chatbotwhatsapp.service.serviceImpl;

import com.example.chatbotwhatsapp.dtos.OpportuniteDTO;
import com.example.chatbotwhatsapp.dtos.ProjetDTO;
import com.example.chatbotwhatsapp.dtos.UtilisateurDTO;
import com.example.chatbotwhatsapp.entities.Utilisateur;
import com.example.chatbotwhatsapp.service.ChatService;
import com.example.chatbotwhatsapp.service.OpportuniteService;
import com.example.chatbotwhatsapp.service.ProjetService;
import com.example.chatbotwhatsapp.service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {


    private UtilisateurService utilisateurService;
    private ProjetService projetService;
    private OpportuniteService opportuniteService;


    @Override
    public Optional<UtilisateurDTO> authenticateUser(String telephone) {
        return Optional.ofNullable(utilisateurService.getUtilisateurByTelephone(telephone));
    }

    @Override
    public List<ProjetDTO> listProjectsForUserChefProjet(Utilisateur chefProjet) {
        return projetService.findBychefProjet(chefProjet);
    }

    @Override
    public List<ProjetDTO> listProjectsForUserCommercial(Utilisateur commercial) {
        return projetService.findByCommercial(commercial);
    }

    @Override
    public List<OpportuniteDTO> listOpportunitesForUser(Utilisateur commercial) {
        return opportuniteService.findBycommercial(commercial);
    }




}
