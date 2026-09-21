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

    // --- RECEPT MÓDOSÍTÁSA ---
    @PutMapping("/api/recept-modositas/{id}")
    public org.springframework.http.ResponseEntity<?> receptModositas(@PathVariable Long id, @RequestBody Recept ujAdatok) {
        return receptRepository.findById(id).map(recept -> {
            recept.setTitle(ujAdatok.getTitle());
            recept.setDescription(ujAdatok.getDescription());
            recept.setHozzavalok(ujAdatok.getHozzavalok());
            recept.setKategoria(ujAdatok.getKategoria());
            recept.setNehezseg(ujAdatok.getNehezseg());
            recept.setIdo(ujAdatok.getIdo());
            recept.setAdag(ujAdatok.getAdag());
            if (ujAdatok.getKepUrl() != null && !ujAdatok.getKepUrl().isEmpty()) {
                recept.setKepUrl(ujAdatok.getKepUrl());
            }
            receptRepository.save(recept);
            return org.springframework.http.ResponseEntity.ok("Recept sikeresen frissítve!");
        }).orElse(org.springframework.http.ResponseEntity.notFound().build());
    }

    // --- RECEPT TÖRLÉSE ---
    @DeleteMapping("/api/recept-torles/{id}")
    public org.springframework.http.ResponseEntity<?> receptTorles(@PathVariable Long id) {
        return receptRepository.findById(id).map(recept -> {
            receptRepository.delete(recept);
            return org.springframework.http.ResponseEntity.ok("Recept sikeresen törölve!");
        }).orElse(org.springframework.http.ResponseEntity.notFound().build());
    }
}