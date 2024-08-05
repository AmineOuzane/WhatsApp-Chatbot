package com.example.chatbotwhatsapp.web;


import com.example.chatbotwhatsapp.service.InteractionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private final InteractionService interactionService;

    public WebhookController(InteractionService interactionService) {
        this.interactionService = interactionService;
    }


    @PostMapping // Handles POST requests to /webhook
    public ResponseEntity<Map<String, String>> handleWebhook(@RequestBody Map<String, Object> payload) {
        // Log incoming message to the console
        System.out.println("Incoming webhook message: " + payload);

        // Retrieve the "entry" object from the payload
        Object entryObj = payload.get("entry");

        // Check if "entry" is a list
        if (entryObj instanceof List) {
            // Suppress unchecked warnings and cast "entryObj" to a List of maps
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> entries = (List<Map<String, Object>>) entryObj;

            // Iterate over each entry in the list
            for (Map<String, Object> entry : entries) {
                // Retrieve the "changes" object from the current entry
                Object changesObj = entry.get("changes");

                // Check if "changes" is a list
                if (changesObj instanceof List) {
                    // Suppress unchecked warnings and cast "changesObj" to a List of maps
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> changes = (List<Map<String, Object>>) changesObj;

                    // Iterate over each change in the list
                    for (Map<String, Object> change : changes) {
                        // Retrieve the "value" object from the current change
                        Object valueObj = change.get("value");

                        // Check if "value" is a map
                        if (valueObj instanceof Map) {
                            // Suppress unchecked warnings and cast "valueObj" to a Map
                            @SuppressWarnings("unchecked")
                            Map<String, Object> value = (Map<String, Object>) valueObj;

                            // Retrieve the "messages" object from the value map
                            Object messagesObj = value.get("messages");

                            // Check if "messages" is a list
                            if (messagesObj instanceof List) {
                                // Suppress unchecked warnings and cast "messagesObj" to a List of maps
                                @SuppressWarnings("unchecked")
                                List<Map<String, Object>> messages = (List<Map<String, Object>>) messagesObj;

                                // Iterate over each message in the list
                                for (Map<String, Object> message : messages) {
                                    // Retrieve the sender's phone number from the message
                                    String sender = (String) message.get("from");

                                    System.out.println("Sender phone number :" + sender);
                                    // Retrieve the "text" object from the message
                                    Object textObj = message.get("text");

                                    // Check if "text" is a map
                                    if (textObj instanceof Map) {
                                        // Suppress unchecked warnings and cast "textObj" to a Map
                                        @SuppressWarnings("unchecked")
                                        Map<String, Object> text = (Map<String, Object>) textObj;

                                        // Retrieve the message body from the text map
                                        String messageBody = (String) text.get("body");

                                        // If the message body is not null
                                        if (messageBody != null) {
                                            // Trim and convert the message body to lowercase
                                            messageBody = messageBody.trim().toLowerCase();

                                            // Handle the interaction based on the sender and message body
                                            interactionService.handleInteraction(sender, messageBody);

                                            // Create a response message
//                                            String responseMessage = "Your request has been processed.";

                                            // Return a successful response with the response message
//                                            return ResponseEntity.ok(Collections.singletonMap("message_to_send", responseMessage));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("error", "No valid message received"));
    }
}



