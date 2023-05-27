package com.poli.meets.feedback.service.dto;

import com.poli.meets.feedback.domain.TeachingAssistant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link TeachingAssistant} entity.
 */
@Data
@NoArgsConstructor
public class TeachingAssistantDTO implements Serializable {
    
    private Long id;

    private String firstName;

    private String lastName;

}
