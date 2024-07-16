package com.example.chatbotwhatsapp.repositories;

import com.example.chatbotwhatsapp.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByTelephone(String telephone);
    Optional<Utilisateur> findByNom(String name);


}
