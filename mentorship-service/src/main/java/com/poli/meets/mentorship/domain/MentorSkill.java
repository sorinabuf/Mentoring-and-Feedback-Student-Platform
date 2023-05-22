package com.poli.meets.mentorship.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * A MentorSkill.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mentor_skill")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MentorSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mentorSkillSequenceGenerator")
    @SequenceGenerator(
        name = "mentorSkillSequenceGenerator",
        sequenceName = "mentor_skill_sequence_generator",
        allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "mentorSkills", allowSetters = true)
    private Mentor mentor;

    @ManyToOne
    @JsonIgnoreProperties(value = "mentorSkills", allowSetters = true)
    private Skill skill;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public MentorSkill id(Long id) {
        this.id = id;
        return this;
    }

    public MentorSkill mentor(Mentor mentor) {
        this.mentor = mentor;
        return this;
    }

    public MentorSkill skill(Skill skill) {
        this.skill = skill;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
