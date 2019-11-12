package net.medrag.WsSimulator.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


/**
 * {@author} Stanislav Tretyakov
 * 05.11.2019
 */
@Controller
class PController {

    @GetMapping("/")
    fun index() = "index.html"
}