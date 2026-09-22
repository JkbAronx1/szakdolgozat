package com.szakdolgozat.receptmegoszto.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long receptId;

    @Column(columnDefinition = "TEXT")
    private String szoveg;

    private String author;

    public Comment() {}

    // Getterek és setterek
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getReceptId() { return receptId; }
    public void setReceptId(Long receptId) { this.receptId = receptId; }

    public String getSzoveg() { return szoveg; }
    public void setSzoveg(String szoveg) { this.szoveg = szoveg; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
}