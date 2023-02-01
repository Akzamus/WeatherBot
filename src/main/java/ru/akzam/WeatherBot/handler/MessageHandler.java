package ru.akzam.WeatherBot.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.akzam.WeatherBot.facade.GeocoderFacade;
import ru.akzam.WeatherBot.keyboardCommandExecutor.KeyboardCommandExecutor;
import ru.akzam.WeatherBot.model.User;
import ru.akzam.WeatherBot.service.MenuService;
import ru.akzam.WeatherBot.service.UsersService;
import ru.akzam.WeatherBot.util.Button;
import ru.akzam.WeatherBot.util.Replica;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MessageHandler {

    private final UsersService usersService;
    private final MenuService menuService;
    private final GeocoderFacade geocoderFacade;

    private final Map<String, KeyboardCommandExecutor> commandExecutorMap = new HashMap<>();

    @Autowired
    public MessageHandler(UsersService usersService, MenuService menuService, GeocoderFacade geocoderFacade) {
        this.usersService = usersService;
        this.menuService = menuService;
        this.geocoderFacade = geocoderFacade;
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

        if (location == null) {
            if (messageText.contains("/start")) sendMessage.setText(Replica.GREETING + "\n" + Replica.ADVICE);
            else geocoderFacade.tryAddLocation(messageText, sendMessage, userId);
            return sendMessage;
        }

        if(messageText.contains("/start")){
            sendMessage.setText(Replica.GREETING.toString());
            menuService.addMainMenu(sendMessage);
            return sendMessage;
        }

        try {
            KeyboardCommandExecutor defaultValue = commandExecutorMap.get(Button.DEFAULT.toString());
            commandExecutorMap.getOrDefault(messageText, defaultValue).execute(sendMessage, user);
        }catch (IOException e) {
            sendMessage.setText(Replica.UNDEFINED_ERROR.toString());
        }
        return sendMessage;
    }

    public void register(String buttonName, KeyboardCommandExecutor commandExecutor) {
        commandExecutorMap.put(buttonName, commandExecutor);
    }
}
