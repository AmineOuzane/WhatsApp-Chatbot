package com.example.chatbotwhatsapp.service.serviceImpl;

import com.example.chatbotwhatsapp.service.MessageParserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageParserServiceImpl implements MessageParserService {

    private final ObjectMapper objectMapper;


    @Override
    public void parseMessage(String jsonMessage) {
        try {
            // Parse the incoming JSON message
            JsonNode rootNode = objectMapper.readTree(jsonMessage);

            // Extract necessary fields from the JSON message
            String fromNumber = rootNode.path("from").asText();
            String messageBody = rootNode.path("message").path("body").asText();
            String messageType = rootNode.path("message").path("type").asText();

            // Perform actions on message Type
            if("text".equals(messageType)) {
                processTextMessage(fromNumber,messageBody);
            } else if("image".equals(messageType)) {
                processImageMessage(fromNumber, messageBody);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void processTextMessage(String fromNumber, String messageBody) {

        System.out.println("Received message from"+ fromNumber + ": " + messageBody);
        // Implement your business logic here based on the received text message
        // Example: Call a service to respond to the message or store it in a database
    }

    private void processImageMessage(String fromNumber, String imageURL) {

        System.out.println("Received message from"+ fromNumber + ": " + imageURL);
        // Implement your business logic here based on the received image
        // Example: Process the image, save it, or send a response back to the user
    }
}
