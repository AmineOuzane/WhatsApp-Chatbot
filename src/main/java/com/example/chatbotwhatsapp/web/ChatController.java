package com.example.chatbotwhatsapp.web;


import com.example.chatbotwhatsapp.dtos.OpportuniteDTO;
import com.example.chatbotwhatsapp.dtos.ProjetDTO;
import com.example.chatbotwhatsapp.dtos.UtilisateurDTO;
import com.example.chatbotwhatsapp.entities.Projet;
import com.example.chatbotwhatsapp.entities.Utilisateur;
import com.example.chatbotwhatsapp.service.ChatService;
import com.example.chatbotwhatsapp.service.OpportuniteService;
import com.example.chatbotwhatsapp.service.ProjetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class ChatController {

    private ChatService chatService;
    private ProjetService projetService;
    private OpportuniteService opportuniteService;


    @GetMapping("/authenticate")
    public ResponseEntity<Optional<UtilisateurDTO>> authenticate(@RequestParam String phoneNumber) {
        Optional<UtilisateurDTO> user = chatService.authenticateUser(phoneNumber);

        // Check if user is present in Optional
        if (user.isPresent()) {
            return ResponseEntity.ok(user);
        } else {
            // Request has not been applied because it lacks valid authentication credentials for the target resource
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/projects/{chefProjets}")
    public ResponseEntity<List<ProjetDTO>> listProjectsForUserChefProjet(@RequestParam Utilisateur chefProjet) {
        List<ProjetDTO> chefProjetProjets = chatService.listProjectsForUserChefProjet(chefProjet);
        return ResponseEntity.ok(chefProjetProjets);
    }

    @GetMapping("/projects/{commercial}")
    public ResponseEntity<List<ProjetDTO>> listProjectsForUserCommercial(@RequestParam Utilisateur commercial) {
        List<ProjetDTO> commercialProjets = chatService.listProjectsForUserChefProjet(commercial);
        return ResponseEntity.ok(commercialProjets);
    }

    @GetMapping("/opportunities/{commercial}")
    public ResponseEntity<List<OpportuniteDTO>> listOpportunitiesForUser(@RequestParam Utilisateur commercial) {
        List<OpportuniteDTO> opportunities = chatService.listOpportunitesForUser(commercial);
        return ResponseEntity.ok(opportunities);
    }

    @GetMapping("/opportunite")
    public ResponseEntity<List<OpportuniteDTO>> listOpportunities() {
        List<OpportuniteDTO> listOpportunite = opportuniteService.getAllOpportunites();
        return ResponseEntity.ok(listOpportunite);
    }

    @GetMapping("/projet")
    public ResponseEntity<List<ProjetDTO>> allProjet () {
        List<ProjetDTO> listProjets = projetService.getAllProjet();
        return ResponseEntity.ok(listProjets);
    }


}
