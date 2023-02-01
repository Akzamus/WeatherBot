package ru.akzam.WeatherBot.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class HourForecast {

    private Date date;
    private int temperature;
    private String weatherEmoji;
    private String description;
    private SimpleDateFormat simpleDateFormat;

    public String getInfo() {
        return simpleDateFormat.format(date) + ":\nTemperature: " + temperature + "°C\n" +
               description.substring(0, 1).toUpperCase() + description.substring(1) + " " + weatherEmoji + "\n";
    }
}
