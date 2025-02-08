package com.nasa.serviceNasaAPI.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasa.config.NasaConfig;
import com.nasa.serviceDeepL.DeepLService;
import com.nasa.serviceNasaAPI.NasaService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PictureOfTheDayServiceImpl implements NasaService {

    NasaConfig nasaConfig;
    WebClient webClient;
    ObjectMapper objectMapper;
    DeepLService deepLservice;
    @NonFinal
    Optional<List<String>> lastMediaResponse;

    @Autowired
    public PictureOfTheDayServiceImpl(NasaConfig nasaConfig, WebClient webClient, ObjectMapper objectMapper, DeepLService deepLservice) {
        this.nasaConfig = nasaConfig;
        this.webClient = webClient;
        this.objectMapper = objectMapper;
        this.deepLservice = deepLservice;
        lastMediaResponse = Optional.empty();
    }

    @Override
    public Optional<List<String>> constructRequest() {
        if (lastMediaResponse.isPresent()) {
            return lastMediaResponse;
        }
        var mediaResponse = extractMediaUrl(getJson());
        lastMediaResponse = mediaResponse;
        if (mediaResponse.isPresent()) {
            return mediaResponse;
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

    protected Optional<List<String>> extractMediaUrl(String json) {
        var media = new ArrayList<String>();

        try {
            JsonNode jsonNode = ifArrayThenReturnJsonNode(objectMapper.readTree(json));

            media.add(getJsonValue(jsonNode, "media_type"));
            media.add(translator(getJsonValue(jsonNode, "title")));
            media.add(translator(getJsonValue(jsonNode, "explanation")));
            media.add(getJsonValue(jsonNode, "url"));

            return Optional.of(media);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    protected String getJsonValue(JsonNode jsonNode, String key) {
        return jsonNode.has(key) && !jsonNode.get(key).isNull() ? jsonNode.get(key).asText() : "N/A";
    }

    protected String translator(String text) {
        return deepLservice.translate(text);
    }

    private JsonNode ifArrayThenReturnJsonNode(JsonNode jsonNode) {
        return jsonNode.isArray() && !jsonNode.isNull() ? jsonNode.get(0) : jsonNode;
    }

    @Scheduled(cron = "0 30 8 * * *")
    public void setLastMediaResponse() {
        lastMediaResponse = Optional.empty();
    }


}
