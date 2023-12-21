package com.marsraver.neverland.openweather;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WeatherConfig {

    private static final String rootUri = "https://api.openweathermap.org/data/2.5/weather";
    private static final String apiKey = "af7bacb2cec87a1604e5e533d2c86cb3";

    @Bean
    public OpenWeatherMapClient openWeatherMapClient(RestTemplate restTemplate) {
        return new OpenWeatherMapClient(rootUri, apiKey, restTemplate);
    }
}
