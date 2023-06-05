package com.poli.meets.feedback.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facultySequenceGenerator")
    @SequenceGenerator(
        name = "facultySequenceGenerator",
        sequenceName = "faculty_sequence_generator",
        allocationSize = 1
    )
    private Long id;

    @Column(name = "external_id", unique = true)
    private Long externalId;

    @Column(name = "name")
    private String name;

    @Column(name = "domain")
    private String domain;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "faculty")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<UniversityYear> universityYears = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Faculty id(Long id) {
        this.id = id;
        return this;
    }

    public Faculty name(String name) {
        this.name = name;
        return this;
    }

    public Faculty domain(String domain) {
        this.domain = domain;
        return this;
    }

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
