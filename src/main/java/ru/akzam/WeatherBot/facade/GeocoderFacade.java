package ru.akzam.WeatherBot.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.akzam.WeatherBot.service.GeocoderService;
import ru.akzam.WeatherBot.service.MenuService;
import ru.akzam.WeatherBot.service.UsersService;
import ru.akzam.WeatherBot.service.WeatherService;
import ru.akzam.WeatherBot.util.Replica;

import java.io.IOException;

@Component
public class GeocoderFacade {

    private final UsersService usersService;
    private final WeatherService weatherService;
    private final MenuService menuService;
    private final GeocoderService geocoderService;

    @Autowired
    public GeocoderFacade(UsersService usersService, WeatherService weatherService, MenuService menuService, GeocoderService geocoderService) {
        this.usersService = usersService;
        this.weatherService = weatherService;
        this.menuService = menuService;
        this.geocoderService = geocoderService;
    }

    public void tryAddLocation(String messageText, SendMessage sendMessage, long userId) {
        try {
            weatherService.getNowForecast(messageText);
            Double[] coordinates = geocoderService.getCoordinates(messageText);
            usersService.addLocationFromUser(messageText, userId, coordinates[0], coordinates[1]);
            sendMessage.setText(Replica.CORRECT_LOCATION.toString());
            menuService.addMainMenu(sendMessage);
        } catch (IOException e) {sendMessage.setText(Replica.INCORRECT_LOCATION.toString());}
    }
}
