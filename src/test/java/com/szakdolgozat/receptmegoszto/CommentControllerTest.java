package com.szakdolgozat.receptmegoszto;

import com.szakdolgozat.receptmegoszto.controller.CommentController;
import com.szakdolgozat.receptmegoszto.entity.Comment;
import com.szakdolgozat.receptmegoszto.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentController commentController;

    @Test
    void tesztUjKomment_UresSzerzovel_VendegKeszul() {
        Comment ujKomment = new Comment();
        ujKomment.setReceptId(1L);
        ujKomment.setSzoveg("Nagyon finom lett!");
        ujKomment.setAuthor("");

        when(commentRepository.save(any(Comment.class))).thenReturn(ujKomment);

        commentController.addComment(ujKomment);

        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository, times(1)).save(captor.capture());

        assertEquals("Vendég", captor.getValue().getAuthor());
    }
}