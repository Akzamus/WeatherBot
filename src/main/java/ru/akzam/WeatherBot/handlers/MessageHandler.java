package ru.akzam.WeatherBot.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.akzam.WeatherBot.model.User;
import ru.akzam.WeatherBot.servisec.MenuService;
import ru.akzam.WeatherBot.servisec.UsersService;
import ru.akzam.WeatherBot.servisec.WeatherService;
import ru.akzam.WeatherBot.util.Replica;

import java.io.IOException;

@Component
public class MessageHandler {

    private final UsersService usersService;
    private final WeatherService weatherService;
    private final MenuService menuService;

    @Autowired
    public MessageHandler(UsersService usersService, WeatherService weatherService, MenuService menuService) {
        this.usersService = usersService;
        this.weatherService = weatherService;
        this.menuService = menuService;
    }

    public BotApiMethod<?> getResponse(Message message) {

        long userId = message.getFrom().getId();
        long chatId = message.getChatId();

        String messageText = message.getText();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        if(!usersService.findById(userId).isPresent())
            usersService.save(new User(userId));

        User user = usersService.findById(userId).get();
        String location = user.getLocation();

        if(location == null) {
            addLocation(messageText, sendMessage, userId);
            return sendMessage;
        }

        if(messageText.contains("/start")){
            sendMessage.setText(Replica.GREETING);
            menuService.addMainMenu(sendMessage);
            return sendMessage;
        }

        sendMessage.setText(messageText);
        return sendMessage;
    }

    private void addLocation(String messageText, SendMessage sendMessage, long userId) {
        if(messageText.contains("/start")) {
            sendMessage.setText(Replica.GREETING + "\n" + Replica.ADVICE);
        }else if(isExist(messageText)){
            usersService.addLocationFromUser(messageText, userId);
            sendMessage.setText(Replica.CORRECT_LOCATION);
            usersService.addLocationFromUser(messageText, userId);
            menuService.addMainMenu(sendMessage);
        }else {
            sendMessage.setText(Replica.INCORRECT_LOCATION);
        }
    }

    private boolean isExist(String location) {
        location = location.toUpperCase();
        if(location.equals("WEEK") || location.equals("DAY") || location.equals("BACK")){
            return false;
        }

        try {
            weatherService.getUrlContent("https://api.openweathermap.org/data/2.5/weather?q=" + location +
                                         "&appid=" + weatherService.getApiKey() +"&units=metric");
            return true;
        }catch (IOException e){
            return false;
        }
    }
}
