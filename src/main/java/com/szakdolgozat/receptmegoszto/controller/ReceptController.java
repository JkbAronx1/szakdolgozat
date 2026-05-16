package com.szakdolgozat.receptmegoszto.controller;

import com.szakdolgozat.receptmegoszto.entity.Recept;
import com.szakdolgozat.receptmegoszto.repository.ReceptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ReceptController {

    @Autowired
    private ReceptRepository receptRepository;

    @PostMapping("/api/uj-recept")
    public Recept ujReceptMentes(@RequestBody Recept ujRecept) {
        return receptRepository.save(ujRecept);
    }

    @GetMapping("/api/receptek")
    public List<Recept> osszesRecept() {
        return receptRepository.findAll();
    }
}