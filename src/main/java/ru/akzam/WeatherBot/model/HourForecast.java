package ru.akzam.WeatherBot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class HourForecast {
    @NotNull
    private Date date;

    @NotNull
    @Max(100)
    @Min(-100)
    private int temperature;

    @NotEmpty
    private String weatherEmoji;

    @NotEmpty
    private String description;

    @NotNull
    private SimpleDateFormat simpleDateFormat;

    public String getInfo() {
        return simpleDateFormat.format(date) + ":\nTemperature: " + temperature + "°C\n" +
               description.substring(0, 1).toUpperCase() + description.substring(1) + " " + weatherEmoji + "\n";
    }
}
