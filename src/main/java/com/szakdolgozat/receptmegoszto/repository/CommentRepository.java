package com.szakdolgozat.receptmegoszto.repository;

import com.szakdolgozat.receptmegoszto.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}