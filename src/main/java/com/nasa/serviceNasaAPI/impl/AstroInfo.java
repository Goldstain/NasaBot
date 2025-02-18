package com.nasa.serviceNasaAPI.impl;

import com.nasa.config.NasaConfig;
import com.nasa.serviceNasaAPI.NasaService;
import com.nasa.serviceNasaAPI.dto.AstroInfoDTO;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AstroInfo implements NasaService {

    NasaConfig nasaConfig;
    WebClient webClient;

    @Autowired
    public AstroInfo(NasaConfig nasaConfig, WebClient webClient) {
        this.nasaConfig = nasaConfig;
        this.webClient = webClient;
    }

    @Override
    public Optional<String> constructResponse(String... options) {
        if (options == null || options.length < 2) return Optional.empty();

        String astroInfo = getAstroInfo(options);
        if (astroInfo == null || astroInfo.isEmpty()) return Optional.empty();

        return Optional.of(astroInfo);
    }


    private String getAstroInfo(String[] options) {
        var url = String.format("%s?apiKey=%s&lat=%s&long=%s"
                , nasaConfig.getAstro_info()
                , nasaConfig.getAstro_info_api_key()
                , options[0]
                , options[1]);

        var astroInfoDTO = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(AstroInfoDTO.class)
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.just(new AstroInfoDTO());
                })
                .block();

        return astroInfoDTO.toString();
    }

}
