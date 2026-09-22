package com.szakdolgozat.receptmegoszto.repository;

import com.szakdolgozat.receptmegoszto.entity.Recept;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReceptRepository extends JpaRepository<Recept, Long> {
}