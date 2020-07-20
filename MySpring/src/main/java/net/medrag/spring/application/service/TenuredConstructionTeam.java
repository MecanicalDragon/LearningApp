package net.medrag.spring.application.service;

import net.medrag.spring.application.domain.Plot;
import net.medrag.spring.application.domain.Rocket;
import net.medrag.spring.application.service.api.AnnouncementTeam;
import net.medrag.spring.application.service.api.ConstructionTeam;
import net.medrag.spring.infrastructure.annotations.AutoInject;
import net.medrag.spring.infrastructure.annotations.Bean;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
@Bean
public class TenuredConstructionTeam implements ConstructionTeam {

    @AutoInject(beanId = "pr")
    private AnnouncementTeam announcementTeam;

    @Override
    public Rocket constructRocket(Plot plot) {
        announcementTeam.announce(String.format("Rocket model %s has been constructed successfully!", plot.getModel()));
        return new Rocket(plot);
    }
}
