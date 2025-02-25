package com.nasa.serviceBot.command.impl;

import com.nasa.bot.NasaBot;
import com.nasa.serviceBot.MainManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandHelpTest {

    @Mock
    private MainManager mainManager;
    @Mock
    private NasaBot nasaBot;

    @InjectMocks
    CommandHelp commandHelp;

    @Test
    void executeShouldSendMessage() {
        Message message = mock(Message.class);
        when(message.getChatId()).thenReturn(777L);

        commandHelp.execute(message, nasaBot);

        verify(mainManager).sendTextMessage(eq(777L)
                , contains("Ось список доступних команд:")
                , eq(nasaBot));
    }
}
