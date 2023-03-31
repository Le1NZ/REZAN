package com.example.rezan;

import java.util.List;

public class Weather {
    public MainInfo main; // Экзмемпляр класса
    public List<WeatherInfo> weather; // Массив

    class MainInfo {
        public Double temp;
    }

    class WeatherInfo {
        public String main;
    }
}