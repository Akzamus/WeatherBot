package ru.akzam.WeatherBot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.akzam.WeatherBot.handler.MessageHandler;


@Component
public class TelegramFacade {
    private final MessageHandler messageHandler;

    @Autowired
    public TelegramFacade(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            return messageHandler.getResponse(message);
        }
        return null;
    }
}
