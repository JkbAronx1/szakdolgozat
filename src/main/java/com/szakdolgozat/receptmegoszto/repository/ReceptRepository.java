package com.szakdolgozat.receptmegoszto.repository;

import com.szakdolgozat.receptmegoszto.entity.Recept;
import org.springframework.data.jpa.repository.JpaRepository;

// Ez az Interface biztosítja nekünk a mentés, törlés, keresés funkciókat a receptekhez
public interface ReceptRepository extends JpaRepository<Recept, Long> {
}