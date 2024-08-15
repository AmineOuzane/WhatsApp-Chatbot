package com.example.chatbotwhatsapp.service;

public interface MessageIdMappingService {

    void storeMapping(String messageId, Integer opportuniteId);
    Integer getOpportunityId(String messageId);
    void clearMapping();
    void logAllMappings();
    int getMapSize();
}
