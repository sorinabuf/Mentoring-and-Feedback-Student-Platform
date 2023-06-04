package com.poli.meets.feedback.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poli.meets.feedback.domain.enumeration.Semester;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

    @Enumerated(EnumType.STRING)
    @Column(name = "semester")
    private Semester semester;


    @ManyToOne
    @JsonIgnoreProperties(value = "universityClasses", allowSetters = true)
    private Teacher teacher;

    @ManyToOne
    @JsonIgnoreProperties(value = "universityClasses", allowSetters = true)
    private UniversityYear universityYear;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UniversityClass id(Long id) {
        this.id = id;
        return this;
    }

    public UniversityClass name(String name) {
        this.name = name;
        return this;
    }

    public UniversityClass abbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
        return this;
    }

    public UniversityClass description(String description) {
        this.description = description;
        return this;
    }

    public UniversityClass semester(Semester semester) {
        this.semester = semester;
        return this;
    }


    public UniversityClass teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public UniversityClass universityYear(UniversityYear universityYear) {
        this.universityYear = universityYear;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
