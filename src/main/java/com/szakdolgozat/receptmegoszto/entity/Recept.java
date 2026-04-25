package com.szakdolgozat.receptmegoszto.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "receptek")
public class Recept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String author;

    // --- ÚJ MEZŐK ---
    private int ido;
    private String nehezseg;
    private String kategoria;
    private String kepUrl; // A Java automatikusan összeköti a DB-ben lévő kep_url oszloppal

    public Recept() {
    }

    // Getterek és Setterek a régi mezőkhöz
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    // --- ÚJ GETTEREK ÉS SETTEREK ---
    public int getIdo() { return ido; }
    public void setIdo(int ido) { this.ido = ido; }

    public String getNehezseg() { return nehezseg; }
    public void setNehezseg(String nehezseg) { this.nehezseg = nehezseg; }

    public String getKategoria() { return kategoria; }
    public void setKategoria(String kategoria) { this.kategoria = kategoria; }

    public String getKepUrl() { return kepUrl; }
    public void setKepUrl(String kepUrl) { this.kepUrl = kepUrl; }
}