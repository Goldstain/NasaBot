package com.nasa.serviceBot.command.impl;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.MainManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandStartTest {

    @Mock
    private MainManager mainManager;
    @Mock
    private NasaBot nasaBot;

    @InjectMocks
    private CommandStart commandStart;

    @Test
    void executeShouldSendMessage () {
        Message message = mock(Message.class);
        User user = mock(User.class);
        when(message.getChatId()).thenReturn(777L);
        when(message.getFrom()).thenReturn(user);
        when(user.getFirstName()).thenReturn("Wenzel");

        commandStart.execute(message, nasaBot);

        verify(mainManager).sendWelcomeMessage(eq(777L)
                , eq("Wenzel"), eq(nasaBot));
    }
}
