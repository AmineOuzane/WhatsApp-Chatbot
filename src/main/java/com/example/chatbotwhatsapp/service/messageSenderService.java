package com.example.chatbotwhatsapp.service;

import com.example.chatbotwhatsapp.dtos.OpportuniteDTO;
import com.example.chatbotwhatsapp.dtos.ProjetDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface messageSenderService {

    ResponseEntity<String> sendTemplateMessage();
    ResponseEntity<String> sendInconnuMessage();
    ResponseEntity<String> sendChoixProjet();
    ResponseEntity<String> sendChoixOpportunite();
    ResponseEntity<String> projetCommercialouChefProjet();
    ResponseEntity<String> sendTemplateMessageWithUserNoProjet(String phoneNumber);
    ResponseEntity<String> sendTemplateMessageWithUserNoOpportunite(String phoneNumber);
    ResponseEntity<String> sendTemplateMessageWithUserAndOpportunite(String phoneNumber);
    ResponseEntity<String> sendTemplateMessageWithUserAndProjetcommercial(String phoneNumber);
    ResponseEntity<String> sendTemplateMessageWithUserAndProjetchefProjet(String phoneNumber);
    String getOpportuniteListText(List<OpportuniteDTO> opportuniteDTOList);
    String getProjetListText(List<ProjetDTO> projetsDTOList);


}
