package com.example.chatbotwhatsapp.repositories;

import com.example.chatbotwhatsapp.entities.Opportunite;
import com.example.chatbotwhatsapp.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpportuniteRepository extends JpaRepository<Opportunite, Integer> {

    List<Opportunite> findBycommercial(Utilisateur commercial);
}
