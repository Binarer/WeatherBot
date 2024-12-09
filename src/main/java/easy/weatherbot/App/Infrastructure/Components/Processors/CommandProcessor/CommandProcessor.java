package easy.weatherbot.App.Infrastructure.Components.Processors.CommandProcessor;

import easy.weatherbot.App.Domain.Models.State.UserState;
import easy.weatherbot.App.Domain.Models.UserSession.UserSession;
import easy.weatherbot.App.Domain.Services.SessionService.SessionService;
import easy.weatherbot.App.Infrastructure.Components.Commands.MenuCommands.MenuCommands;
import easy.weatherbot.App.Infrastructure.Components.Commands.WeatherCommands.WeatherCommands;
import easy.weatherbot.App.Infrastructure.Components.MessageUtil.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Component
public class CommandProcessor {
    private final MenuCommands menuCommands;
    private final WeatherCommands weatherCommands;
    private final SessionService sessionService;

    @Autowired
    public CommandProcessor(MenuCommands menuCommands, WeatherCommands weatherCommands, SessionService sessionService) {
        this.menuCommands = menuCommands;
        this.weatherCommands = weatherCommands;
        this.sessionService = sessionService;
    }

    @Transactional
    public SendMessage processCommand(String command, Long chatId) {
        UserSession session = sessionService.getSession(chatId);
        try {
            if (session.getState() == UserState.ENTERING_CITY) {
                return processCityInput(session, command);
            }
            return processGeneralCommand(session, command);
        } catch (Exception e) {
            log.error("Ошибка при обработке команды: {}", e.getMessage());
            return MessageUtils.createMessage(chatId, "Произошла ошибка. Попробуйте снова.");
        }
    }

    private SendMessage processGeneralCommand(UserSession session, String command) {
        return switch (command.toLowerCase()) {
            case "/start" -> menuCommands.handleStart(session);
            case "/weather" -> weatherCommands.handleWeather(session);
            case "/history" -> weatherCommands.handleHistory(session);
            case "отмена", "/cancel" -> menuCommands.handleCancel(session);
            default -> handleUnknown(session);
        };
    }

    private SendMessage processCityInput(UserSession session, String city) {
        if (city.startsWith("/")) {
            return MessageUtils.createMessage(session.getChatId(), "Введите название города, а не команду.");
        }
        if ("отмена".equalsIgnoreCase(city.trim())) {
            return menuCommands.handleCancel(session);
        }
        log.info("Выбранный город: {}", city.trim());
        return weatherCommands.handleCity(session, city.trim());
    }

    private SendMessage handleUnknown(UserSession session) {
        return MessageUtils.createMessage(
                session.getChatId(),
                "Неизвестная команда⚠"
        );
    }
}