package com.example.chatbotwhatsapp.repositories;

import com.example.chatbotwhatsapp.entities.Projet;
import com.example.chatbotwhatsapp.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjetRepository extends JpaRepository<Projet, Integer> {

    List<Projet> findBychefProjet(Utilisateur chefProjet);
    List<Projet> findByCommercial(Utilisateur commercial);

}
