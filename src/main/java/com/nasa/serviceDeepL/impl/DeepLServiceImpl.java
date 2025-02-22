package com.nasa.serviceDeepL.impl;

import com.deepl.api.DeepLException;
import com.deepl.api.Translator;
import com.nasa.config.DeepLConfig;
import com.nasa.serviceDeepL.DeepLService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeepLServiceImpl implements DeepLService {

    Logger logger = LoggerFactory.getLogger(DeepLServiceImpl.class);

    DeepLConfig deepLConfig;
    Translator translator;

    @Autowired
    public DeepLServiceImpl(DeepLConfig deepLConfig) {
        this.deepLConfig = deepLConfig;
        this.translator = new Translator(deepLConfig.getApi_key());
    }

    @Override
    public String translate(String text) {
        String translatedText = "";
        try {
            translatedText = translator
                    .translateText(text, null, "uk")
                    .getText();
        } catch (DeepLException | InterruptedException e) {
            logger.error(e.getMessage());
        }

        return translatedText;
    }
}
