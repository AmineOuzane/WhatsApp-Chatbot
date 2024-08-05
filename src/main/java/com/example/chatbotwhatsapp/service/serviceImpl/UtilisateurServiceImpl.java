package com.example.chatbotwhatsapp.service.serviceImpl;


import com.example.chatbotwhatsapp.dtos.UtilisateurDTO;
import com.example.chatbotwhatsapp.entities.Utilisateur;
import com.example.chatbotwhatsapp.repositories.UtilisateurRepository;
import com.example.chatbotwhatsapp.service.UtilisateurService;
import com.example.chatbotwhatsapp.utils.ObjectMapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    UtilisateurRepository utilisateurRepository;

    @Override
    public UtilisateurDTO getUtilisateurByTelephone(String phoneNumber) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByPhoneNumber(phoneNumber);
        return utilisateur.map(value -> ObjectMapperUtils.map(value, UtilisateurDTO.class)).orElse(null);
    }

    @Override
    public void updateConversationState(String phoneNumber, String newState) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByPhoneNumber(phoneNumber);
        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            utilisateur.setCurrentState(newState);
            utilisateurRepository.save(utilisateur);
        }
    }

    @Override
    public void saveUtilisateur(Utilisateur utilisateur) {
        utilisateurRepository.save(utilisateur);
    }
}

