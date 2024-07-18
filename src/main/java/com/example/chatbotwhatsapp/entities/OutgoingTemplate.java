package com.example.chatbotwhatsapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutgoingTemplate {

    //This class is used to define the template that will be used for the outgoing WhatsApp message.

    private String name; // Template name
    private Language language;

}
