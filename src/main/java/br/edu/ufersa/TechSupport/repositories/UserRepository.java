package br.edu.ufersa.TechSupport.repositories;

import br.edu.ufersa.TechSupport.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import collections.*;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    public ListaEncadeada<User> getAll();
}
