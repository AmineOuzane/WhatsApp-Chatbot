package com.example.chatbotwhatsapp;

import com.example.chatbotwhatsapp.entities.Opportunite;
import com.example.chatbotwhatsapp.entities.Projet;
import com.example.chatbotwhatsapp.entities.Utilisateur;
import com.example.chatbotwhatsapp.enums.statutOpp;
import com.example.chatbotwhatsapp.enums.statutPrj;
import com.example.chatbotwhatsapp.repositories.OpportuniteRepository;
import com.example.chatbotwhatsapp.repositories.ProjetRepository;
import com.example.chatbotwhatsapp.repositories.UtilisateurRepository;
import com.example.chatbotwhatsapp.service.WhatsAppService;
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

    public static void main(String[] args) {
        SpringApplication.run(ChatbotWhatsappApplication.class, args);
    }

    @Bean
    CommandLineRunner start(UtilisateurRepository utilisateurRepository,
                            OpportuniteRepository opportuniteRepository,
                            ProjetRepository projetRepository,
                            WhatsAppService whatsAppService) {

        return args -> {
            // List Utilisateur
            Stream.of("Hassan", "Yassine", "Aicha", "Amine", "Omar", "Fatima").forEach(name -> {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setNom(name);
                utilisateur.setPrenom("Benjelloun");
                utilisateur.setEmail(name.toLowerCase() + "@gmail.com");
                utilisateur.setTelephone(generatePhoneNumber());
                utilisateurRepository.save(utilisateur);
            });

            Stream.of("Hassan", "Yassine", "Aicha", "Amine", "Omar", "Fatima").forEach(name -> {
                Utilisateur utilisateur2 = new Utilisateur();
                utilisateur2.setNom(name);
            });


            // List Opportunite
            Optional<Utilisateur> commercialUserOpportunite = utilisateurRepository.findByNom("commercialUsernameOpportunite");
            Stream.of("Dell", "Asos", "Samsung", "HP", "Munisys", "Orange").forEach(name -> {
                Opportunite opportunite1 = new Opportunite();
                opportunite1.setClient(name);
                opportunite1.setDate(new Date());
                opportunite1.setCommercial(commercialUserOpportunite);
                opportunite1.setDescription("Opportunite 1");
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

        };
    }

    private String generatePhoneNumber() {
        String prefix = "06";
        int num1 = ThreadLocalRandom.current().nextInt(600, 700);
        int num2 = ThreadLocalRandom.current().nextInt(0, 1000);
        String formattedNum2 = String.format("%03d", num2);
        int num3 = ThreadLocalRandom.current().nextInt(0, 10000);
        String formattedNum3 = String.format("%04d", num3);
        return prefix + num1 + formattedNum2 + formattedNum3;
    }
}
