package net.medrag.old_database.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * {@author} Stanislav Tretyakov
 * 22.11.2019
 */
@Getter
@Setter
@Entity
@Table(name = "old_developer")
public class OldDeveloper {

    @Id
    @SequenceGenerator(name = "developer_id_generator", sequenceName = "developer_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "developer_id_generator")
    private Long id;

    @Column(name="dev_name")
    private String name;

    @Column(name="dev_surname")
    private String surname;

    @Column(name="dev_age")
    private int age;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "developer", fetch = FetchType.LAZY)
    private Set<OldSkill> skills;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OldDeveloper)) return false;

        OldDeveloper that = (OldDeveloper) o;

        if (age != that.age) return false;
        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (!surname.equals(that.surname)) return false;
        return skills.equals(that.skills);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + age;
        result = 31 * result + skills.hashCode();
        return result;
    }
}
