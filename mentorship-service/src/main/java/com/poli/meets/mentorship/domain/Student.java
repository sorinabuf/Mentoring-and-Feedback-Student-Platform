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
 * A Student.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studentSequenceGenerator")
    @SequenceGenerator(
        name = "studentSequenceGenerator",
        sequenceName = "student_sequence_generator",
        allocationSize = 1
    )
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "student_email")
    private String studentEmail;

    @Column(name = "personal_email")
    private String personalEmail;

    @Column(name = "group_num")
    private String groupNum;

    @Column(name = "image")
    private byte[] image;

    @OneToMany(mappedBy = "student")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Mentor> mentors = new HashSet<>();

    @OneToMany(mappedBy = "student")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MeetingRequest> meetingRequests = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "students", allowSetters = true)
    private UniversityYear universityYear;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Student id(Long id) {
        this.id = id;
        return this;
    }

    public Student firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Student lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Student studentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
        return this;
    }

    public Student personalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
        return this;
    }

    public Student mentors(Set<Mentor> mentors) {
        this.mentors = mentors;
        return this;
    }

    public Student addMentors(Mentor mentor) {
        this.mentors.add(mentor);
        mentor.setStudent(this);
        return this;
    }

    public Student removeMentors(Mentor mentor) {
        this.mentors.remove(mentor);
        mentor.setStudent(null);
        return this;
    }

    public Student meetingRequests(Set<MeetingRequest> meetingRequests) {
        this.meetingRequests = meetingRequests;
        return this;
    }

    public Student addMeetingRequests(MeetingRequest meetingRequest) {
        this.meetingRequests.add(meetingRequest);
        meetingRequest.setStudent(this);
        return this;
    }

    public Student removeMeetingRequests(MeetingRequest meetingRequest) {
        this.meetingRequests.remove(meetingRequest);
        meetingRequest.setStudent(null);
        return this;
    }

    public Student universityYear(UniversityYear universityYear) {
        this.universityYear = universityYear;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
