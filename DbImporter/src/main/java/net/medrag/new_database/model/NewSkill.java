package net.medrag.new_database.model;

import lombok.*;

import javax.persistence.*;

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
@Table(name = "new_skill")
public class NewSkill {

    @Id
    @SequenceGenerator(name = "skill_id_generator", sequenceName = "skill_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "skill_id_generator")
    private Long id;

    @Column(name = "new_name")
    private String skillName;

    @Enumerated(EnumType.STRING)
    @Column(name = "sk_level")
    private NewSkillLevel level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_developer_id")
    private NewDeveloper developer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewSkill)) return false;

        NewSkill newSkill = (NewSkill) o;

        if (id != null ? !id.equals(newSkill.id) : newSkill.id != null) return false;
        if (skillName != null ? !skillName.equals(newSkill.skillName) : newSkill.skillName != null) return false;
        return level == newSkill.level;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (skillName != null ? skillName.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        return result;
    }
}
