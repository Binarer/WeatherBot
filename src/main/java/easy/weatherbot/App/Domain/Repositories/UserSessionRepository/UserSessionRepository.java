package easy.weatherbot.App.Domain.Repositories.UserSessionRepository;

import easy.weatherbot.App.Domain.Models.UserSession.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Optional<UserSession> findByChatId(Long chatId);
}
