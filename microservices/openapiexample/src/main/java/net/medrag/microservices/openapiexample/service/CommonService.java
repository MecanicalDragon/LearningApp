package net.medrag.microservices.openapiexample.service;

import lombok.RequiredArgsConstructor;
import net.medrag.microservices.openapiexample.api.model.Skill;
import net.medrag.microservices.openapiexample.api.model.Unit;
import net.medrag.microservices.openapiexample.api.model.UnitRequest;
import net.medrag.microservices.openapiexample.model.ClassType;
import net.medrag.microservices.openapiexample.repository.SkillRepo;
import net.medrag.microservices.openapiexample.repository.UnitRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Stanislav Tretyakov
 * 23.12.2020
 */
@Service
@RequiredArgsConstructor
public class CommonService {

    private final SkillRepo skillRepo;
    private final UnitRepo unitRepo;

    public void addSkill(Skill skill) {
        skillRepo.save(new net.medrag.microservices.openapiexample.model.Skill(skill));
    }

    public void removeSkill(String name) {
        skillRepo.deleteByName(name);
    }

    public Skill getSkillByName(String skillName) {
        return skillRepo.getByName(skillName).mapToApiSkill();
    }

    public List<Skill> getAll() {
        return skillRepo.findAll().stream().map(net.medrag.microservices.openapiexample.model.Skill::mapToApiSkill)
                .collect(Collectors.toList());
    }

    public void addUnit(Unit unit) {
        unitRepo.save(new net.medrag.microservices.openapiexample.model.Unit(unit));
    }

    public void removeUnitByName(String unitName) {
        unitRepo.deleteByName(unitName);
    }

    public Unit getUnitByName(String unitName) {
        return unitRepo.getByName(unitName).mapToApiUnit();
    }

    public List<Unit> getAllUnits() {
        return unitRepo.findAll().stream().map(net.medrag.microservices.openapiexample.model.Unit::mapToApiUnit)
                .collect(Collectors.toList());
    }

    public List<Unit> getAllBySkillName(String unitClass, String skillName) {
        var skills = skillRepo.getAllByNameIn(List.of(skillName));
        return unitRepo.getAllByClassTypeAndSkillsIn(ClassType.valueOf(unitClass), skills).stream()
                .map(net.medrag.microservices.openapiexample.model.Unit::mapToApiUnit).collect(Collectors.toList());
    }

    public List<Unit> getUnitsWithSkills(UnitRequest unitRequest) {
        var skills = skillRepo.getAllByNameIn(unitRequest.getRequiredSkills());
        return unitRepo.getAllByClassTypeAndSkillsIn(
                        ClassType.valueOf(unitRequest.getRequiredClassName().getValue()),
                        skills).stream()
                .map(net.medrag.microservices.openapiexample.model.Unit::mapToApiUnit).collect(Collectors.toList());
    }
}
