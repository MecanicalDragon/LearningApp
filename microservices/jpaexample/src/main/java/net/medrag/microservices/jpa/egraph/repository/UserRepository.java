package net.medrag.microservices.jpa.egraph.repository;

import net.medrag.microservices.jpa.egraph.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Stanislav Tretyakov
 * 19.03.2022
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph("user-eg")
    @Query("from User p where p.id = :id")
    User getByIdWithGraph(Long id);
}
