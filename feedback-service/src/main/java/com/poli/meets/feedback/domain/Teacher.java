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
 * A Teacher.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teacher")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacherSequenceGenerator")
    @SequenceGenerator(
        name = "teacherSequenceGenerator",
        sequenceName = "teacher_sequence_generator",
        allocationSize = 1
    )
    private Long id;

    @Column(name = "external_id", unique = true)
    private Long externalId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Teacher id(Long id) {
        this.id = id;
        return this;
    }

    public Teacher firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Teacher lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
