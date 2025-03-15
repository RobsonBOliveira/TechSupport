package br.edu.ufersa.TechSupport.controllers;

import br.edu.ufersa.TechSupport.model.Message;
import br.edu.ufersa.TechSupport.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/{ticketId}")
    public List<Message> findByTicketId(@PathVariable Long ticketId) {
        return messageRepository.findByTicketId(ticketId);
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message savedMessage = messageRepository.save(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
    }

}
