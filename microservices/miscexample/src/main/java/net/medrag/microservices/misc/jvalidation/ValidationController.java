package net.medrag.microservices.misc.jvalidation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Stanislav Tretyakov
 * 24.07.2022
 */
@Slf4j
@RestController
@RequestMapping("/jvalidation")
@RequiredArgsConstructor
public class ValidationController {

    @PostMapping
    public ResponseEntity<Void> post(@Valid @RequestBody ValidationRecord record) {
        log.info("" + record);
        return ResponseEntity.noContent().build();
    }
}
