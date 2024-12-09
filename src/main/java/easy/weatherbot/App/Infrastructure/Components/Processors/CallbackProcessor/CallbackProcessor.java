package easy.weatherbot.App.Infrastructure.Components.Processors.CallbackProcessor;

import easy.weatherbot.App.Infrastructure.Components.Keyboard.KeyboardUtils.KeyboardUtils;
import easy.weatherbot.App.Infrastructure.Components.MessageUtil.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Slf4j
@Component
public class CallbackProcessor {

    @Transactional
    public SendMessage processCallback(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        String callbackData = callbackQuery.getData();

        return switch (callbackData) {
            default -> {
                log.warn("Unknown callback: {}", callbackData);
                yield MessageUtils.createMessage(chatId, "Неизвестный выбор: " + callbackData);
            }
        };
    }
}
