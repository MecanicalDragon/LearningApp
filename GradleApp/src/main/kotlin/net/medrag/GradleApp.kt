package net.medrag

import org.slf4j.LoggerFactory


/**
 * @author Stanislav Tretyakov
 * 26.03.2020
 */
val log = LoggerFactory.getLogger("net.medrag")

fun main(args: Array<String>) {
    log.info("We've just built a simple gradle application with logging on Kotlin, that could be packed into jar with dependencies.")
    if (args.isNotEmpty()) {
        log.info("These are command line arguments:")
        args.forEach { log.info(it) }
    }
}