package com.poli.meets.mentorship.service.dto;

import com.poli.meets.mentorship.domain.enumeration.MeetingType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class MentorSlotDTO {
    private byte[] image;
    
    private String name;
    
    private String email;
    
    private Instant date;
    
    private MeetingType type;
}
