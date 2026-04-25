package com.szakdolgozat.receptmegoszto.controller;

import com.szakdolgozat.receptmegoszto.entity.Recept;
import com.szakdolgozat.receptmegoszto.repository.ReceptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReceptController {

    @Autowired
    private ReceptRepository receptRepository;

    @PostMapping("/api/uj-recept")
    public String ujReceptMentes(@RequestParam String title,
                                 @RequestParam String description,
                                 @RequestParam String author) {

        Recept ujRecept = new Recept();
        ujRecept.setTitle(title);
        ujRecept.setDescription(description);
        ujRecept.setAuthor(author);

        receptRepository.save(ujRecept);

        return "A(z) " + title + " nevű recept sikeresen bekerült az adatbázisba!";
    }
}
