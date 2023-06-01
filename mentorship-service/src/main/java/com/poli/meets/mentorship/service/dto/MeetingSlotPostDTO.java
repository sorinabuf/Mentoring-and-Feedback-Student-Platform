package com.poli.meets.mentorship.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
public class MeetingSlotPostDTO implements Serializable {
    private Instant date;
}
