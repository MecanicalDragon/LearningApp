package net.medrag.curatorapp.curator

import org.apache.curator.RetryPolicy
import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.CuratorFrameworkFactory
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.curator.x.async.AsyncCuratorFramework
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

/**
 * @author Stanislav Tretyakov
 * 14.11.2021
 */
@Configuration
@EnableConfigurationProperties(CuratorProperties::class)
class Config {

    @Autowired
    private lateinit var curatorProperties: CuratorProperties

    @Bean
    fun retryPolicy(): RetryPolicy = ExponentialBackoffRetry(
        curatorProperties.timeLag, curatorProperties.retries
    )

    @Bean
    @Primary // required because zookeeper-config added
    fun curator(): CuratorFramework =
        CuratorFrameworkFactory.newClient(curatorProperties.zookeeperHost, retryPolicy()).apply {
            start()
        }

    @Bean
    fun aCurator(): AsyncCuratorFramework = AsyncCuratorFramework.wrap(curator())
}

@ConstructorBinding
@ConfigurationProperties("curator")
data class CuratorProperties(
    val retries: Int,
    val timeLag: Int,
    val zookeeperHost: String
)
