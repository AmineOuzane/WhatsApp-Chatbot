package com.example.chatbotwhatsapp.service.serviceImpl;


import com.example.chatbotwhatsapp.dtos.ProjetDTO;
import com.example.chatbotwhatsapp.entities.Projet;
import com.example.chatbotwhatsapp.entities.Utilisateur;
import com.example.chatbotwhatsapp.repositories.ProjetRepository;
import com.example.chatbotwhatsapp.service.ProjetService;
import com.example.chatbotwhatsapp.utils.ObjectMapperUtils;
import jdk.jshell.execution.Util;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetServiceImpl implements ProjetService {

    @Autowired
    private ProjetRepository projetRepository;

    @Override
    public List<ProjetDTO> getAllProjet() {
        List<Projet> projetList=projetRepository.findAll();
        return ObjectMapperUtils.mapAll(projetList,ProjetDTO.class);
    }

    @Override
    public ProjetDTO getProjetById(int idProjet) {
        Optional<Projet> searchedProjet=projetRepository.findById(idProjet);
        return searchedProjet.map(projet -> ObjectMapperUtils.map(projet, ProjetDTO.class)).orElse(null);
    }


    public List<ProjetDTO> findBychefProjet(Utilisateur chefProjet) {
        List<Projet> projetsChefProjet = projetRepository.findBychefProjet(chefProjet);
        return ObjectMapperUtils.mapAll(projetsChefProjet, ProjetDTO.class);
    }

    @Override
    public List<ProjetDTO> findByCommercial(Utilisateur commercial) {
        List<Projet> projetsCommercial = projetRepository.findBychefProjet(commercial);
        return ObjectMapperUtils.mapAll(projetsCommercial, ProjetDTO.class);

}
}

