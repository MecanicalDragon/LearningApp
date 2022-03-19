package net.medrag.microservices.jpa.egraph.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 19.03.2022
 */
@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private List<SubscriptionDto> subscriptions;
}
