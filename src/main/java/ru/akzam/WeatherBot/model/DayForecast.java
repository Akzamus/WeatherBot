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
import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
public class DayForecast {
    @NotNull
    private Date date;

    @NotEmpty
    private String weatherEmoji;

    @NotNull
    @Max(100)
    @Min(-100)
    private int morningTemperature;

    @NotNull
    @Max(100)
    @Min(-100)
    private int dayTemperature;

    @NotNull
    @Max(100)
    @Min(-100)
    private int eveningTemperature;

    @NotNull
    @Max(100)
    @Min(-100)
    private int nightTemperature;

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMMM, EEEE",Locale.ENGLISH);

    public String getInfo() {
        return simpleDateFormat.format(date) + " " + weatherEmoji + "\n" +
               "Morning          " + morningTemperature + "°C\n" +
               "Day                  " + dayTemperature     + "°C\n" +
               "Evening           " + eveningTemperature + "°C\n" +
               "Night               " + nightTemperature   + "°C\n";
    }
}
