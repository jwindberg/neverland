package com.marsraver.neverland.openweather;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@AllArgsConstructor
public class WeatherController {

    private OpenWeatherMapClient openWeatherMapClient;

    @GetMapping
    public ResponseEntity<WeatherResponse> getWeather(@RequestParam(value = "lat", defaultValue = "47.67877703975015") double lattitude, @RequestParam(value = "lon", defaultValue = "-122.30305395764644") double longitude) {
        return ResponseEntity.ok(openWeatherMapClient.getWeather(lattitude, longitude));
    }
}
