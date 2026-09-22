package com.szakdolgozat.receptmegoszto.controller;

import com.szakdolgozat.receptmegoszto.entity.Recept;
import com.szakdolgozat.receptmegoszto.entity.User;
import com.szakdolgozat.receptmegoszto.repository.CommentRepository;
import com.szakdolgozat.receptmegoszto.repository.ReceptRepository;
import com.szakdolgozat.receptmegoszto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.szakdolgozat.receptmegoszto.entity.Comment;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private ReceptRepository receptRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    // 1. Összes recept lekérése adminnak
    @GetMapping("/recipes")
    public ResponseEntity<List<Recept>> getAllRecipesForAdmin() {
        return ResponseEntity.ok(receptRepository.findAll());
    }

    // 2. Recept törlése adminként
    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<?> deleteRecipeAsAdmin(@PathVariable Long id) {
        Optional<Recept> receptOpt = receptRepository.findById(id);
        if (receptOpt.isPresent()) {
            Recept recept = receptOpt.get();

            if (recept.getAuthor() != null) {
                User author = userRepository.findAll().stream()
                        .filter(u -> u.getUsername().equalsIgnoreCase(recept.getAuthor()))
                        .findFirst().orElse(null);
                if (author != null && author.getRecipeCount() > 0) {
                    author.setRecipeCount(author.getRecipeCount() - 1);
                    userRepository.save(author);
                }
            }

            receptRepository.deleteById(id);
            return ResponseEntity.ok().body("Recept sikeresen törölve az admin által!");
        }
        return ResponseEntity.notFound().build();
    }

    // 3. Komment / Értékelés törlése adminként
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<String> deleteCommentAsAdmin(@PathVariable Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return ResponseEntity.ok("Értékelés sikeresen törölve!");
        }
        return ResponseEntity.notFound().build();
    }

    // 4. Összes komment/értékelés lekérése adminnak
    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getAllCommentsForAdmin() {
        return ResponseEntity.ok(commentRepository.findAll());
    }
}