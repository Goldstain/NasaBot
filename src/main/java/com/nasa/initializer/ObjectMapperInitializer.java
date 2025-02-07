package com.nasa.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperInitializer {

    @Bean
    public ObjectMapper objectMapper() {;
        return new ObjectMapper();
    }

}
