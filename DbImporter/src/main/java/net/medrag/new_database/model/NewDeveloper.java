package net.medrag.new_database.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * {@author} Stanislav Tretyakov
 * 22.11.2019
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "new_developer")
public class NewDeveloper {

    @Id
    @SequenceGenerator(name = "developer_id_generator", sequenceName = "developer_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "developer_id_generator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "developer", fetch = FetchType.EAGER)  //  cascade type = none
    private Set<NewSkill> skills;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewDeveloper)) return false;

        NewDeveloper that = (NewDeveloper) o;

        if (age != that.age) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        return skills != null ? skills.equals(that.skills) : that.skills == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (skills != null ? skills.hashCode() : 0);
        return result;
    }
}
