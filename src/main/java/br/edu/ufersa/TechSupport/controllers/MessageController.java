package br.edu.ufersa.TechSupport.controllers;

import br.edu.ufersa.TechSupport.model.Message;
import br.edu.ufersa.TechSupport.repositories.MessageRepository;
import collections.ListaEncadeada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Stack;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/{ticketId}")
    public ResponseEntity<Stack<Message>> findByTicketId(@PathVariable Long ticketId) {
        Stack<Message> messages = messageRepository.findByTicketId(ticketId);
        //ListaEncadeada<Message> messagesEncadeada = new ListaEncadeada<>();
        //messagesEncadeada.addAll(messages);
        if (messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(messages, HttpStatus.OK);
        }
    }

    /*@GetMapping("/pdf/{tickedId}")
    public ResponseEntity generatePdf(@PathVariable Long ticketId) {
        List<Message> messages = messageRepository.findByTicketId(ticketId);
        for (Message message : messages) {

        }
    }*/

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message savedMessage = messageRepository.save(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
    }

}
