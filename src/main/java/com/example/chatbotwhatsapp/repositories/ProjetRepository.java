package com.example.chatbotwhatsapp.repositories;

import com.example.chatbotwhatsapp.entities.Opportunite;
import com.example.chatbotwhatsapp.entities.Projet;
import com.example.chatbotwhatsapp.entities.Utilisateur;
import com.example.chatbotwhatsapp.enums.statutPrj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjetRepository extends JpaRepository<Projet, Integer> {

    List<Projet> findBychefProjet(Utilisateur chefProjet);
    List<Projet> findByCommercial(Utilisateur commercial);

    @Query("SELECT p FROM Projet p WHERE p.statut = :status")
    List<Projet> findByStatut(@Param("status") statutPrj status);

    @Query("SELECT p FROM Projet p WHERE p.nom LIKE %:nom%")
    Projet findByName(@Param("nom") String name);
}
