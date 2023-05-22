package com.poli.meets.mentorship.service.dto;

import com.poli.meets.mentorship.domain.MentorSubject;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link MentorSubject} entity.
 */
@Data
@NoArgsConstructor
public class MentorSubjectDTO implements Serializable {
    
    private Long id;


    private Long universityClassId;

    private Long mentorId;
}
