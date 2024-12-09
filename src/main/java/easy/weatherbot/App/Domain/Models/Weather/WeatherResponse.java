package easy.weatherbot.App.Domain.Models.Weather;

import lombok.Data;

@Data
public class WeatherResponse {
    private Main main;
    private Weather[] weather;
    private String name;

    @Data
    public static class Main {
        private double temp;
    }

    @Data
    public static class Weather {
        private String description;
    }
}
