package ru.akzam.WeatherBot.keyboardCommandExecutor;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.akzam.WeatherBot.model.User;
import ru.akzam.WeatherBot.util.Button;
import ru.akzam.WeatherBot.util.Replica;

@Component
public class DefaultCommandExecutor implements KeyboardCommandExecutor {

    @Override
    public void execute(SendMessage sendMessage, User user) {
        sendMessage.setText(Replica.USE_KEYBOARD.toString());
    }

    @Override
    public String getButtonName() {
        return Button.DEFAULT.toString();
    }
}
