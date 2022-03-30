package net.medrag.microservices.jpa.weather.service;

import net.medrag.microservices.jpa.weather.model.WeatherSearchParams;
import net.medrag.microservices.jpa.weather.model.entity.WeatherEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Stanislav Tretyakov
 * 30.03.2022
 */
@RequiredArgsConstructor
public class WeatherSpecification implements Specification<WeatherEntity> {

    private final WeatherSearchParams searchParams;

    private static final String ID = "id";
    private static final String DATE = "date";
    private static final String CITY = "city";

    @Override
    public Predicate toPredicate(Root<WeatherEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        defineOrder(root, criteriaQuery, criteriaBuilder);

        return criteriaBuilder.and(Stream.of(
                searchParams.getCity().isEmpty() ? null : criteriaBuilder.or(
                        searchParams.getCity().stream()
                                .map(it -> criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get(CITY)), it.toLowerCase(Locale.ROOT)
                                )).toArray(Predicate[]::new)
                ),
                searchParams.getDate() == null ? criteriaBuilder.isNotNull(root.get(DATE))
                        : criteriaBuilder.equal(root.get(DATE), searchParams.getDate())
        ).filter(Objects::nonNull).toArray(Predicate[]::new));
    }

    private void defineOrder(Root<WeatherEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (searchParams.getSort() != null) {
            switch (searchParams.getSort()) {
                case ASC:
                    query.orderBy(builder.asc(root.get(DATE)), builder.asc(root.get(ID)));
                    break;
                case DESC:
                    query.orderBy(builder.desc(root.get(DATE)), builder.asc(root.get(ID)));
                    break;
            }
        } else {
            query.orderBy(builder.asc(root.get(ID)));
        }
    }
}