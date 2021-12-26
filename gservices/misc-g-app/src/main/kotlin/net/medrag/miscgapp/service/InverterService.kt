package net.medrag.miscgapp.service

import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import org.springframework.stereotype.Service

/**
 * Provides metrics of inversions total amount and time, spent for inversion.
 *
 * @author Stanislav Tretyakov
 * 09.12.2021
 */
@Service
class InverterService {

    /**
     * Invert incoming data.
     *
     * @param data SomeData.
     * @return SomeData - inversion result.
     */
    @Counted(value = "inversions", description = "Indicates number of inversions", extraTags = ["total", "amount"])
    @Timed(value = "inversions_time", description = "Time, spent for inversion")
    fun invertData(data: SomeData): SomeData {
        Thread.sleep((Math.random() * 300).toLong())
        println("Data to invert have been received: <$data>.")
        return SomeData(data.intValue.toString(), data.stringValue.hashCode())
    }
}
