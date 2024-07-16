package com.example.chatbotwhatsapp.service;

import com.example.chatbotwhatsapp.dtos.UtilisateurDTO;

public interface UtilisateurService {

    UtilisateurDTO getUtilisateurByTelephone(String telephone);
}
