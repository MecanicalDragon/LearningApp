package net.medrag.microservices.jpa.weather.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 30.03.2022
 */
@Getter
@EqualsAndHashCode
@ToString
public class WeatherDto {
    private final Integer id;
    private final LocalDate date;
    private final Float lat;
    private final Float lon;
    private final String city;
    private final String state;
    private final List<Double> temperatures;

    public WeatherDto(Integer id, LocalDate date, Float lat, Float lon, String city, String state, List<Double> temperatures) {
        this.id = id;
        this.date = date;
        this.lat = lat;
        this.lon = lon;
        this.city = city;
        this.state = state;
        this.temperatures = Collections.unmodifiableList(temperatures);
    }
}