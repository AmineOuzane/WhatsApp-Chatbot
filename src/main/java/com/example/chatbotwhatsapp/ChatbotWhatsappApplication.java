package com.example.chatbotwhatsapp;

import com.example.chatbotwhatsapp.entities.Opportunite;
import com.example.chatbotwhatsapp.entities.Projet;
import com.example.chatbotwhatsapp.entities.Utilisateur;
import com.example.chatbotwhatsapp.enums.statutOpp;
import com.example.chatbotwhatsapp.enums.statutPrj;
import com.example.chatbotwhatsapp.repositories.OpportuniteRepository;
import com.example.chatbotwhatsapp.repositories.ProjetRepository;
import com.example.chatbotwhatsapp.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@SpringBootApplication
public class ChatbotWhatsappApplication {

    @Value("${recipient_number}")
    private String recipientNumber;

    public static void main(String[] args) {
        SpringApplication.run(ChatbotWhatsappApplication.class, args);
    }

    @Bean
    CommandLineRunner start(UtilisateurRepository utilisateurRepository,
                            OpportuniteRepository opportuniteRepository,
                            ProjetRepository projetRepository ) {



        return args -> {

                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setPhoneNumber(recipientNumber);
                utilisateur.setNom("Amine");
                utilisateur.setPrenom("Ouzane");
                utilisateur.setEmail("amineouzane10@gmail.com");
                utilisateurRepository.save(utilisateur);

            System.out.println("User phone number: " + utilisateur.getPhoneNumber() );


            Stream.of("Hassan", "Yassine", "Aicha", "Amine", "Omar", "Fatima").forEach(name -> {
                Utilisateur utilisateur2 = new Utilisateur();
                utilisateur2.setNom(name);
            });


            // List Opportunite
            Optional<Utilisateur> commercial_phoneNumber = utilisateurRepository.findByPhoneNumber("+212628402453");
            Utilisateur commercialUser = commercial_phoneNumber.orElse(null); // unwrap the Optional
            Stream.of("Dell", "Asos", "Samsung", "HP", "Munisys", "Orange").forEach(name -> {
                Opportunite opportunite1 = new Opportunite();
                opportunite1.setClient(name);
                opportunite1.setDate(new Date());
                opportunite1.setNom("Opportunite " + name);
                opportunite1.setCommercial(commercialUser);
                opportunite1.setDescription("Opportunite Description");
                opportunite1.setStatut(statutOpp.APPROUVE);
                opportuniteRepository.save(opportunite1);
            });

            // List Projet
            Optional<Utilisateur> commercialUserProjet = utilisateurRepository.findByNom("commercialUsernameProjet");
            Optional<Utilisateur> chefProjetlUser = utilisateurRepository.findByNom("chefProjetUsername");
            Stream.of("Dell", "Asos", "Samsung", "HP", "Munisys", "Orange").forEach(name -> {
                Projet projet1 = new Projet();
                projet1.setClient(name);
                projet1.setCommercial(commercialUserProjet);
                projet1.setDate(new Date());
                projet1.setDescription("Project 1");
                projet1.setChefProjet(chefProjetlUser);
                projet1.setStatut(statutPrj.LANCEMENT);
                projetRepository.save(projet1);
            });

        System.out.println("System is running on port 8083");

            ;


        };
    }
}
