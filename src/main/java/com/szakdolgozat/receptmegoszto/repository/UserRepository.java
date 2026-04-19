package com.szakdolgozat.receptmegoszto.repository;
import com.szakdolgozat.receptmegoszto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}