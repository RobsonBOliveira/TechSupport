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
        if (ticketList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ticketList, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> findTicketsWithNoTech() {
        List<Ticket> ticketList = ticketRepository.findByTechId(null);
        if (ticketList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ticketList, HttpStatus.OK);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Ticket>> getUserTickets(@PathVariable Long id) {
        ListaEncadeada<Ticket> ticketList = ticketRepository.findByUserId(id);
        if (ticketList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ticketList, HttpStatus.OK);
        }
    }

    @GetMapping("/tech/{id}")
    public ResponseEntity<List<Ticket>> getTechTickets(@PathVariable Long id) {
        List<Ticket> ticketList = ticketRepository.findByTechId(id);
        if (ticketList.isEmpty()) {
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

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody Ticket newTicket) {
        Optional<Ticket> oldTicket = ticketRepository.findById(id);
        if (oldTicket.isPresent()) {
            oldTicket.get().setTitle(newTicket.getTitle());
            oldTicket.get().setUserId(newTicket.getUserId());
            oldTicket.get().setTechId(newTicket.getTechId());
            oldTicket.get().setCompleted(newTicket.isCompleted());
            ticketRepository.save(oldTicket.get());
            return ResponseEntity.ok(oldTicket.get());
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
