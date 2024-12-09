package easy.weatherbot.App.Infrastructure.Components.MessageUtil;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class MessageUtils {
    public static SendMessage createMessage(Long chatId, String text) {
        return SendMessage.builder().chatId(chatId).text(text).build();
    }
    public static SendMessage createMessage(Long chatId, String text, InlineKeyboardMarkup keyboardMarkup) {
        return SendMessage.builder().chatId(chatId).text(text).replyMarkup(keyboardMarkup).build();
    }
    public static SendMessage createMessage(Long chatId, String text, ReplyKeyboardMarkup keyboardMarkup) {
        return SendMessage.builder().chatId(chatId).text(text).replyMarkup(keyboardMarkup).build();
    }
}
