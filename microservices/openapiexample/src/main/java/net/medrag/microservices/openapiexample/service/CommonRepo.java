package net.medrag.microservices.openapiexample.service;

import net.medrag.microservices.openapiexample.api.model.Skill;
import net.medrag.microservices.openapiexample.api.model.SkillType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Stanislav Tretyakov
 * 23.12.2020
 */
@Service
public class CommonRepo {

    private final Map<String, Skill> skillRepo = new ConcurrentHashMap<>();

    public void removeSkill(String name){
        skillRepo.remove(name.toLowerCase(), null);
    }

    public void addSkill(Skill skill){
        skillRepo.put(skill.getName().toLowerCase(), skill);
    }

    public Skill getSkillByName(String skillName) {
        return skillRepo.get(skillName.toLowerCase());
    }

    public List<Skill> getAll() {
        return new ArrayList<>(skillRepo.values());
    }

    @PostConstruct
    private void initRepo() {
        skillRepo.put("punch", new Skill().name("Punch").power(5).duration(0).skillType(SkillType.STRIKE));
        skillRepo.put("curse", new Skill().name("Curse").power(2).duration(3).skillType(SkillType.PERSISTENT));
        skillRepo.put("heal", new Skill().name("Heal").power(3).duration(0).skillType(SkillType.STRIKE));
        skillRepo.put("bliss", new Skill().name("Bliss").power(2).duration(3).skillType(SkillType.PERSISTENT));
    }
}
