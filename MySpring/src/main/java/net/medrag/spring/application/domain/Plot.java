package net.medrag.spring.application.domain;

import lombok.Data;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
@Data
public class Plot {
    private Integer weight;
    private Integer height;
    private Integer power;
    private String model;
}
