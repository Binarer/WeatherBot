package easy.weatherbot.App.Infrastructure.Components;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {
    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "Запустить бота"),
            new BotCommand("/help", "Информация о боте"),
            new BotCommand("/weather", "Просмотр погоды"),
            new BotCommand("/history", "История запросов")
    );
    String HELP_TEXT = """
            Этот бот создан для просмотра погоды.
            В настоящий момент доступны следующие команды:

            /start - Запустить бота
            /help - Показать это меню справки
            /weather - Посмотреть погоду
            /history - Показать историю запросов
            """;
}
