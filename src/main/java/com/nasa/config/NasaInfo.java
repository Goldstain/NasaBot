package com.nasa.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NasaInfo {

    String roversInfo = "\uD83D\uDD34 За весь час досліджень Марса на поверхні працювало 6 марсоходів. " +
            "NASA марсоходи - 5 з 6. Зараз в NASA доступні до перегляду фото з Curiosity";

    public String getRoversInfo() {
        return roversInfo;
    }
}
