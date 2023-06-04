package com.poli.meets.mentorship.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poli.meets.mentorship.domain.enumeration.MeetingSlotStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A MeetingSlot.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meeting_slot")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MeetingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meetingSlotSequenceGenerator")
    @SequenceGenerator(
        name = "meetingSlotSequenceGenerator",
        sequenceName = "meeting_slot_sequence_generator",
        allocationSize = 1
    )
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MeetingSlotStatus status;

    @OneToMany(mappedBy = "meetingSlot")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MeetingRequest> meetingRequests = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "meetingSlots", allowSetters = true)
    private Mentor mentor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public MeetingSlot id(Long id) {
        this.id = id;
        return this;
    }

    public MeetingSlot date(Instant date) {
        this.date = date;
        return this;
    }

    public MeetingSlot status(MeetingSlotStatus status) {
        this.status = status;
        return this;
    }

    public MeetingSlot meetingRequests(Set<MeetingRequest> meetingRequests) {
        this.meetingRequests = meetingRequests;
        return this;
    }

    public MeetingSlot addMeetingRequests(MeetingRequest meetingRequest) {
        this.meetingRequests.add(meetingRequest);
        meetingRequest.setMeetingSlot(this);
        return this;
    }

    public MeetingSlot removeMeetingRequests(MeetingRequest meetingRequest) {
        this.meetingRequests.remove(meetingRequest);
        meetingRequest.setMeetingSlot(null);
        return this;
    }

    public MeetingSlot mentor(Mentor mentor) {
        this.mentor = mentor;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
