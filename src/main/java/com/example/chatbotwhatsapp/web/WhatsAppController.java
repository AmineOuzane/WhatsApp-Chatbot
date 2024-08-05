package com.example.chatbotwhatsapp.web;


import com.example.chatbotwhatsapp.enums.statutPrj;
import com.example.chatbotwhatsapp.service.*;
import com.example.chatbotwhatsapp.enums.statutOpp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/whatsapp")
public class WhatsAppController {

    @Autowired
    private final messageSenderService messageService;
    private final SenderStatusService senderStatusService;
    private final AboutMeService aboutMeService;
    private final DetailsOpportuniteService detailsOpportuniteService;
    private final DetailsProjetService detailsProjetService;

    public WhatsAppController(messageSenderService messageService, SenderStatusService senderStatusService, AboutMeService aboutMeService, DetailsOpportuniteService detailsOpportuniteService, DetailsProjetService detailsProjetService, DetailsProjetService detailsProjetService1, DetailsProjetService detailsProjetService2) {
        this.messageService = messageService;
        this.senderStatusService = senderStatusService;
        this.aboutMeService = aboutMeService;
        this.detailsOpportuniteService = detailsOpportuniteService;
        this.detailsProjetService = detailsProjetService2;
    }


    @PostMapping("/sendTemplate")
    public ResponseEntity<String> sendTemplateMessage() {
        // Handle response if needed
        return messageService.sendTemplateMessage();
    }

    @PostMapping("/Inconnu")
    public ResponseEntity<String> inconnu() {
        return messageService.sendInconnuMessage();
    }

    @PostMapping("/choixProjet")
    public ResponseEntity<String> choixProjet() {
        return messageService.sendChoixProjet();
    }

    @PostMapping("/choixOpportunite")
    public ResponseEntity<String> choixOpportunite() {
        return messageService.sendChoixOpportunite();
    }

    @PostMapping("/CommercialouChef")
    public ResponseEntity<String> CommercialouChef() {
        return messageService.projetCommercialouChefProjet();
    }

    @PostMapping("/sendTemplateWithUserNoProjet/{phoneNumber}")
    public ResponseEntity<String> sendTemplateMessageNoProjet(@PathVariable String phoneNumber) {
        return messageService.sendTemplateMessageWithUserNoProjet(phoneNumber);
    }

    @PostMapping("/sendTemplateWithUserNoOpp/{phoneNumber}")
    public ResponseEntity<String> sendTemplateMessageNoOpp(@PathVariable String phoneNumber) {
        return messageService.sendTemplateMessageWithUserNoOpportunite(phoneNumber);
    }

    @PostMapping("/sendTemplateWithOpportunite/{phoneNumber}")
    public ResponseEntity<String> sendTemplateMessageWithOpportunite(@PathVariable String phoneNumber) {
        return messageService.sendTemplateMessageWithUserAndOpportunite(phoneNumber);
    }

    @PostMapping("/sendTemplateWithProjetCommercial/{phoneNumber}")
    public ResponseEntity<String> sendTemplateMessageWithProjetCommercial(@PathVariable String phoneNumber) {
        return messageService.sendTemplateMessageWithUserAndProjetcommercial(phoneNumber);
    }

    @PostMapping("/sendTemplateWithProjetChefProjet/{phoneNumber}")
    public ResponseEntity<String> sendTemplateMessageWithProjetChefProjet(@PathVariable String phoneNumber) {
        return messageService.sendTemplateMessageWithUserAndProjetchefProjet(phoneNumber);
    }

    @PostMapping("/saisirOpportuniteStatus")
    public ResponseEntity<String> saisirOpportuniteStatus() {
        return senderStatusService.saisirOpportuniteStatus();
    }

    @PostMapping("/saisirProjetStatus")
    public ResponseEntity<String> saisirProjetStatus() {
        return senderStatusService.saisirProjetStatus();
    }

    @PostMapping("/noOpportuniteStatus/{status}")
    public ResponseEntity<String> noOpportuniteStatus(@PathVariable String status) {
        statutOpp statutOpp = com.example.chatbotwhatsapp.enums.statutOpp.valueOf(status.toUpperCase());
        return senderStatusService.sendNoOpportuniteStatus(statutOpp);
    }

    @PostMapping("/noProjetStatus/{status}")
    public ResponseEntity<String> noProjetStatus(@PathVariable String status) {
        statutPrj statutPrj = com.example.chatbotwhatsapp.enums.statutPrj.valueOf(status.toUpperCase());
        return senderStatusService.sendNoProjetStatus(statutPrj);
    }

    @PostMapping("/OpportuniteStatus/{status}")
    public ResponseEntity<String> sendOpportuniteStatus(@PathVariable String status) {
        statutOpp statutOpp = com.example.chatbotwhatsapp.enums.statutOpp.valueOf(status.toUpperCase());
        return senderStatusService.sendOpportuniteStatus(statutOpp);
    }

    @PostMapping("/ProjetStatus/{status}")
    public ResponseEntity<String> sendProjetStatus(@PathVariable String status) {
        statutPrj statutPrj = com.example.chatbotwhatsapp.enums.statutPrj.valueOf(status.toUpperCase());
        return senderStatusService.sendProjetStatus(statutPrj);
    }

    @PostMapping("/sendTemplateUser/{phoneNumber}")
    public ResponseEntity<String> sendTemplateUser(@PathVariable String phoneNumber) {
        return aboutMeService.sendTemplateMessageWithUser(phoneNumber);
    }

    @PostMapping("/saisirProjetNom")
    public ResponseEntity<String> saisirProjetNom() {
        return detailsProjetService.saisirNomProjet();
    }

    @PostMapping("/saisirOpportuniteNom")
    public ResponseEntity<String> saisirOpportuniteNom() {
        return detailsOpportuniteService.saisirNomOpportunite();
    }

    @PostMapping("/noOpportuniteName/{nom}")
    public ResponseEntity<String> noOpportuniteName(@PathVariable String nom) {
        return detailsOpportuniteService.NoOpportuniteFound(nom);
    }

    @PostMapping("/OpportuniteName/{nom}")
    public ResponseEntity<String> OpportuniteName(@PathVariable String nom) {
        return detailsOpportuniteService.sendOpportuniteDetails(nom);
    }

    @PostMapping("/noProjetName/{nom}")
    public ResponseEntity<String> noProjetName(@PathVariable String nom) {
        return detailsProjetService.NoProjetFound(nom);
    }

    @PostMapping("/ProjetName/{nom}")
    public ResponseEntity<String> ProjetName(@PathVariable String nom) {
        return detailsProjetService.sendProjetDetails(nom);
    }

}