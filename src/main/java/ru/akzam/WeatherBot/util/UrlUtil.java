package ru.akzam.WeatherBot.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Component
public class UrlUtil {

    @Value("${openweathermap.key}")
    private String apiKeyWeather;

    @Value("${googleMaps.key}")
    private String apiKeyGeocoder;

    public String getUrlToNowForecast(String location) {
        return "https://api.openweathermap.org/data/2.5/weather?q=" + location +
                "&appid=" + apiKeyWeather +"&units=metric";
    }

    public String getUrlToDayForecast(double latitude, double longitude) {
        return "https://api.openweathermap.org/data/3.0/onecall?lat=" + latitude + "&lon=" +
                longitude + "&exclude=current,minutely,daily,alerts&appid=" + apiKeyWeather + "&units=metric";
    }

    public String getUrlToWeekForecast(double latitude, double longitude) {
        return "https://api.openweathermap.org/data/3.0/onecall?lat=" + latitude + "&lon=" +
                longitude + "&exclude=current,minutely,hourly,alerts&appid=" + apiKeyWeather + "&units=metric";
    }

    public String getUrlToGeocoding(String location) {
        return "https://maps.googleapis.com/maps/api/geocode/json?address=" + location + "&key=" + apiKeyGeocoder;
    }

    public String getUrlContent(String urlAddress) throws IOException {
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
}
