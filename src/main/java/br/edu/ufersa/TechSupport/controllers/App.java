package br.edu.ufersa.TechSupport.controllers;

import br.edu.ufersa.TechSupport.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class App {
    @MessageMapping("/chatmessage")
    @SendTo("/chat")
    public Message sendMessage(Message message){
        return message;
    }
}
