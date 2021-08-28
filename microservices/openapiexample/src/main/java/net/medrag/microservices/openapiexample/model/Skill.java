package net.medrag.microservices.openapiexample.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 04.03.2021
 */
@Entity
@Data
public class Skill {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private SkillType skillType;
    private int duration;
    private int power;
    @ManyToMany(mappedBy = "skills")
    private List<Unit> units;

    public Skill() {
    }

    public Skill(net.medrag.microservices.openapiexample.api.model.Skill skill) {
        this.name = skill.getName();
        this.duration = skill.getDuration();
        this.power = skill.getPower();
        this.skillType = SkillType.valueOf(skill.getSkillType().name());
    }

    public net.medrag.microservices.openapiexample.api.model.Skill mapToApiSkill() {
        return new net.medrag.microservices.openapiexample.api.model.Skill()
                .name(this.name)
                .duration(this.duration)
                .power(this.power)
                .skillType(net.medrag.microservices.openapiexample.api.model.SkillType.valueOf(this.skillType.name()));
    }
}
