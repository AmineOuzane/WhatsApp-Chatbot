package com.example.chatbotwhatsapp.service;

import com.example.chatbotwhatsapp.enums.ChatState;
import com.example.chatbotwhatsapp.enums.statutOpp;
import com.example.chatbotwhatsapp.enums.statutPrj;

public interface InteractionService {

    void handleFirstInteraction(String phoneNumber);
    void handleInteraction(String phoneNumber, String incomingMessage);
    ChatState determineNewState(ChatState currentState, String userInput);
    void sendResponseBasedOnState(String state, String phoneNumber, String userInput);

}
