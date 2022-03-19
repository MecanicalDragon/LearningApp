package net.medrag.microservices.jpa.egraph.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Stanislav Tretyakov
 * 19.03.2022
 */
@NamedEntityGraph(
        name = "post-eg",
        attributeNodes = {
                @NamedAttributeNode("subject"),
                @NamedAttributeNode("user"),
                @NamedAttributeNode("comments"),
        }
)
@NamedEntityGraph(
        name = "post-eg-comments",
        attributeNodes = {
                @NamedAttributeNode(value = "comments", subgraph = "comments-subgraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "comments-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("user")
                        }
                )
        }
)
@NamedEntityGraph(
        name = "post-eg-extended",
        attributeNodes = {
                @NamedAttributeNode("subject"),
                @NamedAttributeNode("user"),
                @NamedAttributeNode("attachments")
        }
)
@NamedEntityGraph(
        name = "post-eg-with-multiple-bag-exception",
        attributeNodes = {
                @NamedAttributeNode("subject"),
                @NamedAttributeNode("user"),
                @NamedAttributeNode("attachments"),
                @NamedAttributeNode(value = "comments", subgraph = "comments-subgraph"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "comments-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("user")
                        }
                )
        }
)
@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "ATTACHMENT", joinColumns = @JoinColumn(name = "post_id"))
    private List<Attachment> attachments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return id != null && Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
