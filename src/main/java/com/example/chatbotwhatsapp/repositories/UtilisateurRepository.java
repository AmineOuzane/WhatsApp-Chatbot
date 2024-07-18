package com.example.chatbotwhatsapp.repositories;

import com.example.chatbotwhatsapp.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {

    Optional<Utilisateur> findByPhoneNumber(String phoneNumber);
    Optional<Utilisateur> findByNom(String name);


}
