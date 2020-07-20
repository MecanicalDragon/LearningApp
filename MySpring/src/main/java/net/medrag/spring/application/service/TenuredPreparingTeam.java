package net.medrag.spring.application.service;

import net.medrag.spring.application.domain.Rocket;
import net.medrag.spring.application.service.api.AnnouncementTeam;
import net.medrag.spring.application.service.api.PreparingTeam;
import net.medrag.spring.infrastructure.annotations.AutoInject;
import net.medrag.spring.infrastructure.annotations.Bean;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
@Bean(id = "baikonur")
public class TenuredPreparingTeam implements PreparingTeam {

    @AutoInject(beanId = "pr")
    private AnnouncementTeam announcementTeam;

    @Override
    public void prepareTheRocket(Rocket rocket) {
        rocket.setReadyForLaunch(true);
        announcementTeam.announce(String.format("Rocket model %s is ready for launching now.", rocket.getModel()));
    }
}
