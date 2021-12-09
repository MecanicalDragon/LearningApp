package net.medrag.curatorapp.springintegration

import org.apache.curator.framework.CuratorFramework
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.zookeeper.config.LeaderInitiatorFactoryBean

/**
 * @author Stanislav Tretyakov
 * 09.12.2021
 */
@Configuration
class LeaderElectionConfig {

    @Bean
    fun leaderInitiatorFactoryBean(
        curatorFramework: CuratorFramework,
        leaderCandidate: LeaderCandidate
    ): LeaderInitiatorFactoryBean = LeaderInitiatorFactoryBean().apply {
        setClient(curatorFramework)
        setCandidate(leaderCandidate)
        setPath("/leader-path")
        isAutoStartup = true
    }
}
