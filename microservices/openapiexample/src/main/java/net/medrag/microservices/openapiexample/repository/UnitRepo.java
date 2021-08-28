package net.medrag.microservices.openapiexample.repository;

import net.medrag.microservices.openapiexample.model.ClassType;
import net.medrag.microservices.openapiexample.model.Skill;
import net.medrag.microservices.openapiexample.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 04.03.2021
 */
public interface UnitRepo extends JpaRepository<Unit, Long> {
    @Query("delete from Unit u where u.name = ?1")
    int deleteByName(String name);

    @Query("select u from Unit u where u.name = ?1")
    Unit getByName(String name);

    List<Unit> getAllByClassTypeAndSkillsIn(ClassType unitClass, List<Skill> skills);
}
