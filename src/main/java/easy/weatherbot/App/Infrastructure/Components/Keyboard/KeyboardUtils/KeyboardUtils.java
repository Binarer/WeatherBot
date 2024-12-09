package easy.weatherbot.App.Infrastructure.Components.Keyboard.KeyboardUtils;

import easy.weatherbot.App.Infrastructure.Components.Keyboard.KeyboardButtons.KeyboardButtons;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardUtils {

    public static ReplyKeyboardMarkup createMainKeyboard() {
        KeyboardRow row = new KeyboardRow();
        row.add(KeyboardButtons.WEATHER_BUTTON);
        row.add(KeyboardButtons.HISTORY_BUTTON);

        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(row);

        return ReplyKeyboardMarkup.builder().keyboard(rows).resizeKeyboard(true).build();
    }

    public static ReplyKeyboardMarkup createCancelKeyboard() {

        KeyboardRow row = new KeyboardRow();
        row.add(KeyboardButtons.CANCEL_BUTTON);

        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(row);

        return ReplyKeyboardMarkup.builder().keyboard(rows).resizeKeyboard(true).oneTimeKeyboard(true).build();
    }
}
