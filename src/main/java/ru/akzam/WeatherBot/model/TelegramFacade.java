package ru.akzam.WeatherBot.model;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Component
public class TelegramFacade {

    @Value("${openweathermap.key}")
    private String apiKey;

    public BotApiMethod<?> handleUpdate(Update update) {
        Message message = update.getMessage();
        if (message.hasText()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            if(message.getText().contains("/start")) {
                sendMessage.setText("Hello \uD83D\uDC4B \n" + "Enter the name of the city, country or continent:");
                return sendMessage;
            }
            String city =  message.getText();
            try {
                String output = getUrlContent("https://api.openweathermap.org/data/2.5/weather?q=" + city +
                        "&appid=" + apiKey +"&units=metric");
                if(!output.isEmpty()){
                    JSONObject jsonObject = new JSONObject(output);
                    String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
                    sendMessage.setText("The weather in " + city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase() + ":\n"
                        + description.substring(0,1).toUpperCase() + description.substring(1) + " "
                            + getEmoji(jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon")) + "\n"
                        + "Temperature \uD83C\uDF21  " + jsonObject.getJSONObject("main").getDouble("temp") + " °C\n"
                        + "Feels like               " + jsonObject.getJSONObject("main").getDouble("feels_like") + " °C\n"
                        + "Max                        " + jsonObject.getJSONObject("main").getDouble("temp_max") + " °C\n"
                        + "Min                         " + jsonObject.getJSONObject("main").getDouble("temp_min") + " °C\n"
                        + "Pressure                " + jsonObject.getJSONObject("main").getDouble("pressure") + " hPa\n"
                    );
                }
                return sendMessage;
            }catch (IOException e){
                sendMessage.setText("Such a city or country was not found!");
                return sendMessage;
            }
        }
        return null;
    }

    private static String getUrlContent(String urlAddress) throws IOException {
        StringBuilder content = new StringBuilder();
        URL url = new URL(urlAddress);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            content.append(line).append("\n");
        }
        bufferedReader.close();
        return content.toString();
    }

    private static String getEmoji(String icon){
        return switch (icon){
            case "01d", "01n" -> "\u2600";
            case "02d","02n" -> "\uD83C\uDF24";
            case "03d", "03n", "04d", "04n" -> "\u2601";
            case "09d", "09n" -> "\uD83C\uDF27";
            case "10d", "10n" -> "\uD83C\uDF26";
            case "11d", "11n" -> "\u26C8";
            case "13d", "13n" -> "\u2744";
            case "50d", "50n" -> "\uD83C\uDF2B";
            default -> "\u231B";
        };
    }
}
