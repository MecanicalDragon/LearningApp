package net.medrag.spring.application.service;

import net.medrag.spring.application.domain.Rocket;
import net.medrag.spring.application.service.api.AnnouncementTeam;
import net.medrag.spring.application.service.api.LaunchingTeam;
import net.medrag.spring.infrastructure.annotations.AutoInject;
import net.medrag.spring.infrastructure.annotations.Broadcast;
import net.medrag.spring.infrastructure.annotations.SabotageTarget;

/**
 * @author Stanislav Tretyakov
 * 01.07.2020
 */
public class TenuredLaunchingTeam implements LaunchingTeam {

    @AutoInject(beanId = "pr")
    private AnnouncementTeam announcementTeam;

    @Override
    @Broadcast
    @SabotageTarget(chance = 50.0)
    public void launch(Rocket rocket) {
        announcementTeam.announce(String.format("Rocket model %s has been launched successfully!", rocket.getModel()));
    }
}
