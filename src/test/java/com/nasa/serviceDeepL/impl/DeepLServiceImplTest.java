package com.nasa.serviceDeepL.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DeepLServiceImplTest {
    @Autowired
    private DeepLServiceImpl deepLServiceImpl;

    @Test
    void translate() {
        String translated = deepLServiceImpl.translate("text");

        assertTrue(translated.length() > 0);
        assertTrue("текст".equalsIgnoreCase(translated));
    }
}
