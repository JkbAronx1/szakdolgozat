package com.szakdolgozat.receptmegoszto.controller;
import com.szakdolgozat.receptmegoszto.entity.User;
import com.szakdolgozat.receptmegoszto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}