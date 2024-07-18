package com.example.chatbotwhatsapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutGoingWhatsAppMessage {

    // This class is likely used to construct a WhatsApp message that will be sent to a recipient using a pre-approved template

    private String messagingProduct = "whatsapp";
    private String to; // recipient's phone number
    private String type = "template";
    private OutgoingTemplate template;
    private TemplateParameters parameters;
}
