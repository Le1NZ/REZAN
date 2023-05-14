package com.example.rezan.data.db;

import java.util.List;

public class Weather {
    public MainInfo main; // Экзмемпляр класса
    public List<WeatherInfo> weather; // Массив

    public class MainInfo {
        public Double temp;
    }

    public class WeatherInfo {
        public String main;
    }
}