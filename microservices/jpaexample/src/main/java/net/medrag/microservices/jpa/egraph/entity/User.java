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
        name = "user-eg",
        attributeNodes = {
                @NamedAttributeNode(value = "name"),
                @NamedAttributeNode(value = "email"),
                @NamedAttributeNode(value = "subscriptions", subgraph = "subscriptions-subgraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "subscriptions-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("topic")
                        }
                )
        }
)
@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @ManyToMany
    @JoinTable(name = "user_subscription",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_id"))
    private List<Subscription> subscriptions = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
