package com.poli.meets.mentorship.domain;

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
 * A Mentor.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mentor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mentorSequenceGenerator")
    @SequenceGenerator(
        name = "mentorSequenceGenerator",
        sequenceName = "mentor_sequence_generator",
        allocationSize = 1
    )
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "mentor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MentorSubject> mentorSubjects = new HashSet<>();

    @OneToMany(mappedBy = "mentor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MentorSkill> mentorSkills = new HashSet<>();

    @OneToMany(mappedBy = "mentor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MeetingSlot> meetingSlots = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "mentors", allowSetters = true)
    private Student student;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Mentor id(Long id) {
        this.id = id;
        return this;
    }

    public Mentor description(String description) {
        this.description = description;
        return this;
    }

    public Mentor mentorSubjects(Set<MentorSubject> mentorSubjects) {
        this.mentorSubjects = mentorSubjects;
        return this;
    }

    public Mentor addMentorSubjects(MentorSubject mentorSubject) {
        this.mentorSubjects.add(mentorSubject);
        mentorSubject.setMentor(this);
        return this;
    }

    public Mentor removeMentorSubjects(MentorSubject mentorSubject) {
        this.mentorSubjects.remove(mentorSubject);
        mentorSubject.setMentor(null);
        return this;
    }

    public Mentor mentorSkills(Set<MentorSkill> mentorSkills) {
        this.mentorSkills = mentorSkills;
        return this;
    }

    public Mentor addMentorSkills(MentorSkill mentorSkill) {
        this.mentorSkills.add(mentorSkill);
        mentorSkill.setMentor(this);
        return this;
    }

    public Mentor removeMentorSkills(MentorSkill mentorSkill) {
        this.mentorSkills.remove(mentorSkill);
        mentorSkill.setMentor(null);
        return this;
    }

    public Mentor meetingSlots(Set<MeetingSlot> meetingSlots) {
        this.meetingSlots = meetingSlots;
        return this;
    }

    public Mentor addMeetingSlots(MeetingSlot meetingSlot) {
        this.meetingSlots.add(meetingSlot);
        meetingSlot.setMentor(this);
        return this;
    }

    public Mentor removeMeetingSlots(MeetingSlot meetingSlot) {
        this.meetingSlots.remove(meetingSlot);
        meetingSlot.setMentor(null);
        return this;
    }

    public Mentor student(Student student) {
        this.student = student;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
