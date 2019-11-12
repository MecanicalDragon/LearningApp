package net.medrag.WsSimulator.controller

import net.medrag.WsSimulator.model.Response
import net.medrag.WsSimulator.service.JpaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import net.medrag.WsSimulator.shutDown


/**
 * {@author} Stanislav Tretyakov
 * 05.11.2019
 */
@RestController
@RequestMapping("/ws", "/")
class RController(private val service: JpaService) {

    @PostMapping("/addResp")
    fun addResp(@RequestBody resp: Response, @RequestParam(required = false) validate: Boolean) = try {
        ResponseEntity.ok(service.addResponse(resp, validate))
    } catch (e: Exception) {
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @DeleteMapping("/remove")
    fun remove(@RequestParam id: String) = ResponseEntity.ok(service.remove(id))

    @GetMapping("/getResp")
    fun getResp(@RequestParam id: String) = ResponseEntity.ok(service.getById(id))

    @GetMapping("/getReqs")
    fun getAll() = ResponseEntity.ok(service.getReqs())

    @GetMapping("/shutdown")
    fun shutdown() {
        shutDown()
    }
}