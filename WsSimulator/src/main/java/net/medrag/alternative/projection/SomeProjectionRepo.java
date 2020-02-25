package net.medrag.alternative.projection;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * {@author} Stanislav Tretyakov
 * 25.02.2020
 */
public interface SomeProjectionRepo{
//     extends CrudRepository<SomeProjectionOwnerClass, Long>
    Set<SomeProjectionRepo> findDistinctByValueOneNotNull();
}
