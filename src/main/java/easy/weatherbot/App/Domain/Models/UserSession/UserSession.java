package easy.weatherbot.App.Domain.Models.UserSession;

import easy.weatherbot.App.Domain.Models.CityHistory.CityHistory;
import easy.weatherbot.App.Domain.Models.State.UserState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "user_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long chatId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserState state = UserState.NONE;

    @OneToMany(mappedBy = "userSession", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CityHistory> cityHistory = new LinkedList<>();

    public void addCityToHistory(String city) {
        if (cityHistory.size() >= 5) {
            cityHistory.remove(0);
        }
        cityHistory.add(new CityHistory(this, city));
    }
}