package com.cadastraai.service;

import com.cadastraai.model.User;
import com.cadastraai.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void cadastrarUserComSucesso() {
        User user = new User(null, "Philipe", "philipe@email.com", "senha123");

        when(userRepository.existeEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.cadastraUser(user);

        assertNotNull(result);
        assertEquals("Philipe", result.getNome());
    }

    @Test
    public void rejeitarCadastroUserEmailDuplicado() {
        User user = new User(null, "Philipe", "philipe@email.com", "senha123");

        when(userRepository.existeEmail(user.getEmail())).thenReturn(true);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.cadastraUser(user);
        });

        assertEquals("Este email já foi cadastrado", exception.getMessage());
    }

    @Test
    public void fazerLoginUserSucesso() {
        User user = new User(1L, "Lucas", "lucas@email.com", "senha123");

        when(userRepository.encontrarEmail(user.getEmail())).thenReturn(Optional.of(user));

        boolean resultado = userService.fazLogin(user.getEmail(), "senha123");

        assertTrue(resultado);
    }

    @Test
    public void failLoginUserCredenciaisInvalidas() {
        when(userRepository.encontrarEmail("notfound@email.com")).thenReturn(Optional.empty());

        boolean resultado = userService.fazLogin("notfound@email.com", "password123");

        assertFalse(resultado);
    }
}
