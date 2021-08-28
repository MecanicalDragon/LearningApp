package net.medrag.microservices.openapiexample.repository;

import net.medrag.microservices.openapiexample.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 04.03.2021
 */
public interface SkillRepo extends JpaRepository<Skill, Long> {
    @Query("delete from Skill s where s.name = ?1")
    int deleteByName(String name);

    @Query("select s from Skill s where s.name = ?1")
    Skill getByName(String name);

    List<Skill> getAllByNameIn(List<String> names);
}
