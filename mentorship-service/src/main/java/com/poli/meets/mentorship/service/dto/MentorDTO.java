package com.poli.meets.mentorship.service.dto;

import com.poli.meets.mentorship.domain.Mentor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link Mentor} entity.
 */
@Data
@NoArgsConstructor
public class MentorDTO implements Serializable {
    
    private Long id;

    private String description;


    private StudentDTO student;
}
