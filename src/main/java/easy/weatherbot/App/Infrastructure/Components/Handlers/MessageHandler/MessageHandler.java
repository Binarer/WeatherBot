package easy.weatherbot.App.Infrastructure.Components.Handlers.MessageHandler;

import easy.weatherbot.App.Infrastructure.Components.Handlers.CallbackHandler.CallbackHandler;
import easy.weatherbot.App.Infrastructure.Components.Handlers.CommandHandler.CommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class MessageHandler {

    private final CommandHandler commandHandler;
    private final CallbackHandler callbackHandler;

    public MessageHandler(CommandHandler commandHandler, CallbackHandler callbackHandler) {
        this.commandHandler = commandHandler;
        this.callbackHandler = callbackHandler;
    }

    public SendMessage processUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String command = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            return commandHandler.processCommand(command, chatId);
        }

        if (update.hasCallbackQuery()) {
            return callbackHandler.processCallback(update.getCallbackQuery());
        }

        log.warn("неизвестный тип update");
        return null;
    }
}
