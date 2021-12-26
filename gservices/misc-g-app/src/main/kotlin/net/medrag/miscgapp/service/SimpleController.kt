package net.medrag.miscgapp.service

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * Controller for handling http requests to invert data.
 *
 * @author Stanislav Tretyakov
 * 09.12.2021
 */
@RestController
class SimpleController(
    private val interceptorService: InterceptorService
) {

    @PostMapping("/invert")
    fun invertData(@RequestBody data: SomeData): ResponseEntity<SomeData> =
        ResponseEntity.ok(interceptorService.callInnerServiceWithData(data))
}
