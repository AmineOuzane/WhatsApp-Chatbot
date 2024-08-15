package com.example.chatbotwhatsapp.service.serviceImpl;

import com.example.chatbotwhatsapp.service.MessageIdMappingService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageIdMappingServiceImpl implements MessageIdMappingService {

    // Use ConcurrentHashMap for thread safety
    private final Map<String, Integer> messageIdToOpportunityIdMap = new ConcurrentHashMap<>();


    @Override
    public void storeMapping(String messageId, Integer opportuniteId) {
        System.out.println("Storing mapping: Message ID = " + messageId + ", Opportunity ID = " + opportuniteId);
        messageIdToOpportunityIdMap.put(messageId, opportuniteId);
        logAllMappings();
    }

    @Override
    public Integer getOpportunityId(String messageId) {
        return messageIdToOpportunityIdMap.get(messageId);
    }

    @Override
    public void clearMapping() {
        messageIdToOpportunityIdMap.clear();
    }

    public void logAllMappings() {
        System.out.println("Current message ID to Opportunity ID mappings:");
        messageIdToOpportunityIdMap.forEach((key, value) ->
                System.out.println("Message ID: " + key + " -> Opportunity ID: " + value));
    }

    public int getMapSize() {
        return messageIdToOpportunityIdMap.size();
    }
}
