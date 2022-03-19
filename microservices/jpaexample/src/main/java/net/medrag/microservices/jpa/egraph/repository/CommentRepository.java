package net.medrag.microservices.jpa.egraph.repository;

import net.medrag.microservices.jpa.egraph.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Stanislav Tretyakov
 * 19.03.2022
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
