package com.nasa.serviceNasaAPI.impl;

import com.nasa.config.NasaConfig;
import com.nasa.serviceDeepL.DeepLService;
import com.nasa.serviceNasaAPI.NasaService;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NewsRSS implements NasaService {

    Logger logger = LoggerFactory.getLogger(NewsRSS.class);

    NasaConfig nasaConfig;
    DeepLService deepLService;

    @Autowired
    public NewsRSS(NasaConfig nasaConfig, DeepLService deepLService) {
        this.nasaConfig = nasaConfig;
        this.deepLService = deepLService;
    }

    @Override
    public Optional<List<String>> constructResponse(String... options) {
        List<String> news = new ArrayList<>();

        try {
            URL url = new URL(nasaConfig.getRss_feed());
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));

            news = feed.getEntries().stream()
                    .limit(5)
                    .map(entry -> {
                        String title = translate(entry.getTitle());
                        String link = entry.getLink();
                        String description = entry.getDescription() != null ? translate(entry.getDescription().getValue()) : "";
                        return "\uD83D\uDE80Новина:\n" + title + "\n" + "\uD83D\uDCE1Посилання: " + link + "\n" + "Опис: " + description;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return Optional.of(news);
    }

    private String translate(String text) {
        return deepLService.translate(text);
    }
}
