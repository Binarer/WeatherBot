package easy.weatherbot.App.Domain.Services.SessionService;

import easy.weatherbot.App.Domain.Models.CityHistory.CityHistory;
import easy.weatherbot.App.Domain.Models.UserSession.UserSession;
import easy.weatherbot.App.Domain.Repositories.UserSessionRepository.IUserSessionRepository;
import easy.weatherbot.App.Domain.Services.CityHistoryService.CityHistoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final IUserSessionRepository IUserSessionRepository;
    private final CityHistoryService cityHistoryService;

    @Transactional
    public UserSession getSession(Long chatId) {
        return IUserSessionRepository.findByChatId(chatId)
                .orElseGet(() -> {
                    UserSession session = new UserSession();
                    session.setChatId(chatId);
                    return IUserSessionRepository.save(session);
                });
    }
    @Transactional
    public void saveSession(UserSession session) {
        IUserSessionRepository.save(session);
    }

    @Transactional
    public void deleteSession(Long chatId) {
        IUserSessionRepository.findByChatId(chatId)
                .ifPresent(IUserSessionRepository::delete);
    }
    @Transactional
    public void addCityToHistory(Long chatId, String city) {
        UserSession session = getSession(chatId);
        cityHistoryService.addCityToHistory(session, city);
        saveSession(session);
    }
    @Transactional
    public List<CityHistory> getCityHistory(Long chatId) {
        UserSession session = getSession(chatId);
        return cityHistoryService.getCityHistory(session);
    }
}
