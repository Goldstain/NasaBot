package com.nasa.serviceNasaAPI.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AstroInfoTest {

    @InjectMocks
    private AstroInfo astroInfo;

    @Test
    void shouldReturnOptionalEmpty() {
        String[] coordinates = {"50.777"};

        assertEquals(Optional.empty(), astroInfo.constructResponse(coordinates));

        coordinates = null;

        assertEquals(Optional.empty(), astroInfo.constructResponse(coordinates));
    }
}
