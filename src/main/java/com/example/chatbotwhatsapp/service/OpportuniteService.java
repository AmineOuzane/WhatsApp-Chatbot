package com.example.chatbotwhatsapp.service;

import com.example.chatbotwhatsapp.dtos.OpportuniteDTO;
import com.example.chatbotwhatsapp.entities.Utilisateur;

import java.util.List;

public interface OpportuniteService {

     List<OpportuniteDTO> getAllOpportunites();
     OpportuniteDTO getOpportuniteById(int idOpportunite);
     List<OpportuniteDTO> findBycommercial(Utilisateur commercial);

}
