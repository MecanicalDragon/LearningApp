package net.medrag.ktapp.config

import net.medrag.ktapp.monitoring.MonitoringConfig
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("ktapp.monitoring.incoming-requests")
data class IncomingHttpRequestsMonitoringProperties(
    val config: MonitoringConfig
)