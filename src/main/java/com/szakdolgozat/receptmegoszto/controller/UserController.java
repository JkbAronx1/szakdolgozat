package com.szakdolgozat.receptmegoszto.controller;

import com.szakdolgozat.receptmegoszto.entity.User;
import com.szakdolgozat.receptmegoszto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200") 
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/uj-felhasznalo")
    public String ujFelhasznaloMentes(@RequestParam String username,
                                      @RequestParam String email,
                                      @RequestParam String password) {

        User ujFelhasznalo = new User();
        ujFelhasznalo.setUsername(username);
        ujFelhasznalo.setEmail(email);
        ujFelhasznalo.setPassword(password);

        userRepository.save(ujFelhasznalo);

        return "Sikeres regisztráció!";
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginAdatok) {
        String email = loginAdatok.get("email");
        String password = loginAdatok.get("jelszo");

        Optional<User> felhasznalo = userRepository.findByEmail(email);

        if (felhasznalo.isPresent()) {
            if (felhasznalo.get().getPassword().equals(password)) {
                return ResponseEntity.ok(felhasznalo.get());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Hibás jelszó!");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hiba: Ezzel az email címmel még senki sem regisztrált!");
        }
    }
}