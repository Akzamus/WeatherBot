package ru.akzam.WeatherBot.keyboardCommandExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.akzam.WeatherBot.DTO.HourForecast;
import ru.akzam.WeatherBot.model.User;
import ru.akzam.WeatherBot.service.WeatherService;
import ru.akzam.WeatherBot.util.Button;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class DayForecastCommandExecutor implements KeyboardCommandExecutor {

    private final WeatherService weatherService;

    @Autowired
    public DayForecastCommandExecutor(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public void execute(SendMessage sendMessage, User user) throws IOException {
        sendMessage.setText("Weather for the day in " + user.getLocation() + "\n\n" +
                             weatherService.getDayForecast(user.getLatitude(), user.getLongitude())
                                           .stream()
                                           .map(HourForecast::getInfo)
                                           .collect(Collectors.joining("\n" )));
    }

    @Override
    public String getButtonName() {
        return Button.DAY.toString();
    }
}
