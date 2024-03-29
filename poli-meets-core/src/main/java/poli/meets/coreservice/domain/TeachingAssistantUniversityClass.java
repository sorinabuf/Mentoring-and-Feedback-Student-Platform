package poli.meets.coreservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * A TeachingAssistantUniversityClass.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teaching_assistant_class")
public class TeachingAssistantUniversityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teachingAssistantUniversityClassSequenceGenerator")
    @SequenceGenerator(
        name = "teachingAssistantUniversityClassSequenceGenerator",
        sequenceName = "teaching_assistant_university_class_sequence_generator",
        allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "universityClasses", allowSetters = true)
    private TeachingAssistant teachingAssistant;

    @ManyToOne
    @JsonIgnoreProperties(value = "teachingAssistants", allowSetters = true)
    private UniversityClass universityClass;

    // jhipster-needle-entity-add-field - JHipster will add fields here


    public TeachingAssistantUniversityClass teachingAssistant(TeachingAssistant teachingAssistant) {
        this.teachingAssistant = teachingAssistant;
        return this;
    }

    public TeachingAssistantUniversityClass universityClass(UniversityClass universityClass) {
        this.universityClass = universityClass;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
