package com.szakdolgozat.receptmegoszto.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "receptek") // A DBben így fog megjelenni
public class Recept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Recept címe
    private String title;

    //Recept leírása/elkészítése
    @Column(columnDefinition = "TEXT")
    private String description;

    //Recept készitő
    private String author;


    public Recept() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
