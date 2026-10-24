package com.szakdolgozat.receptmegoszto.controller;

import com.szakdolgozat.receptmegoszto.entity.User;
import com.szakdolgozat.receptmegoszto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/api/uj-felhasznalo")
    public ResponseEntity<String> ujFelhasznaloMentes(@RequestParam String username,
                                                      @RequestParam String email,
                                                      @RequestParam String password) {

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hiba: Ez az e-mail cím már foglalt!");
        }

        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hiba: Ez a felhasználónév már foglalt!");
        }

        User ujFelhasznalo = new User();
        ujFelhasznalo.setUsername(username);
        ujFelhasznalo.setEmail(email);

        ujFelhasznalo.setPassword(passwordEncoder.encode(password));

        userRepository.save(ujFelhasznalo);

        return ResponseEntity.ok("Sikeres regisztráció!");
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginAdatok) {
        String email = loginAdatok.get("email");
        String password = loginAdatok.get("jelszo");

        Optional<User> felhasznalo = userRepository.findByEmail(email);

        if (felhasznalo.isPresent()) {
            // Itt használjuk a matches() függvényt az összehasonlításhoz
            // Az első paraméter a sima beírt jelszó, a második a titkosított jelszó az adatbázisból
            if (passwordEncoder.matches(password, felhasznalo.get().getPassword())) {
                return ResponseEntity.ok(felhasznalo.get());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Hibás jelszó!");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hiba: Ezzel az email címmel még senki sem regisztrált!");
        }
    }
}