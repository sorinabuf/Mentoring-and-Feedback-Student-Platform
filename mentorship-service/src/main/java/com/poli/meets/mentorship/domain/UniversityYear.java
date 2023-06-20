package com.poli.meets.mentorship.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poli.meets.mentorship.domain.enumeration.Year;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * A UniversityYear.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "university_year")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UniversityYear {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "universityYearSequenceGenerator")
    @SequenceGenerator(
        name = "universityYearSequenceGenerator",
        sequenceName = "university_year_sequence_generator",
        allocationSize = 1
    )
    private Long id;

    @Column(name = "external_id")
    private Long externalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "year")
    private Year year;

    @Column(name = "series")
    private String series;

    @OneToMany(mappedBy = "universityYear")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<UniversityClass> universityClasses = new HashSet<>();

    @OneToMany(mappedBy = "universityYear")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Student> students = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "universityYears", allowSetters = true)
    private Faculty faculty;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UniversityYear id(Long id) {
        this.id = id;
        return this;
    }

    public UniversityYear year(Year year) {
        this.year = year;
        return this;
    }

    public UniversityYear series(String series) {
        this.series = series;
        return this;
    }

    public UniversityYear universityClasses(Set<UniversityClass> universityClasses) {
        this.universityClasses = universityClasses;
        return this;
    }

    public UniversityYear addUniversityClasses(UniversityClass universityClass) {
        this.universityClasses.add(universityClass);
        universityClass.setUniversityYear(this);
        return this;
    }

    public UniversityYear removeUniversityClasses(UniversityClass universityClass) {
        this.universityClasses.remove(universityClass);
        universityClass.setUniversityYear(null);
        return this;
    }

    public UniversityYear students(Set<Student> students) {
        this.students = students;
        return this;
    }

    public UniversityYear addStudents(Student student) {
        this.students.add(student);
        student.setUniversityYear(this);
        return this;
    }

    public UniversityYear removeStudents(Student student) {
        this.students.remove(student);
        student.setUniversityYear(null);
        return this;
    }

    public UniversityYear faculty(Faculty faculty) {
        this.faculty = faculty;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
