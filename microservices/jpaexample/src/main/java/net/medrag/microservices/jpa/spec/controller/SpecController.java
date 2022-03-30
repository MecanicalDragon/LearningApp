package net.medrag.microservices.jpa.spec.controller;

import net.medrag.microservices.jpa.spec.dto.SpecUserDto;
import net.medrag.microservices.jpa.spec.dto.SpecUserSearchParams;
import net.medrag.microservices.jpa.spec.service.SpecUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/spec-user")
public class SpecController {

    public static final String INSERT_ENDPOINT = "/insert";

    private final SpecUserService specUserService;

    public SpecController(SpecUserService specUserService) {
        this.specUserService = specUserService;
    }

    @PostMapping(INSERT_ENDPOINT)
    public ResponseEntity<SpecUserDto> insert(
            @RequestBody SpecUserDto specUserDto,
            HttpServletRequest httpServletRequest
    ) {
        final StringBuffer requestURL = httpServletRequest.getRequestURL();
        final URI location = URI.create(requestURL.replace(
                        requestURL.lastIndexOf(INSERT_ENDPOINT),
                        requestURL.length(),
                        "/select/" + specUserDto.getId())
                .toString()
        );
        final SpecUserDto specUserDto1 = specUserService.addOne(specUserDto);
        return ResponseEntity.created(location).body(specUserDto1);
    }

    @GetMapping("/select")
    public ResponseEntity<Page<SpecUserDto>> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String dir,
            SpecUserSearchParams specUserSearchParams
    ) {
        final PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(dir), sort));
        final Page<SpecUserDto> result = specUserService.getPage(specUserSearchParams, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/select/{id}")
    public ResponseEntity<SpecUserDto> getAll(@PathVariable Long id) {
        final Optional<SpecUserDto> extract = specUserService.extract(id);
        return extract.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        specUserService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
