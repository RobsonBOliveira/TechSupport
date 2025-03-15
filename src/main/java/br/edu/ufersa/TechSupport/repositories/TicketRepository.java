package br.edu.ufersa.TechSupport.repositories;

import br.edu.ufersa.TechSupport.model.Ticket;
import collections.ListaEncadeada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    ListaEncadeada<Ticket> findByUserId(Long id);
    List<Ticket> findByTechId(Long id);
    List<Ticket> findByIsCompleted(boolean completed);
}
