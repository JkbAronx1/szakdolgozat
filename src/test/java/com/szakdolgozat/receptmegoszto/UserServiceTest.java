package com.szakdolgozat.receptmegoszto;

import com.szakdolgozat.receptmegoszto.controller.UserController;
import com.szakdolgozat.receptmegoszto.entity.User;
import com.szakdolgozat.receptmegoszto.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;

    @Test
    void tesztSikeresRegisztracio_EsJelszoTitkositas() {

        String nyersJelszo = "titkos123";
        String titkositottHash = "$2a$10$ValamiTitkositottBcryptHash";

        when(passwordEncoder.encode(nyersJelszo)).thenReturn(titkositottHash);

        when(userRepository.existsByEmail("teszt@gmail.com")).thenReturn(false);
        when(userRepository.existsByUsername("SzakdogaTeszt")).thenReturn(false);

        ResponseEntity<String> valasz = userController.ujFelhasznaloMentes("SzakdogaTeszt", "teszt@gmail.com", nyersJelszo);

        assertEquals(HttpStatus.OK, valasz.getStatusCode());
        assertEquals("Sikeres regisztráció!", valasz.getBody());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());

        User mentettUser = userCaptor.getValue();

        assertEquals(titkositottHash, mentettUser.getPassword());

        assertEquals("SzakdogaTeszt", mentettUser.getUsername());
        assertEquals("teszt@gmail.com", mentettUser.getEmail());
    }

    @Test
    void tesztRegisztracio_FoglaltEmail() {
        when(userRepository.existsByEmail("teszt@gmail.com")).thenReturn(true);

        ResponseEntity<String> valasz = userController.ujFelhasznaloMentes("UjUser", "teszt@gmail.com", "jelszo123");

        assertEquals(HttpStatus.BAD_REQUEST, valasz.getStatusCode());
        assertEquals("Hiba: Ez az e-mail cím már foglalt!", valasz.getBody());
    }

    @Test
    void tesztLogin_Sikeres() {
        User mockUser = new User();
        mockUser.setUsername("SzakdogaTeszt");
        mockUser.setPassword("titkositottHash");

        when(userRepository.findByEmail("teszt@gmail.com")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("titkos123", "titkositottHash")).thenReturn(true);

        Map<String, String> loginAdatok = Map.of(
                "email", "teszt@gmail.com",
                "jelszo", "titkos123"
        );

        ResponseEntity<?> valasz = userController.login(loginAdatok);

        assertEquals(HttpStatus.OK, valasz.getStatusCode());
        assertEquals(mockUser, valasz.getBody());
    }

    @Test
    void tesztLogin_HibasJelszo() {
        User mockUser = new User();
        mockUser.setPassword("titkositottHash");

        when(userRepository.findByEmail("teszt@gmail.com")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("rosszJelszo", "titkositottHash")).thenReturn(false);

        Map<String, String> loginAdatok = Map.of(
                "email", "teszt@gmail.com",
                "jelszo", "rosszJelszo"
        );

        ResponseEntity<?> valasz = userController.login(loginAdatok);

        assertEquals(HttpStatus.UNAUTHORIZED, valasz.getStatusCode());
        assertEquals("Hibás jelszó!", valasz.getBody());
    }
}