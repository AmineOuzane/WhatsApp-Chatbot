package com.example.chatbotwhatsapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TemplateParameters {

//    This class represents the dynamic parameters that will be replaced in the WhatsApp template
    private String parameter1;
    private String parameter2;
}
