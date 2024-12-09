package easy.weatherbot.App.Infrastructure.Components.Keyboard.KeyboardButtons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

public class KeyboardButtons {
    public static final KeyboardButton WEATHER_BUTTON =
            KeyboardButton.builder().text("/weather").build();

    public static final KeyboardButton HISTORY_BUTTON =
            KeyboardButton.builder().text("/history").build();

    public static final KeyboardButton CANCEL_BUTTON =
            KeyboardButton.builder().text("Отмена").build();
}
