package net.medrag.microservices.jpa.weather;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import net.medrag.microservices.jpa.weather.dto.NewForecastDto;
import net.medrag.microservices.jpa.weather.dto.WeatherDto;
import net.medrag.microservices.jpa.weather.model.WeatherSearchParams;
import net.medrag.microservices.jpa.weather.model.entity.WeatherEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Stanislav Tretyakov
 * 30.03.2022
 */
@Configuration
public class WeatherConfig {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern(DATE_FORMAT);
    }

    @Bean
    public org.springframework.core.convert.converter.Converter<String, LocalDate> localDateConverter() {
        return new org.springframework.core.convert.converter.Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String s) {
                return LocalDate.parse(s, dateTimeFormatter());
            }
        };
    }

    @Bean
    public org.springframework.core.convert.converter.Converter<String, WeatherSearchParams.Sort> sortConverter() {
        return new org.springframework.core.convert.converter.Converter<String, WeatherSearchParams.Sort>() {
            @Override
            public WeatherSearchParams.Sort convert(String s) {
                return switch (s) {
                    case "date" -> WeatherSearchParams.Sort.ASC;
                    case "-date" -> WeatherSearchParams.Sort.DESC;
                    default -> throw new IllegalArgumentException(
                            String.format("Sort class can not be built with value <%s>", s)
                    );
                };
            }
        };
    }

    @Bean
    public ObjectMapper objectMapper() {
        SimpleModule customDateModule = new SimpleModule();
        customDateModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateTimeFormatter()));
        customDateModule.addSerializer(new LocalDateSerializer(dateTimeFormatter()));
        return new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .registerModule(new JavaTimeModule())
                .registerModule(customDateModule);
    }

    @Bean
    public Converter<WeatherEntity, WeatherDto> weatherDtoConverter() {
        return new Converter<WeatherEntity, WeatherDto>() {
            @Override
            public WeatherDto convert(MappingContext<WeatherEntity, WeatherDto> mappingContext) {
                final WeatherEntity source = mappingContext.getSource();
                return new WeatherDto(
                        source.getId(),
                        source.getDate(),
                        source.getLat(),
                        source.getLon(),
                        source.getCity(),
                        source.getState(),
                        source.getTemperatures()
                );
            }
        };
    }

    @Bean
    @Qualifier("weather")
    public ModelMapper weatherModelMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        TypeMap<NewForecastDto, WeatherEntity> propertyMapper =
                modelMapper.createTypeMap(NewForecastDto.class, WeatherEntity.class);
        propertyMapper.addMapping(NewForecastDto::getDate, WeatherEntity::setDate);
        modelMapper.addConverter(weatherDtoConverter());
        return modelMapper;
    }
}
