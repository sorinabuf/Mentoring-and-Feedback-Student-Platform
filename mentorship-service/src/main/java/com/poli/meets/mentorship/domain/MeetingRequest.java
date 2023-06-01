package com.poli.meets.mentorship.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poli.meets.mentorship.domain.enumeration.MeetingRequestStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * A MeetingRequest.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meeting_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MeetingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meetingRequestSequenceGenerator")
    @SequenceGenerator(
        name = "meetingRequestSequenceGenerator",
        sequenceName = "meeting_request_sequence_generator",
        allocationSize = 1
    )
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MeetingRequestStatus status;

    @ManyToOne
    @JsonIgnoreProperties(value = "meetingRequests", allowSetters = true)
    private MeetingSlot meetingSlot;

    @ManyToOne
    @JsonIgnoreProperties(value = "meetingRequests", allowSetters = true)
    private Student student;

    @ManyToOne
    @JsonIgnoreProperties(value = "meetingRequests", allowSetters = true)
    private MentorSubject mentorSubject;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public MeetingRequest id(Long id) {
        this.id = id;
        return this;
    }

    public MeetingRequest description(String description) {
        this.description = description;
        return this;
    }

    public MeetingRequest status(MeetingRequestStatus status) {
        this.status = status;
        return this;
    }

    public MeetingRequest meetingSlot(MeetingSlot meetingSlot) {
        this.meetingSlot = meetingSlot;
        return this;
    }

    public MeetingRequest student(Student student) {
        this.student = student;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
