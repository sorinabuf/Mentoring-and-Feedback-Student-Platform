package com.poli.meets.mentorship.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * A Skill.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "skill")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skillSequenceGenerator")
    @SequenceGenerator(
        name = "skillSequenceGenerator",
        sequenceName = "skill_sequence_generator",
        allocationSize = 1
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "skill")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MentorSkill> mentorSkills = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Skill id(Long id) {
        this.id = id;
        return this;
    }

    public Skill name(String name) {
        this.name = name;
        return this;
    }

    public Skill mentorSkills(Set<MentorSkill> mentorSkills) {
        this.mentorSkills = mentorSkills;
        return this;
    }

    public Skill addMentorSkills(MentorSkill mentorSkill) {
        this.mentorSkills.add(mentorSkill);
        mentorSkill.setSkill(this);
        return this;
    }

    public Skill removeMentorSkills(MentorSkill mentorSkill) {
        this.mentorSkills.remove(mentorSkill);
        mentorSkill.setSkill(null);
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
