package net.medrag.microservices.openapiexample.controller;

import lombok.RequiredArgsConstructor;
import net.medrag.microservices.openapiexample.api.SkillApi;
import net.medrag.microservices.openapiexample.api.model.Skill;
import net.medrag.microservices.openapiexample.service.CommonRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 19.12.2020
 */
@RestController
@RequiredArgsConstructor
public class SkillController implements SkillApi {

    private final CommonRepo commonRepo;

    @Override
    public ResponseEntity<Void> addSkill(@Valid Skill body) {
        commonRepo.addSkill(body);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteSkill(@Size(min = 1, max = 64) String skillName) {
        commonRepo.removeSkill(skillName);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Skill> getSkill(@Size(min = 1, max = 64) String skillName) {
        return ResponseEntity.ok(commonRepo.getSkillByName(skillName));
    }

    @Override
    public ResponseEntity<List<Skill>> getSkills() {
        return ResponseEntity.ok(commonRepo.getAll());
    }
}
