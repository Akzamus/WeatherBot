package ru.akzam.WeatherBot.util;

public enum Emoji {

    WAVE("\uD83D\uDC4B"),
    LIKE("\uD83D\uDC4D"),
    THERMOMETER("\uD83C\uDF21"),
    SUN("\u2600"),
    SUN_WITH_SMALL_CLOUDS("\uD83C\uDF24"),
    CLOUD("\u2601"),
    CLOUD_WITH_RAIN("\uD83C\uDF27"),
    SUN_WITH_CLOUDS_WITH_RAIN("\uD83C\uDF26"),
    THUNDER("\u26C8"),
    SNOWFLAKE("\u2744"),
    FOG("\uD83C\uDF2B"),
    HOURGLASS("\u231B"),
    CHECK_MARK("\u2705");

    private final String code;

    Emoji(String code) {
        this.code = code;
    }

    public static String getEmojiByIcon(String icon) {
        return (switch (icon) {
            case "01d", "01n" -> SUN;
            case "02d", "02n" -> SUN_WITH_SMALL_CLOUDS;
            case "03d", "03n", "04d", "04n" -> CLOUD;
            case "09d", "09n" -> CLOUD_WITH_RAIN;
            case "10d", "10n" -> SUN_WITH_CLOUDS_WITH_RAIN;
            case "11d", "11n" -> THUNDER;
            case "13d", "13n" -> SNOWFLAKE;
            case "50d", "50n" -> FOG;
            default           -> HOURGLASS;
        }).toString();
    }

    @Override
    public String toString() {
        return this.code;
    }
}
