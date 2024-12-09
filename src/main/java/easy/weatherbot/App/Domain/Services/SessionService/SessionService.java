package easy.weatherbot.App.Domain.Services.SessionService;

import easy.weatherbot.App.Domain.Models.UserSession.UserSession;
import easy.weatherbot.App.Domain.Repositories.UserSessionRepository.UserSessionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final UserSessionRepository userSessionRepository;

    @Transactional
    public UserSession getSession(Long chatId) {
        return userSessionRepository.findByChatId(chatId)
                .orElseGet(() -> {
                    UserSession session = new UserSession();
                    session.setChatId(chatId);
                    return userSessionRepository.save(session);
                });
    }
    @Transactional
    public void saveSession(UserSession session) {
        userSessionRepository.save(session);
    }

    @Transactional
    public void deleteSession(Long chatId) {
        userSessionRepository.findByChatId(chatId)
                .ifPresent(userSessionRepository::delete);
    }

    @Transactional
    public void addCityToHistory(Long chatId, String city) {
        UserSession session = getSession(chatId);
        session.addCityToHistory(city);
        saveSession(session);
    }
}
