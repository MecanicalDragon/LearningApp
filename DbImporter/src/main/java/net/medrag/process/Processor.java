package net.medrag.process;

import lombok.extern.slf4j.Slf4j;
import net.medrag.new_database.model.NewDeveloper;
import net.medrag.new_database.model.NewSkill;
import net.medrag.new_database.model.NewSkillLevel;
import net.medrag.new_database.repository.NewDevRepo;
import net.medrag.new_database.repository.NewSkillRepo;
import net.medrag.old_database.model.OldDeveloper;
import net.medrag.old_database.model.OldSkill;
import net.medrag.old_database.model.OldSkillLevel;
import net.medrag.old_database.repository.OldDevRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class Processor {

    private OldDevRepo oldDevRepo;
    private NewDevRepo newDevRepo;
    private NewSkillRepo newSkillRepo;

    @Autowired
    public Processor(OldDevRepo oldDevRepo, NewDevRepo newDevRepo, NewSkillRepo newSkillRepo) {
        this.oldDevRepo = oldDevRepo;
        this.newDevRepo = newDevRepo;
        this.newSkillRepo = newSkillRepo;
    }

    @Transactional
    public void migrate() {
        log.info("Starting migration process...");
        List<OldDeveloper> old = (List<OldDeveloper>) oldDevRepo.findAll();
        List<NewDeveloper> mapped = old.stream().map(this::mapDev).collect(Collectors.toList());
        long count = mapped.stream().peek(this::persist).count();
        log.info("{} developer have been saved total.", count);
    }

    private void persist(NewDeveloper dev) {
        NewDeveloper saved = newDevRepo.save(dev);
        long count = dev.getSkills().stream().peek(skill -> skill.setDeveloper(saved)).count();
        newSkillRepo.saveAll(dev.getSkills());
        log.info("{} skills have been saved for developer with id '{}'.", count, saved.getId());
    }

    private NewDeveloper mapDev(OldDeveloper old) {
        return NewDeveloper.builder().id(null).name(old.getName()).age(old.getAge())
                .surname(old.getSurname()).skills(copySkills(old.getSkills())).build();
    }

    private Set<NewSkill> copySkills(Set<OldSkill> oldSet) {
        return oldSet.stream().map(this::mapSkill).collect(Collectors.toSet());
    }

    private NewSkill mapSkill(OldSkill old) {
        return NewSkill.builder().id(null).skillName(old.getSkillName()).level(mapLevel(old.getLevel())).developer(null).build();
    }

    private NewSkillLevel mapLevel(OldSkillLevel old) {
        return NewSkillLevel.valueOf(old.name());
    }
}
