package com.poli.meets.feedback.service.dto;

import com.poli.meets.feedback.domain.TeachingAssistantUniversityClass;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link TeachingAssistantUniversityClass} entity.
 */
@Data
@NoArgsConstructor
public class TeachingAssistantUniversityClassDTO implements Serializable {
    
    private Long id;


    private Long teachingAssistantId;

    private Long universityClassId;
}
