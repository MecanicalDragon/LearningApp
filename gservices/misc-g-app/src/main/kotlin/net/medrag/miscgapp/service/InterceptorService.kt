package net.medrag.miscgapp.service

import org.springframework.stereotype.Service

/**
 * @author Stanislav Tretyakov
 * 09.12.2021
 */
@Service
class InterceptorService(
    private val inverterService: InverterService
) {

    fun callInnerServiceWithData(data: SomeData): SomeData {
        val invertedData = inverterService.invertData(data)
        println("Data have been inverted: <$invertedData>.")
        return invertedData
    }
}
