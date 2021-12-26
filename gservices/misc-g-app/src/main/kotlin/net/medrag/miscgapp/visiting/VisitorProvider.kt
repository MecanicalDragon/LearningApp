package net.medrag.miscgapp.visiting

import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

/**
 * Component, that provides visitors[Visitor] for [VisitorMeter] with samo random interval.
 * @author Stanislav Tretyakov
 *
 * @property provide sign, that permits providing.
 * @property names array of names for visitors[Visitor].
 * 17.12.2021
 */
@Service
class VisitorProvider(val visitorMeter: VisitorMeter) {

    private val provide = AtomicBoolean(true)

    private val names = arrayOf(
        "Viktor", "Stan", "Olga", "Ilya", "Elena", "John", "Peter", "Dmitriy", "Anon", "Dale", "Ralph", "Scott", "Ben",
        "Charlie", "Angela", "Kane", "Max", "Donnie", "Neo", "Winston", "James", "Alan", "Freddie", "Mike", "Leo", "Li"
    )

    @PostConstruct
    fun init() {
        Thread {
            while (provide.get()) {
                TimeUnit.MILLISECONDS.sleep((Math.random() * 5000).toLong())
                visitorMeter.visit(Visitor(names.random(), (Math.random() * 100).toInt()))
            }
        }.apply {
            isDaemon = true
            start()
        }
    }

    @PreDestroy
    fun destroy() {
        provide.set(false)
    }
}
