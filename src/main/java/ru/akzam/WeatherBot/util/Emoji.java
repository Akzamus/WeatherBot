package ru.akzam.WeatherBot.util;

public class Emoji {
    public final  static String WAVE = "\uD83D\uDC4B";
    public final  static String LIKE = "\uD83D\uDC4D";
    public final static String THERMOMETER = "\uD83C\uDF21";
    public final static String SUN = "\u2600";
    public final static String SUN_WITH_SMALL_CLOUDS = "\uD83C\uDF24";
    public final static String CLOUD = "\u2601";
    public final static String CLOUD_WITH_RAIN = "\uD83C\uDF27";
    public final static String SUN_WITH_CLOUDS_WITH_RAIN = "\uD83C\uDF26";
    public final static String THUNDER = "\u26C8";
    public final static String SNOWFLAKE = "\u2744";
    public final static String FOG = "\uD83C\uDF2B";
    public final static String HOURGLASS = "\u231B";
    public final static String CHECK_MARK = "\u2705";

    public static String getEmojiByIcon(String icon) {
        return switch (icon) {
            case "01d", "01n" -> SUN;
            case "02d", "02n" -> SUN_WITH_SMALL_CLOUDS;
            case "03d", "03n", "04d", "04n" -> CLOUD;
            case "09d", "09n" -> CLOUD_WITH_RAIN;
            case "10d", "10n" -> SUN_WITH_CLOUDS_WITH_RAIN;
            case "11d", "11n" -> THUNDER;
            case "13d", "13n" -> SNOWFLAKE;
            case "50d", "50n" -> FOG;
            default           -> HOURGLASS;
        };
    }

}
