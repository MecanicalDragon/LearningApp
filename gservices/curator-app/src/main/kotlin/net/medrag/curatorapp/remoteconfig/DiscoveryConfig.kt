package net.medrag.curatorapp.remoteconfig

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * @author Stanislav Tretyakov
 * 09.12.2021
 */
@Configuration
@EnableConfigurationProperties(RemoteProps::class)
class DiscoveryConfig

@ConstructorBinding
@ConfigurationProperties("remote")
data class RemoteProps(
    val prop1: String,
    val list: List<String>,
    val animals: List<Animal>
)

data class Animal(
    val species: String,
    val name: String
)
