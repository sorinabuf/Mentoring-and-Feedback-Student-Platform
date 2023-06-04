package com.poli.meets.mentorship.service.dto;

import com.poli.meets.mentorship.domain.MeetingSlot;
import com.poli.meets.mentorship.domain.enumeration.MeetingSlotStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link MeetingSlot} entity.
 */
@Data
@NoArgsConstructor
public class MeetingSlotDTO implements Serializable {
    
    private Long id;

    private Instant date;

    private MeetingSlotStatus status;

    private Long mentorId;
}
