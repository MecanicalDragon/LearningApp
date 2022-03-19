package net.medrag.microservices.jpa.spec.repositry;

import net.medrag.microservices.jpa.spec.dto.SpecSearchParams;
import net.medrag.microservices.jpa.spec.entity.SpecEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Objects;
import java.util.stream.Stream;

public class SpecEntitySpecification implements Specification<SpecEntity> {

    private static final String ID = "id";
    private static final String BIRTH_DATE = "birthDate";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PHONE_NUMBER = "phoneNumber";

    private final SpecSearchParams searchParams;

    public SpecEntitySpecification(SpecSearchParams searchParams) {
        this.searchParams = searchParams;
    }

    @Override
    public Predicate toPredicate(Root<SpecEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(Stream.of(
                searchParams.getId().isEmpty() ? null : criteriaBuilder.or(
                        searchParams.getId().stream()
                                .map(it -> criteriaBuilder.equal(root.get(ID), it)).toArray(Predicate[]::new)
                ),
                searchParams.getPhoneNumber().isEmpty() ? null : criteriaBuilder.or(
                        searchParams.getPhoneNumber().stream()
                                .map(it -> criteriaBuilder.equal(root.get(PHONE_NUMBER), it)).toArray(Predicate[]::new)
                ),
                searchParams.getFirstName().isEmpty() ? null : criteriaBuilder.or(
                        searchParams.getFirstName().stream()
                                .map(it -> criteriaBuilder.equal(root.get(FIRST_NAME), it)).toArray(Predicate[]::new)
                ),
                searchParams.getLastName().isEmpty() ? null : criteriaBuilder.or(
                        searchParams.getLastName().stream()
                                .map(it -> criteriaBuilder.equal(root.get(LAST_NAME), it)).toArray(Predicate[]::new)
                ),
                searchParams.getDate().isEmpty() ? null : criteriaBuilder.or(
                        searchParams.getDate().stream()
                                .map(it -> criteriaBuilder.equal(root.get(BIRTH_DATE), it)).toArray(Predicate[]::new)
                )
        ).filter(Objects::nonNull).toArray(Predicate[]::new));
    }
}
