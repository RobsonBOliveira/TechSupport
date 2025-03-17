package br.edu.ufersa.TechSupport.controllers;

import br.edu.ufersa.TechSupport.model.Message;
import br.edu.ufersa.TechSupport.repositories.MessageRepository;
import collections.ListaEncadeada;
import collections.Pilha;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.event.ListDataEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Stack;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/{ticketId}")
    public ResponseEntity<List<Message>> findByTicketId(@PathVariable Long ticketId) {
        List<Message> messages = messageRepository.findByTicketId(ticketId);
        //ListaEncadeada<Message> messagesEncadeada = new ListaEncadeada<>();
        //messagesEncadeada.addAll(messages);
        if (messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(messages, HttpStatus.OK);
        }
    }

    @GetMapping("/pdf/{ticketId}")
    public ResponseEntity<ByteArrayResource> generatePdf(@PathVariable Long ticketId) {
        try {
            // Buscar mensagens associadas ao ticket
            List<Message> messages = messageRepository.findByTicketId(ticketId);
            Pilha<Message> messagePilha = new Pilha<>(messages.size());

            if (messages.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            for(Message message : messages) {
                messagePilha.push(message);
            }

            // Criar documento PDF
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Criar StringBuilder para armazenar conteúdo do TXT
            StringBuilder txtContent = new StringBuilder();
            txtContent.append("Conversa do Ticket #").append(ticketId).append("\n\n");

            // Adicionar título ao PDF
            try {
                document.add(new Paragraph("Conversa do Ticket #" + ticketId + "\n\n"));
            } catch (Exception e) {
                document.close();
            }

            // Criar segunda pilha para inverter a ordem das mensagens
            Pilha<Message> messagePilha2 = new Pilha<>(messages.size());
            while (!messagePilha.isEmpty()) {
                messagePilha2.push(messagePilha.pop());
            }

            // Adicionar mensagens ao PDF e ao TXT
            while (!messagePilha2.isEmpty()) {
                Message message = messagePilha2.pop();
                String line = message.getSenderName() + ": " + message.getContent() + "\n";

                try {
                    document.add(new Paragraph(line));
                } catch (Exception e) {
                    document.close();
                }

                txtContent.append(line);
            }

            document.close();

            // Criar arquivo TXT na pasta raiz do projeto
            String filePath = System.getProperty("user.dir") + "/conversa_ticket_" + ticketId + ".txt";
            Files.write(Paths.get(filePath), txtContent.toString().getBytes());

            // Criar recurso do PDF para retorno
            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=conversa_ticket_" + ticketId + ".pdf")
                    .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                    .body(resource);

        } catch (DocumentException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping
    public ResponseEntity<Message> save(@RequestBody Message message) {
        Message saved = messageRepository.save(message);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
