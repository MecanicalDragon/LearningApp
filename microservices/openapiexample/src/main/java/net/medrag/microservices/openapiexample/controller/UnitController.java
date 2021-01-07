package net.medrag.microservices.openapiexample.controller;

import net.medrag.microservices.openapiexample.api.UnitApi;
import net.medrag.microservices.openapiexample.api.model.Unit;
import net.medrag.microservices.openapiexample.api.model.UnitRequest;
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
public class UnitController implements UnitApi {
    @Override
    public ResponseEntity<Void> addSkillToUnit(@Size(min = 1, max = 64) String skillName) {
        return null;
    }

    @Override
    public ResponseEntity<Void> addUnit(@Valid Unit body) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteUnit(@Size(min = 1, max = 64) String unitName) {
        return null;
    }

    @Override
    public ResponseEntity<Unit> getUnit(@Size(min = 1, max = 64) String unitName) {
        return null;
    }

    @Override
    public ResponseEntity<List<Unit>> getUnits() {
        return null;
    }

    @Override
    public ResponseEntity<List<Unit>> getUnitsWithSkills(@Size(min = 1, max = 64) @Valid String unitClass, @Size(min = 1, max = 64) @Valid String skillName) {
        return null;
    }

    @Override
    public ResponseEntity<List<Unit>> queryUnitsWithSkills(@Valid UnitRequest unitRequest) {
        return null;
    }
}
