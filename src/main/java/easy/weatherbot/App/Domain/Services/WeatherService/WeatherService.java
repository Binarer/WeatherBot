package easy.weatherbot.App.Domain.Services.WeatherService;

import easy.weatherbot.App.Domain.Models.Weather.WeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {
    private final RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    public String getWeather(String city) {
        try {
            String url = UriComponentsBuilder.fromUriString(apiUrl)
                    .queryParam("q", city)
                    .queryParam("appid", apiKey)
                    .queryParam("units", "metric")
                    .queryParam("lang", "ru")
                    .build()
                    .toUriString();

            log.info("Запрос погоды для города: {}", city);
            WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);

            if (isValidWeatherResponse(response)) {
                String temperature = String.format("%.1f°C", response.getMain().getTemp());
                String description = response.getWeather()[0].getDescription();
                String emoji = getWeatherEmoji(description);

                return String.format(
                        "\nТемпература: %s %s\n" +
                                "Описание: %s 🌬️\n",
                        temperature, emoji, description
                );
            }
            log.warn("Город не найден или данные недоступны: {}", city);
            return null;
        } catch (Exception e) {
            log.error("Ошибка при запросе данных о погоде: {}", e.getMessage());
            return "Ошибка при получении данных о погоде. Проверьте название города.";
        }
    }

    private boolean isValidWeatherResponse(WeatherResponse response) {
        return response != null
                && response.getMain() != null
                && response.getWeather() != null
                && response.getWeather().length > 0;
    }

    private String getWeatherEmoji(String description) {
        return switch (description.toLowerCase()) {
            case "ясно" -> "☀️";
            case "малооблачно" -> "🌤️";
            case "облачно с прояснениями" -> "⛅";
            case "переменная облачность", "пасмурно" -> "☁️";
            case "небольшой дождь" -> "🌦️";
            case "дождь" -> "🌧️";
            case "гроза" -> "⛈️";
            case "снег" -> "❄️";
            case "туман" -> "🌫️";
            default -> "🌪️";
        };
    }
}