package br.edu.ufersa.TechSupport.controllers;

import br.edu.ufersa.TechSupport.model.Ticket;
import br.edu.ufersa.TechSupport.repositories.TicketRepository;
import collections.ListaEncadeada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/{completed}")
    public ResponseEntity<List<Ticket>> findNotCompletedTickets(@PathVariable boolean completed) {
        List<Ticket> ticketList = ticketRepository.findByIsCompleted(completed);
        ListaEncadeada<Ticket> listaEncadeada = new ListaEncadeada<>();
        listaEncadeada.addAll(ticketList);
        if (listaEncadeada.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ticketList, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> findTicketsWithNoTech() {
        List<Ticket> ticketList = ticketRepository.findByTechId(null);
        ListaEncadeada<Ticket> listaEncadeada = new ListaEncadeada<>();
        listaEncadeada.addAll(ticketList);
        if (listaEncadeada.isEmpty()) {
            System.out.println("Vazio!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ticketList, HttpStatus.OK);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Ticket>> getUserTickets(@PathVariable Long id) {
        List<Ticket> ticketList = ticketRepository.findByUserId(id);
        ListaEncadeada<Ticket> listaEncadeada = new ListaEncadeada<>();
        listaEncadeada.addAll(ticketList);
        if (listaEncadeada.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ticketList, HttpStatus.OK);
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(ticket.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tech/{id}")
    public ResponseEntity<List<Ticket>> getTechTickets(@PathVariable Long id) {
        List<Ticket> ticketList = ticketRepository.findByTechId(id);
        ListaEncadeada<Ticket> listaEncadeada = new ListaEncadeada<>();
        listaEncadeada.addAll(ticketList);
        if (listaEncadeada.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ticketList, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket ticketSaved = ticketRepository.save(ticket);
        return ResponseEntity.status((HttpStatus.CREATED)).body(ticketSaved);
    }

    @PutMapping("/{techId}")
    public ResponseEntity<Ticket> setTicketTechId(@PathVariable Long techId, @RequestBody Ticket ticket) {
        Optional<Ticket> savedTicket = ticketRepository.findById(ticket.getId());
        if (savedTicket.isPresent()) {
            savedTicket.get().setTechId(techId);
            savedTicket.get().setCompleted(ticket.isCompleted());
            ticketRepository.save(savedTicket.get());
            return ResponseEntity.ok(savedTicket.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isPresent()) {
            ticketRepository.delete(ticket.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
