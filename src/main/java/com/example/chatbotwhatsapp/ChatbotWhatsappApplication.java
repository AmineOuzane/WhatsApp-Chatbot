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


//            Stream.of("Hassan", "Yassine", "Aicha", "Amine", "Omar", "Fatima").forEach(name -> {
//                Utilisateur utilisateur2 = new Utilisateur();
//                utilisateur2.setNom(name);
//            });


            // List Opportunite
            Optional<Utilisateur> commercial_phoneNumber = utilisateurRepository.findByPhoneNumber("+212628402453");
            Utilisateur commercialUser = commercial_phoneNumber.orElse(null); // unwrap the Optional
            Stream.of("Dell","Samsung", "HP", "Munisys").forEach(name -> {
                Opportunite opportunite1 = new Opportunite();
                opportunite1.setClient(name);
                opportunite1.setDate(new Date());
                opportunite1.setNom("Opportunite " + name);
                opportunite1.setCommercial(commercialUser);
                opportunite1.setDescription("Opportunite Description");
                opportunite1.setStatut(statutOpp.APPROUVE);
                opportuniteRepository.save(opportunite1);
            });


            // List Opportunite
            Optional<Utilisateur> commercial_phoneNumber2 = utilisateurRepository.findByPhoneNumber("+212628402453");
            Utilisateur commercialUser2 = commercial_phoneNumber.orElse(null); // unwrap the Optional
            Stream.of("Asus","Lenovo").forEach(name -> {
                Opportunite opportunite1 = new Opportunite();
                opportunite1.setClient(name);
                opportunite1.setDate(new Date());
                opportunite1.setNom("Opportunite " + name);
                opportunite1.setCommercial(commercialUser2);
                opportunite1.setDescription("Opportunite Description");
                opportuniteRepository.save(opportunite1);
            });

            // List Projet

            Optional<Utilisateur> projetCommercial_phoneNumber = utilisateurRepository.findByPhoneNumber("+212628402453");
            Utilisateur projetCommercialUser = projetCommercial_phoneNumber.orElse(null); // unwrap the Optional

            Optional<Utilisateur> projetChef_phoneNumber = utilisateurRepository.findByPhoneNumber("+212628402453");
            Utilisateur projetChefUser = projetChef_phoneNumber.orElse(null); // unwrap the Optional

            Stream.of("Dell", "Asos", "Samsung", "HP", "Munisys", "Orange").forEach(name -> {
                Projet projet1 = new Projet();
                projet1.setClient(name);
                projet1.setCommercial(projetCommercialUser);
                projet1.setDate(new Date());
                projet1.setNom("Projet " + name);
                projet1.setDescription("Project Description");
                projet1.setChefProjet(projetChefUser);
                projet1.setStatut(statutPrj.LANCEMENT);
                projetRepository.save(projet1);
            });

        System.out.println("System is running on port 8083");
        };
    }
}
