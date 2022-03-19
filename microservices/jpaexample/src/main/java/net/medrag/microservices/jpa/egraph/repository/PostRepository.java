package net.medrag.microservices.jpa.egraph.repository;

import net.medrag.microservices.jpa.egraph.entity.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 19.03.2022
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph("post-eg-with-multiple-bag-exception")
    @Query("from Post p where p.id = :id")
    Post getByIdWithGraphAndBagException(Long id);

    @EntityGraph("post-eg-extended")
    @Query("from Post p where p.id = :id")
    Post getByIdWithGraph(Long id);

    @EntityGraph("post-eg-comments")
    @Query("from Post p where p.id = :id")
    Post getCommentsByIdWithGraph(Long id);

    @EntityGraph("post-eg-extended")
    @Query("from Post p")
    List<Post> getAllWithGraph();

    @EntityGraph("post-eg-comments")
    @Query("from Post p where p in :posts")
    List<Post> getCommentsForAllWithGraph(List<Post> posts);
}
