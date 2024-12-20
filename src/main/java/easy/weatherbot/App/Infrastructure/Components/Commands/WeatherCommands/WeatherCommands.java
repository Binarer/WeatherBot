package easy.weatherbot.App.Infrastructure.Components.Commands.WeatherCommands;

import easy.weatherbot.App.Domain.Models.CityHistory.CityHistory;
import easy.weatherbot.App.Domain.Models.State.UserState;
import easy.weatherbot.App.Domain.Models.UserSession.UserSession;
import easy.weatherbot.App.Domain.Services.SessionService.SessionService;
import easy.weatherbot.App.Domain.Services.WeatherService.WeatherService;
import easy.weatherbot.App.Infrastructure.Components.Keyboard.KeyboardUtils.KeyboardUtils;
import easy.weatherbot.App.Infrastructure.Components.MessageUtil.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WeatherCommands {
    private final WeatherService weatherService;
    private final SessionService sessionService;

    public SendMessage handleWeather(UserSession session) {
        session.setState(UserState.ENTERING_CITY);
        sessionService.saveSession(session);
        return MessageUtils.createMessage(
                session.getChatId(),
                "Введите название города или нажмите \"Отмена\".",
                KeyboardUtils.createCancelKeyboard()
        );
    }

    public SendMessage handleHistory(UserSession session) {
        List<CityHistory> cityHistory = sessionService.getCityHistory(session.getChatId());
        String history = cityHistory.isEmpty()
                ? "История городов пуста."
                : cityHistory.stream()
                .map(CityHistory::getCity)
                .reduce((a, b) -> a + "\n🌆 " + b)
                .orElse("История городов пуста.🙉");
        return MessageUtils.createMessage(session.getChatId(), "Последние города:\n🌆 " + history);
    }

    public SendMessage handleCity(UserSession session, String city) {
        String weather = weatherService.getWeather(city);

        if (weather == null || weather.startsWith("Ошибка")) {
            return MessageUtils.createMessage(
                    session.getChatId(),
                    "Город \"" + city + "\" не найден. Проверьте ввод и попробуйте снова.😐"
            );
        }

        sessionService.addCityToHistory(session.getChatId(), city);
        session.setState(UserState.WAITING_FOR_COMMAND);
        sessionService.saveSession(session);

        return MessageUtils.createMessage(
                session.getChatId(),
                "Погода в " + city + "🌡 " + weather,
                KeyboardUtils.createMainKeyboard()
        );
    }
}
