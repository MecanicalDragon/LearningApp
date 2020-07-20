package net.medrag.spring.application.service;

import net.medrag.spring.application.service.api.AnnouncementTeam;
import net.medrag.spring.infrastructure.annotations.Bean;
import net.medrag.spring.infrastructure.annotations.InitMethod;

/**
 * @author Stanislav Tretyakov
 * 03.07.2020
 */
@Bean(id = "pr")
public class TenuredAnnouncementTeam implements AnnouncementTeam {
    @Override
    public void announce(String announce) {
        System.out.println(announce);
    }

    @InitMethod
    public void init() {
        System.out.println("Announcement team has been employed.");
    }
}
