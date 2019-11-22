package net.medrag.new_database.repository;

import net.medrag.new_database.model.NewDeveloper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * {@author} Stanislav Tretyakov
 * 22.11.2019
 */
@Repository
public interface NewDevRepo extends CrudRepository<NewDeveloper, Long> {
}
