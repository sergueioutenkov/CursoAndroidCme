package com.serguei.cursos.cursoandroidcme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by serguei on 03/10/16.
 */

public class Forecast {
    private int image;
    private String location;
    private String description;
    private String temperature;


    public Forecast(int image, String location, String description, String temperature) {
        this.image = image;
        this.location = location;
        this.description = description;
        this.temperature = temperature;
    }

    /**
     * Metodo estatico que genera una lista de forecasts falsos para propositos de mock
     *
     * @return
     */
    public static List<Forecast> getFakeForecasts() {
        List<Forecast> fakeForecasts = new ArrayList<>();

        Forecast fakeForecast1 = new Forecast(R.drawable.ic_cloudy, "Mendoza", "Parcialmente Nublado", "15°C");
        Forecast fakeForecast2 = new Forecast(R.drawable.ic_sunny, "Mendoza", "Soleado", "25°C");

        fakeForecasts.add(fakeForecast1);
        fakeForecasts.add(fakeForecast2);
        fakeForecasts.add(fakeForecast1);
        fakeForecasts.add(fakeForecast2);
        fakeForecasts.add(fakeForecast1);
        fakeForecasts.add(fakeForecast2);
        fakeForecasts.add(fakeForecast1);
        fakeForecasts.add(fakeForecast2);
        fakeForecasts.add(fakeForecast1);
        fakeForecasts.add(fakeForecast2);
        fakeForecasts.add(fakeForecast1);
        fakeForecasts.add(fakeForecast2);

        return fakeForecasts;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
