package ru.akzam.WeatherBot.keyboardCommandExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.akzam.WeatherBot.model.User;
import ru.akzam.WeatherBot.service.WeatherService;
import ru.akzam.WeatherBot.util.Button;

import java.io.IOException;

@Component
public class NowForecastCommandExecutor implements KeyboardCommandExecutor {

    private final WeatherService weatherService;

    @Autowired
    public NowForecastCommandExecutor(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public void execute(SendMessage sendMessage, User user) throws IOException {
        sendMessage.setText("Weather in " + user.getLocation()+ " now:\n" +
                             weatherService.getNowForecast(user.getLocation()).getInfo());
    }

    @Override
    public String getButtonName() {
        return Button.NOW.toString();
    }
}
