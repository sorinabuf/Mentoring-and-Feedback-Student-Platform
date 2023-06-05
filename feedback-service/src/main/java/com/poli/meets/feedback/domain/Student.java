package com.poli.meets.feedback.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * A Student.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studentSequenceGenerator")
    @SequenceGenerator(
        name = "studentSequenceGenerator",
        sequenceName = "student_sequence_generator",
        allocationSize = 1
    )
    private Long id;

    @Column(name = "external_id", unique = true)
    private Long externalId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "student_email")
    private String studentEmail;

    @Column(name = "personal_email")
    private String personalEmail;

    @OneToMany(mappedBy = "student")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Feedback> feedbacks = new HashSet<>();


    @ManyToOne
    @JsonIgnoreProperties(value = "students", allowSetters = true)
    private UniversityYear universityYear;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Student id(Long id) {
        this.id = id;
        return this;
    }

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

    public Student feedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
        return this;
    }

    public Student addFeedbacks(Feedback feedback) {
        this.feedbacks.add(feedback);
        feedback.setStudent(this);
        return this;
    }

    public Student removeFeedbacks(Feedback feedback) {
        this.feedbacks.remove(feedback);
        feedback.setStudent(null);
        return this;
    }

    public Student universityYear(UniversityYear universityYear) {
        this.universityYear = universityYear;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
