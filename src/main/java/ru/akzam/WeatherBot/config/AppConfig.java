package ru.akzam.WeatherBot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import ru.akzam.WeatherBot.model.TelegramBot;
import ru.akzam.WeatherBot.botconfig.TelegramBotConfig;
import ru.akzam.WeatherBot.model.TelegramFacade;

@Configuration
public class AppConfig {
    private final TelegramBotConfig telegramBotConfig;

    public AppConfig(TelegramBotConfig telegramBotConfig) {
        this.telegramBotConfig = telegramBotConfig;
    }

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(telegramBotConfig.getWebHookPath()).build();
    }

    @Bean
    public TelegramBot springWebhookBot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
        TelegramBot telegramBot = new TelegramBot(telegramFacade, setWebhook);
        telegramBot.setBotToken(telegramBotConfig.getBotToken());
        telegramBot.setBotUsername(telegramBotConfig.getUserName());
        telegramBot.setBotPath(telegramBotConfig.getWebHookPath());
        return telegramBot;
    }
}
