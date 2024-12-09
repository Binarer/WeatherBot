package easy.weatherbot.App.Application.Bot;

import easy.weatherbot.App.Infrastructure.Components.BotCommands;
import easy.weatherbot.App.Infrastructure.Components.Handlers.MessageHandler.MessageHandler;
import easy.weatherbot.App.Infrastructure.Config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class WeatherBot extends TelegramLongPollingBot implements BotCommands {
    private final BotConfig config;
    private final MessageHandler messageHandler;

    public WeatherBot(BotConfig config, MessageHandler messageHandler) {
        this.config = config;
        this.messageHandler = messageHandler;
        setCommands();
    }

    private void setCommands() {
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("ошибки: ", e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update == null) return;

            SendMessage response = messageHandler.processUpdate(update);
            if (response != null) {
                execute(response);
            }
        } catch (Exception e) {
            log.error("Error while processing update: ", e);
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }
}
