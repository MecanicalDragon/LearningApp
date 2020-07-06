package net.medrag.spring.application.service;

import net.medrag.spring.application.domain.Rocket;
import net.medrag.spring.application.service.api.AnnouncementTeam;
import net.medrag.spring.application.service.api.LaunchingTeam;
import net.medrag.spring.infrastructure.annotations.AutoInject;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
public class StockLaunchingTeam implements LaunchingTeam {

    @AutoInject(beanId = "pr")
    private AnnouncementTeam announcementTeam;

    @Override
    public void launch(Rocket rocket) {
        announcementTeam.announce(String.format("Rocket model %s has been launched successfully!", rocket.getModel()));
    }
}
