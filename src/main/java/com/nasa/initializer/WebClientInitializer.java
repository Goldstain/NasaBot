package com.nasa.initializer;


import com.nasa.config.NasaConfig;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebClientInitializer {

    NasaConfig nasaConfig;

    @Autowired
    public WebClientInitializer(NasaConfig nasaConfig) {
        this.nasaConfig = nasaConfig;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(nasaConfig.getMain_api())
                .build();
    }

}
