package net.medrag.microservices.jpa.spec.controller;

import net.medrag.microservices.jpa.spec.dto.SpecDto;
import net.medrag.microservices.jpa.spec.dto.SpecSearchParams;
import net.medrag.microservices.jpa.spec.service.SpecService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/spec")
public class SpecController {

    public static final String INSERT_ENDPOINT = "/insert";

    private final SpecService specService;

    public SpecController(SpecService specService) {
        this.specService = specService;
    }

    @PostMapping(INSERT_ENDPOINT)
    public ResponseEntity<SpecDto> insert(
            @RequestBody SpecDto specDto,
            HttpServletRequest httpServletRequest
    ) {
        final StringBuffer requestURL = httpServletRequest.getRequestURL();
        final URI location = URI.create(requestURL.replace(
                        requestURL.lastIndexOf(INSERT_ENDPOINT),
                        requestURL.length(),
                        "/select/" + specDto.getId())
                .toString()
        );
        final SpecDto specDto1 = specService.addOne(specDto);
        return ResponseEntity.created(location).body(specDto1);
    }

    @GetMapping("/select")
    public ResponseEntity<Page<SpecDto>> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String dir,
            SpecSearchParams specSearchParams
    ) {
        final PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(dir), sort));
        final Page<SpecDto> result = specService.getPage(specSearchParams, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/select/{id}")
    public ResponseEntity<SpecDto> getAll(@PathVariable Long id) {
        final Optional<SpecDto> extract = specService.extract(id);
        return extract.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        specService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
