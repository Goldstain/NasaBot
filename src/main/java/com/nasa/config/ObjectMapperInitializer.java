package com.nasa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Configuration
public class ObjectMapperInitializer {

    @Bean
    public ObjectMapper objectMapper() {;
        return new ObjectMapper();
    }

}
