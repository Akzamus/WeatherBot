package ru.akzam.WeatherBot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.akzam.WeatherBot.util.Emoji;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class NowForecast {

    @NotEmpty
    private String description;

    @NotEmpty
    private String weatherEmoji;

    @NotNull
    @Max(100)
    @Min(-100)
    private double temperature;

    @NotNull
    @Max(100)
    @Min(-100)
    private double feelsLike;

    @NotNull
    @Max(100)
    @Min(-100)
    private double max;

    @NotNull
    @Max(100)
    @Min(-100)
    private double min;

    @NotNull
    @Max(100)
    @Min(-100)
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
