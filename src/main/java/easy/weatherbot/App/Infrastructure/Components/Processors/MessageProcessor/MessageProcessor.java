package easy.weatherbot.App.Infrastructure.Components.Processors.MessageProcessor;

import easy.weatherbot.App.Infrastructure.Components.Processors.CallbackProcessor.CallbackProcessor;
import easy.weatherbot.App.Infrastructure.Components.Processors.CommandProcessor.CommandProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class MessageProcessor {

    private final CommandProcessor commandProcessor;
    private final CallbackProcessor callbackProcessor;

    public MessageProcessor(CommandProcessor commandProcessor, CallbackProcessor callbackProcessor) {
        this.commandProcessor = commandProcessor;
        this.callbackProcessor = callbackProcessor;
    }

    public SendMessage processUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String command = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            return commandProcessor.processCommand(command, chatId);
        }

        if (update.hasCallbackQuery()) {
            return callbackProcessor.processCallback(update.getCallbackQuery());
        }

        log.warn("Unsupported update type");
        return null;
    }
}
