package com.marsraver.neverland.openweather;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class OpenWeatherMapClientTest {

    private static final String rootUri = "https://api.openweathermap.org/data/2.5/weather";
    private static final String apiKey = "XXX";
    private static String fileRoot = "/Users/jwindberg/IdeaProjects/neverland/src/test/resources/cities/";

    private ObjectMapper objectMapper = new ObjectMapper();

    private ExecutorService executor = Executors.newFixedThreadPool(5);


    @Test
    public void test() {
        OpenWeatherMapClient openWeatherMapClient = new OpenWeatherMapClient(rootUri, apiKey);
        WeatherResponse weather = openWeatherMapClient.getWeather(47.67877703975015, -122.30305395764644);
        System.out.println(weather);
    }

    @Test
    public void testCities() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        OpenWeatherMapClient openWeatherMapClient = new OpenWeatherMapClient(rootUri, apiKey);
        List<City> cities = objectMapper.readerForListOf(City.class).readValue(this.getClass().getClassLoader().getResource("city.list.json"));
        cities.forEach(city ->
                {

                    executor.execute(new WeatherGetter(openWeatherMapClient, city, objectMapper));

                }
//                city ->
//                new WeatherGetter(openWeatherMapClient, city, objectMapper).run()
        );

        executor.shutdown();

        while (!executor.isTerminated()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Finished all threads");

    }


    @AllArgsConstructor
    public static class WeatherGetter implements Runnable {

        private OpenWeatherMapClient openWeatherMapClient;
        private City city;
        private ObjectMapper objectMapper;

        @Override
        public void run() {
            System.out.println(city.getName());
            String fileName = fileRoot + city.getId() + ".json";
            if (!new File(fileName).exists()) {
                WeatherResponse weather = openWeatherMapClient.getWeather(city.getCoord().getLat(), city.getCoord().getLon());
                System.out.println(weather);
                try {
                    objectMapper.writeValue(new File(fileName), weather);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("File exists, skipping");
            }
        }
    }

}
