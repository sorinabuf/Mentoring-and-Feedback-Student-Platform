package poli.meets.coreservice.domain;


import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import java.util.HashSet;
import java.util.Set;

/**
 * A Faculty.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "faculty")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facultySequenceGenerator")
    @SequenceGenerator(
        name = "facultySequenceGenerator",
        sequenceName = "faculty_sequence_generator",
        allocationSize = 1
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "domain")
    private String domain;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "faculty")
    private Set<UniversityYear> universityYears = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here


    public Faculty description(String description) {
        this.description = description;
        return this;
    }

    public Faculty universityYears(Set<UniversityYear> universityYears) {
        this.universityYears = universityYears;
        return this;
    }

    public Faculty addUniversityYears(UniversityYear universityYear) {
        this.universityYears.add(universityYear);
        universityYear.setFaculty(this);
        return this;
    }

    public Faculty removeUniversityYears(UniversityYear universityYear) {
        this.universityYears.remove(universityYear);
        universityYear.setFaculty(null);
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
