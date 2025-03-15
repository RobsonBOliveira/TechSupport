package br.edu.ufersa.TechSupport.controllers;

import br.edu.ufersa.TechSupport.model.User;
import br.edu.ufersa.TechSupport.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import collections.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
        public List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user;
        } else {
            return Optional.empty();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        Optional<User> userVerify = userRepository.findByEmail(user.getEmail());
        if(userVerify.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(user);
        } else {
            User savedUser = userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User newUser) {
        Optional<User> oldUser = userRepository.findById(id);
        if(oldUser.isPresent()) {
            oldUser.get().setEmail(newUser.getEmail());
            oldUser.get().setName(newUser.getName());
            oldUser.get().setPassword(newUser.getPassword());
            oldUser.get().setTech(newUser.isTech());
            userRepository.save(oldUser.get());
            return ResponseEntity.ok(oldUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            userRepository.delete(user.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
