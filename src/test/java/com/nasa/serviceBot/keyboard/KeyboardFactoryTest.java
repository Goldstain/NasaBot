package com.nasa.serviceBot.keyboard;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class KeyboardFactoryTest {

    @InjectMocks
    private KeyboardFactory keyboardFactory;

    @Test
    void mainMenuButton() {
        InlineKeyboardMarkup keyboard = keyboardFactory.mainMenuButton();

        assertNotNull(keyboard, "Keyboard is null");
        assertNotNull(keyboard.getKeyboard());
        assertNotNull(keyboard.getKeyboard().getFirst());
        assertEquals(1, keyboard.getKeyboard().size());
        assertEquals("🚀 Головне меню", keyboard.getKeyboard().getFirst().getFirst().getText());
        assertEquals("mainMenu", keyboard.getKeyboard().getFirst().getFirst().getCallbackData());
    }

    @Test
    void mainMenu() {
        InlineKeyboardMarkup keyboard = keyboardFactory.mainMenu();

        assertNotNull(keyboard, "Keyboard is null");
        assertNotNull(keyboard.getKeyboard());
        assertFalse(keyboard.getKeyboard().isEmpty(), "Keyboard is empty");
        assertNotNull(keyboard.getKeyboard().getFirst());
        assertFalse(keyboard.getKeyboard().getFirst().isEmpty(), "Keyboard is empty");
    }

    @Test
    void astroInfoMenu() {
        ReplyKeyboardMarkup keyboard = keyboardFactory.astroInfoMenu();

        assertNotNull(keyboard, "Keyboard is null");
        assertNotNull(keyboard.getKeyboard());
        assertNotNull(keyboard.getKeyboard().getFirst(), "Keyboard is empty");
        assertTrue(keyboard.getResizeKeyboard());
        assertTrue(keyboard.getOneTimeKeyboard());
    }

    @Test
    void roversMenu() {
        InlineKeyboardMarkup keyboard = keyboardFactory.roversMenu();

        assertNotNull(keyboard, "Keyboard is null");
        assertNotNull(keyboard.getKeyboard());
        assertNotNull(keyboard.getKeyboard().getFirst(), "Keyboard is empty");
    }

    @Test
    void returnToRoversMenu() {
        InlineKeyboardMarkup keyboard = keyboardFactory.returnToRoversMenu();

        assertNotNull(keyboard, "Keyboard is null");
        assertNotNull(keyboard.getKeyboard());
        assertNotNull(keyboard.getKeyboard().getFirst(), "Keyboard is empty");
        assertEquals("marsRoversPhotos", keyboard.getKeyboard().getFirst().getFirst().getCallbackData());
    }

    @Test
    void roverPhotoButton() {
        InlineKeyboardMarkup returnToRoversMenu = keyboardFactory.returnToRoversMenu();
        InlineKeyboardMarkup keyboard = keyboardFactory.roverPhotoButton(returnToRoversMenu);

        assertNotNull(keyboard, "Keyboard is null");
        assertNotNull(keyboard.getKeyboard());
        assertNotNull(keyboard.getKeyboard().getFirst(), "Keyboard is empty");
    }

}
