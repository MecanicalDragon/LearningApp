package net.medrag.microservices.jpa.egraph.dto;

import lombok.Data;

/**
 * @author Stanislav Tretyakov
 * 19.03.2022
 */
@Data
public class SubscriptionDto {
    private Long id;
    private String topic;
}
