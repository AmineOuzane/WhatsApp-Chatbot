package com.example.chatbotwhatsapp.service.serviceImpl;


import com.example.chatbotwhatsapp.dtos.OpportuniteDTO;
import com.example.chatbotwhatsapp.entities.Opportunite;
import com.example.chatbotwhatsapp.entities.Utilisateur;
import com.example.chatbotwhatsapp.repositories.OpportuniteRepository;
import com.example.chatbotwhatsapp.service.OpportuniteService;
import com.example.chatbotwhatsapp.utils.ObjectMapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OpportuniteServiceImpl implements OpportuniteService {

    private OpportuniteRepository opportuniteRepository;

    @Override
    public List<OpportuniteDTO> getAllOpportunites() {
        List<Opportunite> opportuniteList=opportuniteRepository.findAll();
        return ObjectMapperUtils.mapAll(opportuniteList, OpportuniteDTO.class);
    }

    @Override
    public OpportuniteDTO getOpportuniteById(int idOpportunite) {
        Optional<Opportunite> searchedOpportunite=opportuniteRepository.findById((int) idOpportunite);
        return searchedOpportunite.map(opportunite -> ObjectMapperUtils.map(opportunite, OpportuniteDTO.class)).orElse(null);
    }

    public List<OpportuniteDTO> findBycommercial(Utilisateur commercial) {
        List<Opportunite> opportunites = opportuniteRepository.findBycommercial(commercial);
        return ObjectMapperUtils.mapAll(opportunites, OpportuniteDTO.class);
    }

}
