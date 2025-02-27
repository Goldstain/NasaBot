package com.nasa.serviceNasaAPI.impl;

import com.nasa.config.NasaConfig;
import com.nasa.serviceNasaAPI.dto.ManifestResponseFull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MarsRoverPhotosTest {

    @Autowired
    private MarsRoverPhotos marsRoverPhotos;
    @Autowired
    private NasaConfig nasaConfig;
    @Autowired
    private WebClient webClient;

    @Test
    void shouldReturnOptionalEmpty() {
        Optional<String> resultNull = marsRoverPhotos.constructResponse();
        assertEquals(Optional.empty(), resultNull);

        Optional<String> resultEmpty = marsRoverPhotos.constructResponse(new String[0]);
        assertEquals(Optional.empty(), resultEmpty);
    }

    @Test
    void shouldReturnOptionalPresent() {
        Optional<String> resultCuriosity = marsRoverPhotos.constructResponse("curiosity");

        assertNotNull(resultCuriosity);
        assertEquals(resultCuriosity.get().getClass(), String.class);
    }

    @Test
    void shouldReturnCorrectManifest() {
        ManifestResponseFull manifestResponseFull = marsRoverPhotos.getManifestResponseFull();

        assertNotNull(manifestResponseFull.getPhoto_manifest().getPhotos());
        assertTrue("curiosity".equalsIgnoreCase(manifestResponseFull.getPhoto_manifest().getName()));
        assertTrue(0 < manifestResponseFull.getPhoto_manifest().getMax_sol());
    }
}
