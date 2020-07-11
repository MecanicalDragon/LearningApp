package net.medrag.spring.application.service;

import net.medrag.spring.application.domain.Plot;
import net.medrag.spring.application.service.api.AnnouncementTeam;
import net.medrag.spring.application.service.api.DevelopmentTeam;
import net.medrag.spring.infrastructure.annotations.AutoInject;
import net.medrag.spring.infrastructure.annotations.PropertyValue;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
public class TenuredDevelopmentTeam implements DevelopmentTeam {

    @PropertyValue("net.medrag.spring.application.mars.weight")
    private Integer weight;
    @PropertyValue("net.medrag.spring.application.mars.height")
    private Integer height;
    @PropertyValue("net.medrag.spring.application.mars.power")
    private Integer power;
    @PropertyValue("net.medrag.spring.application.mars.model")
    private String model;

    @AutoInject(beanId = "pr")
    private AnnouncementTeam announcementTeam;

    @Override
    public Plot developTheRocket() {

        String announce = String.format("Rocket model %s has been developed!\n  Announced characteristics:\n" +
                "        Weight: %s\n        Height: %s\n        Power: %s", model, weight, height, power);

        announcementTeam.announce(announce);

        Plot plot = new Plot();

        plot.setHeight(height);
        plot.setWeight(weight);
        plot.setPower(power);
        plot.setModel(model);

        return plot;
    }
}
