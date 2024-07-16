package com.example.chatbotwhatsapp.service;

import com.example.chatbotwhatsapp.dtos.OpportuniteDTO;
import com.example.chatbotwhatsapp.dtos.ProjetDTO;
import com.example.chatbotwhatsapp.entities.Projet;
import com.example.chatbotwhatsapp.entities.Utilisateur;

import java.util.List;

public interface ProjetService {

     List<ProjetDTO> getAllProjet();
     ProjetDTO getProjetById(int idProjet);
     List<ProjetDTO> findBychefProjet(Utilisateur chefProjet);
     List<ProjetDTO> findByCommercial(Utilisateur commercial);


}
