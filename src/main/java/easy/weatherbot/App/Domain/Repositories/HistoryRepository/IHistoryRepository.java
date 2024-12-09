package easy.weatherbot.App.Domain.Repositories.HistoryRepository;

import easy.weatherbot.App.Domain.Models.CityHistory.CityHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHistoryRepository extends JpaRepository<CityHistory, Long> {
}
