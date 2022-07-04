package ru.akzam.WeatherBot.servisec;

import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.akzam.WeatherBot.model.DayForecast;
import ru.akzam.WeatherBot.model.HourForecast;
import ru.akzam.WeatherBot.model.NowForecast;
import ru.akzam.WeatherBot.util.Emoji;
import ru.akzam.WeatherBot.util.UrlUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Getter
public class WeatherService {

    private final UrlUtil urlUtil;

    @Autowired
    public WeatherService(UrlUtil urlUtil) {
        this.urlUtil = urlUtil;
    }

    public NowForecast getNowForecast(String location) throws IOException {
        String output = urlUtil.getUrlContent(urlUtil.getUrlToNowForecast(location));
        JSONObject jsonObject = new JSONObject(output);
        JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);
        JSONObject main = jsonObject.getJSONObject("main");
        return new NowForecast(
                weather.getString("description"),
                Emoji.getEmojiByIcon(weather.getString("icon")),
                main.getDouble("temp"),
                main.getDouble("feels_like"),
                main.getDouble("temp_max"),
                main.getDouble("temp_min"),
                main.getDouble("pressure")
        );
    }

    public List<HourForecast> getDayForecast(double latitude, double longitude) throws IOException {
        String output = urlUtil.getUrlContent(urlUtil.getUrlToDayForecast(latitude, longitude));
        JSONArray hourlyArray = new JSONObject(output).getJSONArray("hourly");
        List<HourForecast> hourForecasts = new ArrayList<>();

        for (int i = 1; i < 25; i++) {
            JSONObject hour = hourlyArray.getJSONObject(i);
            JSONObject weather = hour.getJSONArray("weather").getJSONObject(0);
            hourForecasts.add(new HourForecast(
               new Date(hour.getLong("dt") * 1000),
               (int)Math.round(hour.getDouble("temp")),
               Emoji.getEmojiByIcon(weather.getString("icon")),
               weather.getString("description")
            ));
        }
        return hourForecasts;
    }

    public List<DayForecast> getWeekForecast(double latitude, double longitude) throws IOException {
        String output = urlUtil.getUrlContent(urlUtil.getUrlToWeekForecast(latitude, longitude));
        JSONArray dailyArray = new JSONObject(output).getJSONArray("daily");
        List<DayForecast> dayForecasts = new ArrayList<>();

        for (int i = 1; i < 8; i++) {
            JSONObject day = dailyArray.getJSONObject(i);
            JSONObject temperature = day.getJSONObject("temp");
            dayForecasts.add(new DayForecast(
                    new Date(day.getLong("dt") * 1000),
                    Emoji.getEmojiByIcon(day.getJSONArray("weather").getJSONObject(0).getString("icon")),
                    (int)Math.round(temperature.getDouble("morn")),
                    (int)Math.round(temperature.getDouble("day")),
                    (int)Math.round(temperature.getDouble("eve")),
                    (int)Math.round(temperature.getDouble("night"))
            ));
        }
        return dayForecasts;
    }
}
