package com.marsraver.neverland.openweather;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class WeatherResponse {
    private Coord coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Map<String, Double> rain;
    private Map<String, Integer> clouds;
    private long dt;

    private Sys sys;
    private int timezone;
    private int id;
    private String name;
    private int cod;

    @Data
    public static class Sys {
        private int type;
        private int id;
        private String country;
        private long sunrise;
        private long sunset;
    }


    @Data
    public static class Wind {
        private double speed;
        private double deg;
        private double gust;
    }

    @Data
    public static class Main {
        private double temp;
        private double feels_like;
        private double pressure;
        private double humidity;
        private double temp_min;
        private double temp_max;
    }

    @Data
    private static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;

    }

    @Data
    public static class Coord {
        private double lon;
        private double lat;
    }
}
