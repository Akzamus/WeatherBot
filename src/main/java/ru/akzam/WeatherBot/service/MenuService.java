package ru.akzam.WeatherBot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.akzam.WeatherBot.util.Button;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    public void addMainMenu(SendMessage sendMessage) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }

    private ReplyKeyboardMarkup getMainMenuKeyboard() {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow rowOne = new KeyboardRow();
        KeyboardRow rowTwo = new KeyboardRow();

        rowOne.add(new KeyboardButton(Button.DAY.toString()));
        rowOne.add(new KeyboardButton(Button.WEEK.toString()));

        rowTwo.add((new KeyboardButton(Button.NOW.toString())));
        rowTwo.add(new KeyboardButton(Button.RESET.toString()));

        keyboard.add(rowOne);
        keyboard.add(rowTwo);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}
