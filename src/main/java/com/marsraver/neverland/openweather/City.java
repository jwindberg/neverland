package com.marsraver.neverland.openweather;

import lombok.Data;

@Data
public class City {
    private Integer id;
    private String name;
    private String state;
    private String country;
    private Coord coord;

    @Data
    public static class Coord {
        private double lon;
        private double lat;
    }

}
