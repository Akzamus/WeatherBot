package ru.akzam.WeatherBot.keyboardCommandExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.akzam.WeatherBot.handler.MessageHandler;
import ru.akzam.WeatherBot.model.User;

import java.io.IOException;

public interface KeyboardCommandExecutor {
    void execute(SendMessage sendMessage, User user) throws IOException;

    String getButtonName();

    @Autowired
    default void registerMe(MessageHandler messageHandler) {
        messageHandler.register(getButtonName(), this);
    }
}
