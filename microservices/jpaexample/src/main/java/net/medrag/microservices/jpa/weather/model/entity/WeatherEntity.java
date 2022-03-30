package net.medrag.microservices.jpa.weather.model.entity;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author Stanislav Tretyakov
 * 30.03.2022
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "weather")
@TypeDef(name = "list", typeClass = ListArrayType.class)
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "forecast_date")
    private LocalDate date;

    @Column(name = "latitude")
    private Float lat;
    @Column(name = "longitude")
    private Float lon;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;

    @Type(type = "list")
    @Column(name = "temperature")
    private List<Double> temperatures;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WeatherEntity that = (WeatherEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }
}
