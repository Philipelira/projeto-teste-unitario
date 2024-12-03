package com.cadastraai.service;

import com.cadastraai.model.User;
import com.cadastraai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User cadastraUser(User user) {
        if (userRepository.existeEmail(user.getEmail())) {
            throw new RuntimeException("Este email já foi cadastrado");
        }
        if (user.getSenha().length() < 8) {
            throw new RuntimeException("Senha muito curta");
        }
        return userRepository.save(user);
    }

    public boolean fazLogin(String email, String senha) {
        return userRepository.encontrarEmail(email)
                .map(u -> u.getSenha().equals(senha)) // Transforma o Optional<Usuario> em Optional<Boolean>
                .orElse(false); // Retorna false se o Optional estiver vazio
    }
}

