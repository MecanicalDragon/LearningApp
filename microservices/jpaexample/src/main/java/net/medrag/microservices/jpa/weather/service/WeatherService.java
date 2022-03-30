package net.medrag.microservices.jpa.weather.service;

import net.medrag.microservices.jpa.weather.dto.NewForecastDto;
import net.medrag.microservices.jpa.weather.dto.WeatherDto;
import net.medrag.microservices.jpa.weather.model.EntryDoesNotExistException;
import net.medrag.microservices.jpa.weather.model.WeatherSearchParams;
import net.medrag.microservices.jpa.weather.model.entity.WeatherEntity;
import net.medrag.microservices.jpa.weather.repository.WeatherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Stanislav Tretyakov
 * 30.03.2022
 */
@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final ModelMapper modelMapper;

    public WeatherService(
            WeatherRepository weatherRepository,
            @Qualifier("weather") ModelMapper modelMapper) {
        this.weatherRepository = weatherRepository;
        this.modelMapper = modelMapper;
    }

    public WeatherDto addWeatherData(NewForecastDto forecast) {
        final WeatherEntity weather = modelMapper.map(forecast, WeatherEntity.class);
        final WeatherEntity saved = weatherRepository.save(weather);
        return modelMapper.map(saved, WeatherDto.class);
    }

    public WeatherDto getWeatherData(Integer id) {
        return weatherRepository.findById(id).map(it -> modelMapper.map(it, WeatherDto.class))
                .orElseThrow(() -> new EntryDoesNotExistException(id));
    }

    public List<WeatherDto> getWeatherDataList(WeatherSearchParams searchParams) {
        final List<WeatherEntity> all = weatherRepository.findAll(new WeatherSpecification(searchParams));
        return all.stream().map(it -> modelMapper.map(it, WeatherDto.class)).collect(Collectors.toList());
    }
}
