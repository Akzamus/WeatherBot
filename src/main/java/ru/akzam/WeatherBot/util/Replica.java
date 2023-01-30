package ru.akzam.WeatherBot.util;


public enum Replica {

    GREETING("Hello " + Emoji.WAVE),
    ADVICE("Enter the name of the city, country or continent:"),
    CORRECT_LOCATION("Thank you, location found, nice to use " + Emoji.LIKE),
    INCORRECT_LOCATION("Sorry, the location was not found, maybe you made a mistake, try again."),
    UNDEFINED_ERROR("Sorry there was an error in the program("),
    USE_KEYBOARD("Please use the keyboard."),
    LOCATION_DROPPED("Location reset " + Emoji.CHECK_MARK);

    private final String text;

    Replica(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
