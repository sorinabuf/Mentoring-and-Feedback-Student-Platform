package poli.meets.coreservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import java.util.HashSet;
import java.util.Set;

/**
 * A UniversityClass.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "university_class")
public class UniversityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "universityClassSequenceGenerator")
    @SequenceGenerator(
        name = "universityClassSequenceGenerator",
        sequenceName = "university_class_sequence_generator",
        allocationSize = 1
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "abbreviation")
    private String abbreviation;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "universityClass")
    private Set<TeachingAssistantUniversityClass> teachingAssistants = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "universityClasses", allowSetters = true)
    private Teacher teacher;

    @ManyToOne
    @JsonIgnoreProperties(value = "universityClasses", allowSetters = true)
    private UniversityYear universityYear;

    // jhipster-needle-entity-add-field - JHipster will add fields here


    public UniversityClass abbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
        return this;
    }

    public UniversityClass description(String description) {
        this.description = description;
        return this;
    }

    public UniversityClass teachingAssistants(Set<TeachingAssistantUniversityClass> teachingAssistantUniversityClasses) {
        this.teachingAssistants = teachingAssistantUniversityClasses;
        return this;
    }

    public UniversityClass addTeachingAssistants(TeachingAssistantUniversityClass teachingAssistantUniversityClass) {
        this.teachingAssistants.add(teachingAssistantUniversityClass);
        teachingAssistantUniversityClass.setUniversityClass(this);
        return this;
    }

    public UniversityClass removeTeachingAssistants(TeachingAssistantUniversityClass teachingAssistantUniversityClass) {
        this.teachingAssistants.remove(teachingAssistantUniversityClass);
        teachingAssistantUniversityClass.setUniversityClass(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
