package easy.weatherbot.App.Domain.Services.CityHistoryService;

import easy.weatherbot.App.Domain.Models.CityHistory.CityHistory;
import easy.weatherbot.App.Domain.Models.UserSession.UserSession;
import easy.weatherbot.App.Domain.Repositories.HistoryRepository.IHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityHistoryService {
    private final IHistoryRepository cityHistoryRepository;
    @Transactional
    public void addCityToHistory(UserSession session, String city) {
        boolean cityExists = session.getCityHistory().stream()
                .anyMatch(ch -> ch.getCity().equalsIgnoreCase(city));

        if (!cityExists) {
            if (session.getCityHistory().size() >= 5) {
                session.getCityHistory().removeFirst();
            }
            CityHistory cityHistory = new CityHistory(session, city);
            session.getCityHistory().add(cityHistory);
            cityHistoryRepository.save(cityHistory);
        }
    }

    @Transactional
    public List<CityHistory> getCityHistory(UserSession session) {
        return session.getCityHistory().stream()
                .sorted(Comparator.comparing(CityHistory::getTimestamp).reversed())
                .collect(Collectors.toList());
    }
}
