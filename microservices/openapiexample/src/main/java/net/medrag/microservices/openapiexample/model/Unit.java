package net.medrag.microservices.openapiexample.model;

import lombok.Data;
import net.medrag.microservices.openapiexample.api.model.ClassEnum;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Stanislav Tretyakov
 * 04.03.2021
 */
@Data
@Entity
public class Unit {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private ClassType classType;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "unit_skills",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skills;

    public Unit() {
    }

    public Unit(net.medrag.microservices.openapiexample.api.model.Unit unit) {
        this.name = unit.getName();
        this.classType = ClassType.valueOf(unit.getUnitClass().getValue());
        this.skills = unit.getSkillSet().stream().map(Skill::new).collect(Collectors.toList());
    }

    public net.medrag.microservices.openapiexample.api.model.Unit mapToApiUnit() {
        return new net.medrag.microservices.openapiexample.api.model.Unit()
                .name(this.name)
                .unitClass(ClassEnum.valueOf(this.classType.name()))
                .skillSet(this.skills.stream().map(Skill::mapToApiSkill).collect(Collectors.toList()));
    }
}
