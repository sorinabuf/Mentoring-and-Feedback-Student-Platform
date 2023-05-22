package com.poli.meets.mentorship.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * A MentorSubject.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mentor_subject")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MentorSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mentorSubjectSequenceGenerator")
    @SequenceGenerator(
        name = "mentorSubjectSequenceGenerator",
        sequenceName = "mentor_subject_sequence_generator",
        allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "mentorSubjects", allowSetters = true)
    private UniversityClass universityClass;

    @ManyToOne
    @JsonIgnoreProperties(value = "mentorSubjects", allowSetters = true)
    private Mentor mentor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public MentorSubject id(Long id) {
        this.id = id;
        return this;
    }

    public MentorSubject universityClass(UniversityClass universityClass) {
        this.universityClass = universityClass;
        return this;
    }

    public MentorSubject mentor(Mentor mentor) {
        this.mentor = mentor;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
