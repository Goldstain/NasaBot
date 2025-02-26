package com.nasa.serviceBot;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.keyboard.KeyboardFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class MainManagerTest {

    @Mock
    private NasaBot nasaBot;
    @Mock
    private KeyboardFactory keyboardFactory;


    @InjectMocks
    private MainManager mainManager;

    private final Long chatId = 765432L;
    private final String message = "message";

    @Test
    void testSendTextMessageWithoutKeyboard() throws TelegramApiException {
        mainManager.sendTextMessage(chatId, message, nasaBot);

        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(nasaBot).execute(captor.capture());

        SendMessage captorValue = captor.getValue();

        assertEquals(chatId.toString(), captorValue.getChatId());
        assertEquals(message, captorValue.getText());
        assertNull(captorValue.getReplyMarkup());
    }

    @Test
    void testSendTextMessageWithKeyboard() throws TelegramApiException {
        ReplyKeyboard replyKeyboard = mock(ReplyKeyboard.class);

        mainManager.sendTextMessage(chatId, message, nasaBot, replyKeyboard);

        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(nasaBot).execute(captor.capture());

        SendMessage captorValue = captor.getValue();

        assertEquals(chatId.toString(), captorValue.getChatId());
        assertEquals(message, captorValue.getText());
        assertEquals(replyKeyboard, captorValue.getReplyMarkup());
    }


    @Test
    void testSendCallbackQuery() throws TelegramApiException {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .build();

        mainManager.sendCallbackQuery(sendMessage, nasaBot);

        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(nasaBot).execute(captor.capture());

        SendMessage captorValue = captor.getValue();

        assertEquals(sendMessage.getChatId(), captorValue.getChatId());
        assertEquals(sendMessage.getText(), captorValue.getText());
    }

    @Test
    void testSendWelcomeMessage() throws TelegramApiException {
        String firstName = "FIRST USER NAME";
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);

        mainManager.sendWelcomeMessage(chatId, firstName, nasaBot);

        verify(nasaBot).execute(captor.capture());

        SendMessage captorValue = captor.getValue();

        assertEquals(chatId.toString(), captorValue.getChatId());
        assertTrue(captorValue.getText().contains(firstName));

        verify(keyboardFactory).mainMenuButton();
    }

    @Test
    void sendPhoto() {
    }

    @Test
    void sendVideo() {
    }
}
