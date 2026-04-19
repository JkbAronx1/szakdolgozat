package com.szakdolgozat.receptmegoszto.controller;
import com.szakdolgozat.receptmegoszto.entity.User;
import com.szakdolgozat.receptmegoszto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/teszt-mentes")
    public String tesztMentes() {
        User ujFelhasznalo = new User();
        ujFelhasznalo.setUsername("SzakacsMester");
        ujFelhasznalo.setEmail("szakacs@pelda.hu");
        ujFelhasznalo.setPassword("titkosjelszo123");

        userRepository.save(ujFelhasznalo);

        return "Sikeres mentés!";
    }
}