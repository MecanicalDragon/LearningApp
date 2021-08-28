package net.medrag.microservices.openapiexample.controller;

import lombok.RequiredArgsConstructor;
import net.medrag.microservices.openapiexample.api.UnitApi;
import net.medrag.microservices.openapiexample.api.model.Unit;
import net.medrag.microservices.openapiexample.api.model.UnitRequest;
import net.medrag.microservices.openapiexample.service.CommonService;
import org.apache.commons.lang3.NotImplementedException;
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
public class UnitController implements UnitApi {

    private final CommonService commonService;

    @Override
    public ResponseEntity<Void> addSkillToUnit(@Size(min = 1, max = 64) String skillName) {
        throw new NotImplementedException();
    }

    @Override
    public ResponseEntity<Void> addUnit(@Valid Unit body) {
        commonService.addUnit(body);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteUnit(@Size(min = 1, max = 64) String unitName) {
        commonService.removeUnitByName(unitName);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Unit> getUnit(@Size(min = 1, max = 64) String unitName) {
        return ResponseEntity.ok(commonService.getUnitByName(unitName));
    }

    @Override
    public ResponseEntity<List<Unit>> getUnits() {
        return ResponseEntity.ok(commonService.getAllUnits());
    }

    @Override
    public ResponseEntity<List<Unit>> getUnitsWithSkills(@Size(min = 1, max = 64) @Valid String unitClass, @Size(min = 1, max = 64) @Valid String skillName) {
        return ResponseEntity.ok(commonService.getAllBySkillName(unitClass, skillName));
    }

    @Override
    public ResponseEntity<List<Unit>> queryUnitsWithSkills(@Valid UnitRequest unitRequest) {
        return ResponseEntity.ok(commonService.getUnitsWithSkills(unitRequest));
    }
}
