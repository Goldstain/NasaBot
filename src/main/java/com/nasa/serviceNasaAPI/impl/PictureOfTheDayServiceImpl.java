package com.nasa.serviceNasaAPI.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasa.config.NasaConfig;
import com.nasa.serviceNasaAPI.NasaService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PictureOfTheDayServiceImpl implements NasaService {

    NasaConfig nasaConfig;
    WebClient webClient;
    ObjectMapper objectMapper;

    @Autowired
    public PictureOfTheDayServiceImpl(NasaConfig nasaConfig, WebClient webClient, ObjectMapper objectMapper) {
        this.nasaConfig = nasaConfig;
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<List<String>> constructRequest() {
        var imageUrl = extractMediaUrl(getJson());
        if (imageUrl.isPresent()) {
            return imageUrl;
        }
        return Optional.empty();
    }


    private String getJson() {
        var url = nasaConfig.getPicture_of_the_day()
                .concat("?api_key=")
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

    private Optional<List<String>> extractMediaUrl(String json) {
        var media = new ArrayList<String>();

        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            media.add(jsonNode.get("media_type").asText());
            media.add(jsonNode.get("url").asText());
            return Optional.of(media);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


}
