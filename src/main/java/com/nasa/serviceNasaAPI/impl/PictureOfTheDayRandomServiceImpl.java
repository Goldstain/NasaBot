package com.nasa.serviceNasaAPI.impl;

import com.nasa.config.NasaConfig;
import com.nasa.serviceNasaAPI.NasaService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PictureOfTheDayRandomServiceImpl implements NasaService {

    NasaConfig nasaConfig;
    WebClient webClient;
    PictureOfTheDayServiceImpl pictureOfTheDayService;

    @Autowired
    public PictureOfTheDayRandomServiceImpl(NasaConfig nasaConfig, WebClient webClient
            , PictureOfTheDayServiceImpl pictureOfTheDayService) {
        this.nasaConfig = nasaConfig;
        this.webClient = webClient;
        this.pictureOfTheDayService = pictureOfTheDayService;
    }

    @Override
    public Optional<List<String>> constructRequest(String... options) {
        var mediaResponse = pictureOfTheDayService.extractMediaUrl(getJson());
        if (mediaResponse.isPresent()) {
            return mediaResponse;
        }
        return Optional.empty();
    }


    private String getJson() {
        var url = nasaConfig.getPicture_of_the_day_random()
                .concat("&api_key=")
                .concat(nasaConfig.getApi_key());
        var webClientRequest = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.just("{}");
                })
                .block();
        return webClientRequest;
    }



}
