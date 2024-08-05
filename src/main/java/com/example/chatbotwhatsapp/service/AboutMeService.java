package com.example.chatbotwhatsapp.service;

import org.springframework.http.ResponseEntity;

public interface AboutMeService {

    ResponseEntity<String> sendTemplateMessageWithUser(String phoneNumber);

}
