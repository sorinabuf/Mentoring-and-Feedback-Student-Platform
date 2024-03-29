package poli.meets.coreservice.domain;


import javax.persistence.*;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * A Teacher.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacherSequenceGenerator")
    @SequenceGenerator(
        name = "teacherSequenceGenerator",
        sequenceName = "teacher_sequence_generator",
        allocationSize = 1
    )
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "teacher")
    private Set<UniversityClass> universityClasses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Teacher firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Teacher lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Teacher universityClasses(Set<UniversityClass> universityClasses) {
        this.universityClasses = universityClasses;
        return this;
    }

    public Teacher addUniversityClasses(UniversityClass universityClass) {
        this.universityClasses.add(universityClass);
        universityClass.setTeacher(this);
        return this;
    }

    public Teacher removeUniversityClasses(UniversityClass universityClass) {
        this.universityClasses.remove(universityClass);
        universityClass.setTeacher(null);
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
