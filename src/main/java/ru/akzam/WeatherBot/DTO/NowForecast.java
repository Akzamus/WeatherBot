package ru.akzam.WeatherBot.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.akzam.WeatherBot.util.Emoji;

@Getter
@Setter
@AllArgsConstructor
public class NowForecast {

    private String description;
    private String weatherEmoji;
    private double temperature;
    private double feelsLike;
    private double max;
    private double min;
    private double pressure;

    public String getInfo() {
        return description.substring(0,1).toUpperCase() + description.substring(1) + " " + weatherEmoji + "\n"
                + "Temperature " + Emoji.THERMOMETER + "  " + temperature + " °C\n"
                + "Feels like               " + feelsLike + " °C\n"
                + "Max                        " + max + " °C\n"
                + "Min                         " + min + " °C\n"
                + "Pressure                " + pressure + " hPa";
    }
}
