package com.example.chatbotwhatsapp.service;

import com.example.chatbotwhatsapp.enums.ChatState;

public interface InteractionService {

    void handleFirstInteraction(String phoneNumber);
    void handleInteraction(String phoneNumber, String incomingMessage);
    ChatState determineNewState(ChatState currentState, String userInput);
    void sendResponseBasedOnState(String state, String phoneNumber, String userInput);

}
