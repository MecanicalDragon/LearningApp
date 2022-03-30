package net.medrag.microservices.jpa.weather.controller;

import net.medrag.microservices.jpa.weather.dto.NewForecastDto;
import net.medrag.microservices.jpa.weather.dto.WeatherDto;
import net.medrag.microservices.jpa.weather.model.WeatherSearchParams;
import net.medrag.microservices.jpa.weather.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping
    public ResponseEntity<WeatherDto> addWeatherData(
            @Valid @NotNull @RequestBody NewForecastDto weatherDto,
            HttpServletRequest httpServletRequest
    ) {
        log.trace("New weather data has been received: <{}>.", weatherDto);
        final WeatherDto saved = weatherService.addWeatherData(weatherDto);
        log.info("New weather data has been saved. Data id: <{}>.", saved.getId());
        return ResponseEntity.created(buildLocationUri(httpServletRequest, saved)).body(saved);
    }

    @GetMapping(("/{id}"))
    public ResponseEntity<WeatherDto> getWeather(@PathVariable Integer id) {
        log.trace("Weather forecast with id <{}> has been requested.", id);
        return ResponseEntity.ok(weatherService.getWeatherData(id));
    }

    @GetMapping
    public ResponseEntity<List<WeatherDto>> getWeatherList(
            @RequestParam(required = false) List<String> city,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) WeatherSearchParams.Sort sort
    ) {
        log.trace("Weather forecasts for cities [{}] and date <{}> have been requested.", city, date);
        return ResponseEntity.ok(weatherService.getWeatherDataList(new WeatherSearchParams(city, date, sort)));
    }

    private URI buildLocationUri(HttpServletRequest request, WeatherDto weatherDto) {
        return URI.create(request.getRequestURL().append("/").append(weatherDto.getId()).toString());
    }
}
