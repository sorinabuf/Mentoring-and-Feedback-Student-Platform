package poli.meets.coreservice.domain;


import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import java.util.HashSet;
import java.util.Set;

/**
 * A TeachingAssistant.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teaching_assistant")
public class TeachingAssistant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teachingAssistantSequenceGenerator")
    @SequenceGenerator(
        name = "teachingAssistantSequenceGenerator",
        sequenceName = "teaching_assistant_sequence_generator",
        allocationSize = 1
    )
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "teachingAssistant")
    private Set<TeachingAssistantUniversityClass> universityClasses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here


    public TeachingAssistant firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public TeachingAssistant lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public TeachingAssistant universityClasses(Set<TeachingAssistantUniversityClass> teachingAssistantUniversityClasses) {
        this.universityClasses = teachingAssistantUniversityClasses;
        return this;
    }

    public TeachingAssistant addUniversityClasses(TeachingAssistantUniversityClass teachingAssistantUniversityClass) {
        this.universityClasses.add(teachingAssistantUniversityClass);
        teachingAssistantUniversityClass.setTeachingAssistant(this);
        return this;
    }

    public TeachingAssistant removeUniversityClasses(TeachingAssistantUniversityClass teachingAssistantUniversityClass) {
        this.universityClasses.remove(teachingAssistantUniversityClass);
        teachingAssistantUniversityClass.setTeachingAssistant(null);
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
