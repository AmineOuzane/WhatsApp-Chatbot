package com.example.chatbotwhatsapp.repositories;

import com.example.chatbotwhatsapp.entities.Opportunite;
import com.example.chatbotwhatsapp.entities.Utilisateur;
import com.example.chatbotwhatsapp.enums.statutOpp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OpportuniteRepository extends JpaRepository<Opportunite, Integer> {

    List<Opportunite> findBycommercial(Utilisateur commercial);

    @Query("SELECT o FROM Opportunite o WHERE o.statut = :status")
    List<Opportunite> findByStatut(@Param("status") statutOpp status);

    @Query("SELECT o FROM Opportunite o WHERE o.nom LIKE %:nom%")
    Opportunite findByName(@Param("nom") String name);

//    Opportunite updateOpportuniteByStatut(statutOpp statut);

}
