package com.poli.meets.mentorship.service.dto;

import com.poli.meets.mentorship.domain.MeetingRequest;
import com.poli.meets.mentorship.domain.enumeration.MeetingRequestStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link MeetingRequest} entity.
 */
@Data
@NoArgsConstructor
public class MeetingRequestDTO implements Serializable {
    
    private Long id;

    private String description;

    private MeetingRequestStatus status;

    private Long meetingSlotId;

    private Long studentId;

    private Long mentorSubjectId;
}
