package easy.weatherbot.App.Domain.Models.CityHistory;

import easy.weatherbot.App.Domain.Models.UserSession.UserSession;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "city_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private UserSession userSession;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    public CityHistory(UserSession userSession, String city) {
        this.userSession = userSession;
        this.city = city;
    }
}