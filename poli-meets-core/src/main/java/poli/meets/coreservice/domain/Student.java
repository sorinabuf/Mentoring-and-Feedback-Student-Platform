package poli.meets.coreservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * A Student.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studentSequenceGenerator")
    @SequenceGenerator(
        name = "studentSequenceGenerator",
        sequenceName = "student_sequence_generator",
        allocationSize = 1
    )
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "student_email")
    private String studentEmail;

    @Column(name = "personal_email")
    private String personalEmail;

    @ManyToOne
    @JsonIgnoreProperties(value = "students", allowSetters = true)
    private UniversityYear universityYear;

    // jhipster-needle-entity-add-field - JHipster will add fields here


    public Student firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Student lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Student studentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
        return this;
    }

    public Student personalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
