package com.szakdolgozat.receptmegoszto.controller;

import com.szakdolgozat.receptmegoszto.entity.Comment;
import com.szakdolgozat.receptmegoszto.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    // 1. Adott recept kommentjeinek lekérése a részletes nézethez
    @GetMapping("/comments/recipe/{receptId}")
    public List<Comment> getCommentsForRecipe(@PathVariable Long receptId) {
        return commentRepository.findAll().stream()
                .filter(c -> receptId.equals(c.getReceptId()))
                .toList();
    }

    // 2. Új komment mentése (akár vendég, akár bejelentkezett user)
    @PostMapping("/comments")
    public Comment addComment(@RequestBody Comment comment) {
        // Ha nincs kitöltve név, automatikusan "Vendég"-ként mentjük
        if (comment.getAuthor() == null || comment.getAuthor().trim().isEmpty()) {
            comment.setAuthor("Vendég");
        }
        return commentRepository.save(comment);
    }
}