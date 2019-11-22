package net.medrag.old_database.repository;

import net.medrag.old_database.model.OldSkill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * {@author} Stanislav Tretyakov
 * 22.11.2019
 */
@Repository
public interface OldSkillRepo extends CrudRepository<OldSkill, Long> {
}
