package com.szakdolgozat.receptmegoszto.controller;

import com.szakdolgozat.receptmegoszto.entity.Comment;
import com.szakdolgozat.receptmegoszto.entity.Recept;
import com.szakdolgozat.receptmegoszto.repository.CommentRepository;
import com.szakdolgozat.receptmegoszto.repository.ReceptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ReceptController {

    @Autowired
    private ReceptRepository receptRepository;

    @Autowired
    private CommentRepository commentRepository;

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
    public ResponseEntity<?> receptModositas(@PathVariable Long id, @RequestBody Recept ujAdatok) {
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
            return ResponseEntity.ok("Recept sikeresen frissítve!");
        }).orElse(ResponseEntity.notFound().build());
    }

    // --- RECEPT TÖRLÉSE ---
    @DeleteMapping("/api/recept-torles/{id}")
    public ResponseEntity<?> receptTorles(@PathVariable Long id) {
        return receptRepository.findById(id).map(recept -> {
            receptRepository.delete(recept);
            return ResponseEntity.ok("Recept sikeresen törölve!");
        }).orElse(ResponseEntity.notFound().build());
    }

    // --- ADMIN LEKÉRDEZÉS KOMMENTEKKEL ---
    @GetMapping("/api/admin/receptek-kommentekkel")
    public List<Map<String, Object>> osszesReceptKommentekkel() {
        List<Recept> receptek = receptRepository.findAll();
        List<Comment> kommentek = commentRepository.findAll();

        return receptek.stream().map(recept -> {
            Map<String, Object> map = new HashMap<>();
            map.put("recept", recept);
            List<Comment> receptKommentjei = kommentek.stream()
                    .filter(c -> recept.getId().equals(c.getReceptId()))
                    .toList();
            map.put("kommentek", receptKommentjei);
            return map;
        }).toList();
    }

    // --- KOMMENT TÖRLÉSE ADMINTÓL ---
    @DeleteMapping("/api/admin/comment-torles/{id}")
    public ResponseEntity<?> commentTorles(@PathVariable Long id) {
        return commentRepository.findById(id).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok("Komment törölve!");
        }).orElse(ResponseEntity.notFound().build());
    }
}