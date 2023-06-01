package com.poli.meets.mentorship.service.dto;

import com.poli.meets.mentorship.domain.enumeration.MeetingRequestStatus;
import com.poli.meets.mentorship.domain.enumeration.MeetingType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class MeetingDTO implements Serializable {
    private Long id;

    private String description;

    private MeetingRequestStatus status;

    private MeetingSlotDTO meetingSlot;

    private StudentDTO student;

    private MentorSubjectDTO mentorSubject;

    private MeetingType type;

    private StudentDTO mentor;
}
