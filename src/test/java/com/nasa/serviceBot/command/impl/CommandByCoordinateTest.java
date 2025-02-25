package com.nasa.serviceBot.command.impl;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.MainManager;
import com.nasa.serviceBot.keyboard.KeyboardFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandByCoordinateTest {

    @Mock
    private MainManager manager;
    @Mock
    private KeyboardFactory keyboardFactory;
    @Mock
    private NasaBot nasaBot;

    @InjectMocks
    private CommandByCoordinate command;

    @Test
    void executeShouldSendMessage() {
        Message message = mock(Message.class);
        when(message.getChatId()).thenReturn(777L);
        when(keyboardFactory.mainMenuButton()).thenReturn(null);

        command.execute(message, nasaBot);

        verify(manager).sendTextMessage(eq(777L), contains("Введіть широту і довготу за зразком:")
                , eq(nasaBot), isNull());
    }
}
