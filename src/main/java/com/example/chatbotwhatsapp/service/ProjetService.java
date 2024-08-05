package com.example.chatbotwhatsapp.service;

import com.example.chatbotwhatsapp.dtos.OpportuniteDTO;
import com.example.chatbotwhatsapp.dtos.ProjetDTO;
import com.example.chatbotwhatsapp.entities.Utilisateur;
import com.example.chatbotwhatsapp.enums.statutPrj;

import java.util.List;

public interface ProjetService {

     List<ProjetDTO> getAllProjet();
     ProjetDTO getProjetById(int idProjet);
     List<ProjetDTO> findBychefProjet(Utilisateur chefProjet);
     List<ProjetDTO> findByCommercial(Utilisateur commercial);
     List<ProjetDTO> searchByStatus(statutPrj status);
     ProjetDTO searchByName(String nom);

}
