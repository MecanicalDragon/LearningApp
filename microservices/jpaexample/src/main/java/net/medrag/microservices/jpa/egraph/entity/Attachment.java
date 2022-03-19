package net.medrag.microservices.jpa.egraph.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

/**
 * @author Stanislav Tretyakov
 * 19.03.2022
 */
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class Attachment {
    private String description;
    private String payload;
}
