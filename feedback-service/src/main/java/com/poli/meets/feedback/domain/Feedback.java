package com.poli.meets.feedback.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poli.meets.feedback.domain.enumeration.Grade;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * A Feedback.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedback")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedbackSequenceGenerator")
    @SequenceGenerator(
        name = "feedbackSequenceGenerator",
        sequenceName = "feedback_sequence_generator",
        allocationSize = 1
    )
    private Long id;


    @Column(name = "grade")
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Column(name = "feedback_text")
    private String feedbackText;


    @Column(name = "feedback_date")
    private LocalDateTime feedbackDate;

    @ManyToOne
    private Category category;
    @ManyToOne
    @JsonIgnoreProperties(value = "feedbacks", allowSetters = true)
    private Student student;

    @ManyToOne
    @JsonIgnoreProperties(value = "feedbacks", allowSetters = true)
    private UniversityClass universityClass;


    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Feedback id(Long id) {
        this.id = id;
        return this;
    }


    public Feedback student(Student student) {
        this.student = student;
        return this;
    }

    public Feedback universityClass(UniversityClass universityClass) {
        this.universityClass = universityClass;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
