package net.medrag.microservices.jpa.weather.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.*;
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
public class NewForecastDto {

    @NotNull(message = "Date must not be null.")
    private final LocalDate date;

    @NotNull(message = "Latitude must not be null.")
    @Max(value = 90, message = "Maximal value of latitude is 90.")
    @Min(value = -90, message = "Minimal value of latitude is -90.")
    private final Float lat;

    @NotNull(message = "Longitude must not be null.")
    @Max(value = 180, message = "Maximal value of longitude is 180.")
    @Min(value = -180, message = "Minimal value of longitude is -180.")
    private final Float lon;

    @NotNull(message = "City must not be null.")
    @NotBlank(message = "City name must not be empty.")
    private final String city;

    @NotNull(message = "State must not be null.")
    @NotBlank(message = "State name must not be empty.")
    private final String state;

    @Size(min = 24, max = 24, message = "Temperature array size must be exactly 24.")
    @NotNull(message = "Temperature array size must not be null or empty.")
    private final List<Double> temperatures;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NewForecastDto(
            @JsonProperty("date") LocalDate date,
            @JsonProperty("lat") Float lat,
            @JsonProperty("lon") Float lon,
            @JsonProperty("city") String city,
            @JsonProperty("state") String state,
            @JsonProperty("temperatures") List<Double> temperatures) {
        this.date = date;
        this.lat = lat;
        this.lon = lon;
        this.city = city;
        this.state = state;
        this.temperatures = Collections.unmodifiableList(temperatures);
    }
}
