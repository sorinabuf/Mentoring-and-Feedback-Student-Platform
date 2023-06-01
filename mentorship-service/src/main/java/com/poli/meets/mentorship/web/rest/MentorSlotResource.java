package com.poli.meets.mentorship.web.rest;

import com.poli.meets.mentorship.service.MentorSlotService;
import com.poli.meets.mentorship.service.dto.MeetingSlotDTO;
import com.poli.meets.mentorship.service.dto.MentorSlotDTO;
import com.poli.meets.mentorship.service.dto.PagedResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MentorSlotResource {
    private final MentorSlotService mentorSlotService;

    @GetMapping("/mentor-slots/current-user")
    public ResponseEntity<List<MentorSlotDTO>> getAllMentorSlots(@RequestHeader(
            "Authorization") String token) {

        return ResponseEntity.ok().body(mentorSlotService.findAllMentorSlots(token));
    }
}
