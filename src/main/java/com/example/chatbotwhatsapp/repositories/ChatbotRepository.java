package com.example.chatbotwhatsapp.repositories;

import com.example.chatbotwhatsapp.entities.Chatbot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatbotRepository extends JpaRepository<Chatbot, Integer> {
}
