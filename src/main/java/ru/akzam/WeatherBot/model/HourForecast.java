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
public class HourForecast {
    @NotNull
    private Date date;

    @NotNull
    @Max(100)
    @Min(-100)
    private int temperature;

    @NotEmpty
    private String weatherEmoji;

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:00", Locale.ENGLISH);

    public String getInfo() {
        return simpleDateFormat.format(date) + ":     " + temperature + "°C   " + weatherEmoji;
    }
}
