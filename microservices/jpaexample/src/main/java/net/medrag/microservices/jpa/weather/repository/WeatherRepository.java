package net.medrag.microservices.jpa.weather.repository;

import net.medrag.microservices.jpa.weather.model.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WeatherRepository extends JpaRepository<WeatherEntity, Integer>, JpaSpecificationExecutor<WeatherEntity> {
}
