package ru.akzam.WeatherBot.keyboardCommandExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.akzam.WeatherBot.model.User;
import ru.akzam.WeatherBot.service.UsersService;
import ru.akzam.WeatherBot.util.Button;
import ru.akzam.WeatherBot.util.Replica;

@Component
public class ResetCommandExecutor implements KeyboardCommandExecutor {

    private final UsersService usersService;

    @Autowired
    public ResetCommandExecutor(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public void execute(SendMessage sendMessage, User user) {
        usersService.deleteLocationFromUser(user.getId());
        sendMessage.setText(Replica.LOCATION_DROPPED +"\n" + Replica.ADVICE);
    }

    @Override
    public String getButtonName() {
        return Button.RESET.toString();
    }
}
