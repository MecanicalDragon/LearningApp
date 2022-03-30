package net.medrag.microservices.jpa.weather.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 30.03.2022
 */
@Getter
@EqualsAndHashCode
public final class WeatherSearchParams {

    private final List<String> city;
    private final LocalDate date;
    private final Sort sort;

    public WeatherSearchParams(List<String> city, LocalDate date, Sort sort) {
        this.city = city == null ? Collections.emptyList() : Collections.unmodifiableList(city);
        this.date = date;
        this.sort = sort;
    }

    public enum Sort {
        ASC,
        DESC
    }
}
