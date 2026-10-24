package com.szakdolgozat.receptmegoszto;

import com.szakdolgozat.receptmegoszto.controller.AdminController;
import com.szakdolgozat.receptmegoszto.entity.Recept;
import com.szakdolgozat.receptmegoszto.entity.User;
import com.szakdolgozat.receptmegoszto.repository.CommentRepository;
import com.szakdolgozat.receptmegoszto.repository.ReceptRepository;
import com.szakdolgozat.receptmegoszto.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private ReceptRepository receptRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private AdminController adminController;

    @Test
    void tesztReceptTorlese_EsSzerzoStatisztikaCsokkentese() {
        Long receptId = 1L;
        Recept mockRecept = new Recept();
        mockRecept.setId(receptId);
        mockRecept.setAuthor("SzakacsMester");

        User mockSzerzo = new User();
        mockSzerzo.setUsername("SzakacsMester");
        mockSzerzo.setRecipeCount(5);

        when(receptRepository.findById(receptId)).thenReturn(Optional.of(mockRecept));
        when(userRepository.findAll()).thenReturn(List.of(mockSzerzo));

        ResponseEntity<?> valasz = adminController.deleteRecipeAsAdmin(receptId);

        assertEquals(HttpStatus.OK, valasz.getStatusCode());
        assertEquals("Recept sikeresen törölve az admin által!", valasz.getBody());

        assertEquals(4, mockSzerzo.getRecipeCount());

        verify(userRepository, times(1)).save(mockSzerzo);
        verify(receptRepository, times(1)).deleteById(receptId);
    }

    @Test
    void tesztReceptTorlese_NemLetezoRecept() {
        Long rosszId = 99L;
        when(receptRepository.findById(rosszId)).thenReturn(Optional.empty());

        ResponseEntity<?> valasz = adminController.deleteRecipeAsAdmin(rosszId);

        assertEquals(HttpStatus.NOT_FOUND, valasz.getStatusCode());
        verify(receptRepository, never()).deleteById(anyLong());
    }
}