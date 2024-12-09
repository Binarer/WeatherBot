package easy.weatherbot.App.Infrastructure.Components.Commands.MenuCommands;

import easy.weatherbot.App.Domain.Models.State.UserState;
import easy.weatherbot.App.Domain.Models.UserSession.UserSession;
import easy.weatherbot.App.Domain.Services.SessionService.SessionService;
import easy.weatherbot.App.Infrastructure.Components.Keyboard.KeyboardUtils.KeyboardUtils;
import easy.weatherbot.App.Infrastructure.Components.MessageUtil.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class MenuCommands {
    private final SessionService sessionService;

    @Autowired
    public MenuCommands(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public SendMessage handleStart(UserSession session) {
        session.setState(UserState.WAITING_FOR_COMMAND);
        sessionService.saveSession(session);
        return MessageUtils.createMessage(
                session.getChatId(),
                "Привет! Выберите команду.",
                KeyboardUtils.createMainKeyboard()
        );
    }

    public SendMessage handleCancel(UserSession session) {
        session.setState(UserState.WAITING_FOR_COMMAND);
        sessionService.saveSession(session);
        return MessageUtils.createMessage(
                session.getChatId(),
                "Действие отменено❌",
                KeyboardUtils.createMainKeyboard()
        );
    }
}
