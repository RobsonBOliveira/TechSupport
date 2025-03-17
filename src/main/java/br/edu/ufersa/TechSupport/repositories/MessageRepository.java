package br.edu.ufersa.TechSupport.repositories;

import br.edu.ufersa.TechSupport.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Stack;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Stack<Message> findByTicketId(Long id);
}
