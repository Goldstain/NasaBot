package com.nasa.serviceNasaAPI.impl;

import com.nasa.config.NasaConfig;
import com.nasa.serviceNasaAPI.NasaService;
import com.nasa.serviceNasaAPI.dto.ManifestResponse;
import com.nasa.serviceNasaAPI.dto.ManifestResponseFull;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MarsRoverPhotos implements NasaService {

    NasaConfig nasaConfig;
    WebClient webClient;

    public MarsRoverPhotos(NasaConfig nasaConfig, WebClient webClient) {
        this.nasaConfig = nasaConfig;
        this.webClient = webClient;
    }

    @Override
    public Optional<String> constructRequest(String... rovers) {
        String roverInfo;
        if (rovers != null && rovers.length > 0) {
            roverInfo = getManifestResponse(rovers[0]).toString();
        } else {
            return Optional.empty();
        }

        if (!roverInfo.isEmpty() && !roverInfo.isBlank()) {
            return Optional.of(roverInfo);
        }
        return Optional.empty();
    }


    public ManifestResponseFull getManifestResponseFull(String date) {
        var url = nasaConfig.getMars_rovers_manifest()
                .concat("curiosity")
                .concat("?api_key=")
                .concat(nasaConfig.getApi_key());

        var manifestResponseFull = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(ManifestResponseFull.class)
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.just(new ManifestResponseFull());
                })
                .block();
        return manifestResponseFull;
    }


    private ManifestResponse getManifestResponse(String rover) {
        var url = nasaConfig.getMars_rovers_manifest()
                .concat(rover)
                .concat("?api_key=")
                .concat(nasaConfig.getApi_key());

        var manifestResponse = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(ManifestResponse.class)
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.just(new ManifestResponse());
                })
                .block();
        return manifestResponse;
    }




}
