package ru.akzam.WeatherBot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
public class DayForecast {

    private Date date;
    private String weatherEmoji;
    private int morningTemperature;
    private int dayTemperature;
    private int eveningTemperature;
    private int nightTemperature;

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMMM, EEEE", Locale.ENGLISH);

    public String getInfo() {
        return simpleDateFormat.format(date) + " " + weatherEmoji + "\n" +
               "Morning          " + morningTemperature + "°C\n" +
               "Day                  " + dayTemperature     + "°C\n" +
               "Evening           " + eveningTemperature + "°C\n" +
               "Night               " + nightTemperature   + "°C\n";
    }
}
