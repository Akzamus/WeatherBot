package ru.akzam.WeatherBot.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.akzam.WeatherBot.model.DayForecast;
import ru.akzam.WeatherBot.model.HourForecast;
import ru.akzam.WeatherBot.model.User;
import ru.akzam.WeatherBot.servisec.GeocoderService;
import ru.akzam.WeatherBot.servisec.MenuService;
import ru.akzam.WeatherBot.servisec.UsersService;
import ru.akzam.WeatherBot.servisec.WeatherService;
import ru.akzam.WeatherBot.util.Replica;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class MessageHandler {

    private final UsersService usersService;
    private final WeatherService weatherService;
    private final MenuService menuService;
    private final GeocoderService geocoderService;

    @Autowired
    public MessageHandler(UsersService usersService, WeatherService weatherService, MenuService menuService, GeocoderService geocoderService) {
        this.usersService = usersService;
        this.weatherService = weatherService;
        this.menuService = menuService;
        this.geocoderService = geocoderService;
    }

    public BotApiMethod<?> getResponse(Message message) {

        long userId = message.getFrom().getId();
        long chatId = message.getChatId();

        String messageText = message.getText();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        if(usersService.findById(userId).isEmpty())
            usersService.save(new User(userId));

        User user = usersService.findById(userId).get();
        String location = user.getLocation();

        if(location == null) {
            addLocation(messageText, sendMessage, userId);
            return sendMessage;
        }

        if(messageText.contains("/start")){
            sendMessage.setText(Replica.GREETING.toString());
            menuService.addMainMenu(sendMessage);
            return sendMessage;
        }

        try {return getResponseToRequestFromKeyboard(messageText, sendMessage, user);
        }catch (IOException e) {sendMessage.setText(Replica.UNDEFINED_ERROR.toString()); return sendMessage;}
    }

    private void addLocation(String messageText, SendMessage sendMessage, long userId) {
        if(messageText.contains("/start")) {
            sendMessage.setText(Replica.GREETING + "\n" + Replica.ADVICE);
        }else{
            try {
                weatherService.getNowForecast(messageText);
                Double[] coordinates = geocoderService.getCoordinates(messageText);
                usersService.addLocationFromUser(messageText, userId, coordinates[0], coordinates[1]);
                sendMessage.setText(Replica.CORRECT_LOCATION.toString());
                menuService.addMainMenu(sendMessage);
            }catch (IOException e) {sendMessage.setText(Replica.INCORRECT_LOCATION.toString());}
        }
    }

    private SendMessage getResponseToRequestFromKeyboard(String request, SendMessage sendMessage, User user) throws IOException {
        switch (request) {
            case "day"  -> sendMessage.setText("Weather for the day in " + user.getLocation() + "\n\n" +
                                                weatherService.getDayForecast(user.getLatitude(), user.getLongitude())
                                                              .stream()
                                                              .map(HourForecast::getInfo)
                                                              .collect(Collectors.joining("\n" )));

            case "week" -> sendMessage.setText("Weather for the week in " + user.getLocation() + "\n\n" +
                                                weatherService.getWeekForecast(user.getLatitude(), user.getLongitude())
                                                              .stream()
                                                              .map(DayForecast::getInfo)
                                                              .collect(Collectors.joining("\n")));

            case "now"  -> sendMessage.setText("Weather in " + user.getLocation()+ " now:\n" +
                                                weatherService.getNowForecast(user.getLocation()).getInfo());

            case "reset"->{usersService.deleteLocationFromUser(user.getId());
                           sendMessage.setText(Replica.LOCATION_DROPPED +"\n" + Replica.ADVICE);}

            default ->     sendMessage.setText(Replica.USE_KEYBOARD.toString());

        }
        return sendMessage;
    }
}
