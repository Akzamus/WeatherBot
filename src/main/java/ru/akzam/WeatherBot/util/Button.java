package ru.akzam.WeatherBot.util;

public enum Button {

    NOW("now"),
    DAY("day"),
    WEEK("week"),
    RESET("reset"),
    DEFAULT("default");

    private final String text;

    Button(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
