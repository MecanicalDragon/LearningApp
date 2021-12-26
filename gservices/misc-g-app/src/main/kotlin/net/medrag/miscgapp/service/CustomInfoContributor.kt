package net.medrag.miscgapp.service

import org.springframework.boot.actuate.info.Info
import org.springframework.boot.actuate.info.InfoContributor
import org.springframework.stereotype.Component

/**
 * Component, that adds additional info to actuator's `/info` endpoint.
 *
 * @author Stanislav Tretyakov
 * 16.12.2021
 */
@Component
class CustomInfoContributor : InfoContributor {
    override fun contribute(builder: Info.Builder?) {
        builder?.withDetail("Java data", "This value comes directly from java app!")
    }
}
