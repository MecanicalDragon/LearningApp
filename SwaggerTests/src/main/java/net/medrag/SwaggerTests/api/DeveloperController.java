package net.medrag.SwaggerTests.api;

import net.medrag.SwaggerTests.model.Developer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * {@author} Stanislav Tretyakov
 * 11.11.2019
 */

@Controller
@RequestMapping("${openapi.swaggerTests.base-path:}")
public class DeveloperController implements DeveloperApi {

    public ResponseEntity<Developer> getById(Long id) {
        System.out.println("get developer");
        Developer d = new Developer();
        d.setName("Vasiliy");
        d.setAge(55);
        return ResponseEntity.ok(d);
    }
}
