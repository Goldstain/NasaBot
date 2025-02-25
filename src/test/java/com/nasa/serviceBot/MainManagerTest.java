package com.nasa.serviceBot;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.keyboard.KeyboardFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
class MainManagerTest {

    @Mock
    private KeyboardFactory keyboardFactory;
    @Mock
    private NasaBot nasaBot;

    @InjectMocks
    private MainManager mainManager;

    @Test
    void sendTextMessage() throws TelegramApiException {
        ReplyKeyboard[] keyboards = new ReplyKeyboard[]{};
        SendMessage sendMessage = mock(SendMessage.class);


//        mainManager.sendTextMessage(777L, "Text message", nasaBot, keyboards);
//
//        assertEquals(777L, sendMessage.getChatId());
//        assertEquals("Text message", sendMessage.getText());
//        assertNull(sendMessage.getReplyMarkup());
//
//        verify(nasaBot).execute(eq(sendMessage));

    }

    @Test
    void sendCallbackQuery() {
    }

    @Test
    void sendWelcomeMessage() {
    }

    @Test
    void sendPhoto() {
    }

    @Test
    void sendVideo() {
    }
}
