package com.nasa.serviceNasaAPI.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasa.config.NasaConfig;
import com.nasa.serviceDeepL.DeepLService;
import com.nasa.serviceNasaAPI.NasaService;
import com.nasa.serviceNasaAPI.dto.ManifestResponse;
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
    ObjectMapper objectMapper;
    DeepLService deepLService;

    public MarsRoverPhotos(NasaConfig nasaConfig, WebClient webClient, ObjectMapper objectMapper, DeepLService deepLService) {
        this.nasaConfig = nasaConfig;
        this.webClient = webClient;
        this.objectMapper = objectMapper;
        this.deepLService = deepLService;
    }

    @Override
    public Optional<String> constructRequest(String... rover) {
        var roverInfo = getManifestResponse(rover[0]).toString();

        if (!roverInfo.isBlank() && !roverInfo.isBlank()) {
            return Optional.of(roverInfo);
        }
        return Optional.empty();
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


//    private Optional<List<String>> extractMediaUrl(String json) {
//        var media = new ArrayList<String>();
//
//        try {
//            JsonNode jsonNode = ifArrayThenReturnJsonNode(objectMapper.readTree(json)).get("photo_manifest");
//
//            System.out.println("JJJJJSOOOOOOOOOOOOOOOOOOOOOOOOOO");
//            System.out.println(jsonNode.toString());
//
//            media.add("Назва: " + getJsonValue(jsonNode, "name"));
//            media.add("Стартував з Землі: " + getJsonValue(jsonNode, "launch_date"));
//            media.add("Прибув на Марс: " + getJsonValue(jsonNode, "landing_date"));
//            media.add("Статус місії: " + getJsonValue(jsonNode, "status"));
//            media.add("Працював марсіанських днів: " + getJsonValue(jsonNode, "max_sol"));
//            media.add("Остання дата отриманих знімків: " + getJsonValue(jsonNode, "max_date"));
//            media.add("Всього зроблено знімків: " + getJsonValue(jsonNode, "total_photos"));
//
//            return Optional.of(media);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Optional.empty();
//        }
//    }

//    private String getJsonValue(JsonNode jsonNode, String key) {
//        return jsonNode.has(key) && !jsonNode.get(key).isNull() ? jsonNode.get(key).asText() : "N/A";
//    }
//
//    private JsonNode ifArrayThenReturnJsonNode(JsonNode jsonNode) {
//        return jsonNode.isArray() && !jsonNode.isNull() ? jsonNode.get(0) : jsonNode;
//    }
}
