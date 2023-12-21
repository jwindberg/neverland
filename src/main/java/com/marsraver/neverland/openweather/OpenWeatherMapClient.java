package com.marsraver.neverland.openweather;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class OpenWeatherMapClient {

    // api key af7bacb2cec87a1604e5e533d2c86cb3

    // https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}

    private String rootUri;
    private String apiKey;

    private RestTemplate restTemplate;

    public OpenWeatherMapClient(String rootUri, String apiKey, RestTemplate restTemplate) {
        this.rootUri = rootUri;
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
    }


    public WeatherResponse getWeather(double lattitude, double longitude) {
        URI uri = UriComponentsBuilder.fromUriString(rootUri)
                .queryParam("lat", lattitude).queryParam("lon", longitude)
                .queryParam("appid", apiKey).build().toUri();
        return restTemplate.getForObject(uri, WeatherResponse.class);
    }
}
