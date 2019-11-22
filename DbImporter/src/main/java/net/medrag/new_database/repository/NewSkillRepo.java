package net.medrag.new_database.repository;

import net.medrag.new_database.model.NewSkill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * {@author} Stanislav Tretyakov
 * 22.11.2019
 */
@Repository
public interface NewSkillRepo extends CrudRepository<NewSkill, Long> {
}
