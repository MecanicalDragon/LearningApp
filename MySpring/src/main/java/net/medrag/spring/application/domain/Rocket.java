package net.medrag.spring.application.domain;

import lombok.Data;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
@Data
public class Rocket {
    private Integer weight;
    private Integer height;
    private Integer power;
    private String model;

    private boolean readyForLaunch = false;

    public Rocket(Plot plot){
        this.weight = plot.getWeight();
        this.height = plot.getHeight();
        this.power = plot.getPower();
        this.model = plot.getModel();
    }
}
