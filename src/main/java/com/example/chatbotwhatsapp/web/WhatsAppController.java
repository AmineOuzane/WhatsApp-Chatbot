package com.example.chatbotwhatsapp.web;


import com.example.chatbotwhatsapp.service.UtilisateurService;
import com.example.chatbotwhatsapp.service.messageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/whatsapp")
public class WhatsAppController {

    private final messageSenderService messageService;
    private final UtilisateurService utilisateurService;

    public WhatsAppController(messageSenderService messageService, UtilisateurService utilisateurService) {
        this.messageService = messageService;
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/sendTemplate")
    public ResponseEntity<String> sendTemplateMessage() {
        // Handle response if needed
        return messageService.sendTemplateMessage();
    }

    @PostMapping("/sendTemplateWithUser/{phoneNumber}")
    public ResponseEntity<String> sendTemplateMessage(@PathVariable String phoneNumber) {
        return messageService.sendTemplateMessageWithUser(phoneNumber);
    }

    @PostMapping("/sendTemplateWithOpportunite/{phoneNumber}")
    public ResponseEntity<String> sendTemplateMessageWithOpportunite(@PathVariable String phoneNumber) {
        return messageService.sendTemplateMessageWithUserAndOpportunite(phoneNumber); }
}