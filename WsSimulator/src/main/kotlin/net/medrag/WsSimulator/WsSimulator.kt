package net.medrag.WsSimulator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.ConfigurableApplicationContext

@SpringBootApplication
class WsSimulator : SpringBootServletInitializer() {

    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
        return application.sources(WsSimulator::class.java)
    }
}

lateinit var ctx: ConfigurableApplicationContext

fun main(args: Array<String>) {
    ctx = runApplication<WsSimulator>(*args)
}

fun shutDown() {
    ctx.close()
}
