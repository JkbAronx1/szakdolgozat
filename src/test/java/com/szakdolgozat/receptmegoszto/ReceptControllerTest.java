package com.szakdolgozat.receptmegoszto;

import com.szakdolgozat.receptmegoszto.controller.ReceptController;
import com.szakdolgozat.receptmegoszto.entity.Recept;
import com.szakdolgozat.receptmegoszto.repository.ReceptRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceptControllerTest {

    @Mock
    private ReceptRepository receptRepository;

    @InjectMocks
    private ReceptController receptController;

    @Test
    void tesztReceptModositas_Sikeres() {
        Recept regiRecept = new Recept();
        regiRecept.setId(1L);
        regiRecept.setTitle("Régi Név");
        regiRecept.setIdo(30);

        Recept ujAdatok = new Recept();
        ujAdatok.setTitle("Új Név");
        ujAdatok.setIdo(45);
        ujAdatok.setKategoria("Főétel");

        when(receptRepository.findById(1L)).thenReturn(Optional.of(regiRecept));
        ResponseEntity<?> valasz = receptController.receptModositas(1L, ujAdatok);

        assertEquals(HttpStatus.OK, valasz.getStatusCode());
        assertEquals("Recept sikeresen frissítve!", valasz.getBody());

        assertEquals("Új Név", regiRecept.getTitle());
        assertEquals(45, regiRecept.getIdo());

        verify(receptRepository, times(1)).save(regiRecept);
    }
}