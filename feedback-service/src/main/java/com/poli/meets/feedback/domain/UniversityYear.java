package com.poli.meets.feedback.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poli.meets.feedback.domain.enumeration.Year;
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

    @Column(name = "external_id", unique = true)
    private Long externalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "year")
    private Year year;

    @Column(name = "series")
    private String series;

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


    public UniversityYear faculty(Faculty faculty) {
        this.faculty = faculty;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
