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
import org.springframework.security.crypto.password.PasswordEncoder;

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
        // 1. Előkészítés (Arrange)
        String nyersJelszo = "titkos123";
        String titkositottHash = "$2a$10$ValamiTitkositottBcryptHash";

        // Megmondjuk a tesztkörnyezetnek, hogy mit csináljon a jelszóval
        when(passwordEncoder.encode(nyersJelszo)).thenReturn(titkositottHash);

        // 2. Futtatás (Act)
        // Meghívjuk a TE saját metódusodat a különálló paraméterekkel
        String valasz = userController.ujFelhasznaloMentes("SzakdogaTeszt", "teszt@gmail.com", nyersJelszo);

        // 3. Ellenőrzés (Assert)
        // a) Ellenőrizzük, hogy a Controller jó üzenetet adott-e vissza
        assertEquals("Sikeres regisztráció!", valasz);

        // b) Elkapjuk azt a User objektumot, amit a Controller létrehozott és menteni próbált
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());

        User mentettUser = userCaptor.getValue();

        // c) A legfontosabb: ellenőrizzük, hogy a jelszó tényleg titkosítva van-e a mentett objektumban
        assertEquals(titkositottHash, mentettUser.getPassword());

        // Ráadásként ellenőrizzük a többi adatot is
        assertEquals("SzakdogaTeszt", mentettUser.getUsername());
        assertEquals("teszt@gmail.com", mentettUser.getEmail());
    }
}