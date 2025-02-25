package com.nasa.serviceBot;

import com.nasa.bot.NasaBot;
import com.nasa.exception.UpdateNotFoundException;
import com.nasa.serviceBot.handler.impl.CallbackHandler;
import com.nasa.serviceBot.handler.impl.CommandHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UpdateDispatcherTest {

    @Mock
    private CommandHandler commandHandler;
    @Mock
    private CallbackHandler callbackHandler;
    @Mock
    private NasaBot nasaBot;

    @InjectMocks
    private UpdateDispatcher updateDispatcher;

    @Test
    void handleUpdateShouldThrowsUpdateNotFoundException() throws UpdateNotFoundException {
        Update update = mock(Update.class);
        when(update.hasMessage()).thenReturn(false);
        when(update.hasCallbackQuery()).thenReturn(false);

        assertThrows(UpdateNotFoundException.class, () -> updateDispatcher.handleUpdate(update, nasaBot));
    }

    @Test
    void handleUpdateShouldCallCommandHandlerWhenMessageExists() throws UpdateNotFoundException {
        Update update = mock(Update.class);
        when(update.hasMessage()).thenReturn(true);
        when(update.hasCallbackQuery()).thenReturn(false);

        // for stop executing private method writeLog()
        when(update.getMessage()).thenThrow(new RuntimeException());

        try {
            updateDispatcher.handleUpdate(update, nasaBot);
        } catch (RuntimeException e) {
            System.out.println("Throw exception for stop executing private method writeLog()");
        }
        verify(commandHandler).useUpdate(eq(update), eq(nasaBot));
    }

    @Test
    void handleUpdateShouldCallCallbackHandlerWhenCallbackExists() throws UpdateNotFoundException {
        Update update = mock(Update.class);
        when(update.hasCallbackQuery()).thenReturn(true);

        updateDispatcher.handleUpdate(update, nasaBot);

        verify(callbackHandler).useUpdate(eq(update), eq(nasaBot));
    }
}
