package net.medrag.old_database.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * {@author} Stanislav Tretyakov
 * 22.11.2019
 */
@Getter
@Setter
@Entity
@Table(name = "old_skill")
public class OldSkill {

    @Id
    @SequenceGenerator(name = "skill_id_generator", sequenceName = "skill_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "skill_id_generator")
    private Long id;

    @Column(name = "skill_name")
    private String skillName;

    @Enumerated(EnumType.STRING)
    @Column(name = "skill_level")
    private OldSkillLevel level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_owner_id")
    private OldDeveloper developer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OldSkill)) return false;

        OldSkill oldSkill = (OldSkill) o;

        if (!id.equals(oldSkill.id)) return false;
        if (!skillName.equals(oldSkill.skillName)) return false;
        return level == oldSkill.level;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + skillName.hashCode();
        result = 31 * result + level.hashCode();
        return result;
    }
}
