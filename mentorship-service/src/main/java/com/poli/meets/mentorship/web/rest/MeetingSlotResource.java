package com.poli.meets.mentorship.web.rest;

import com.poli.meets.mentorship.domain.MeetingSlot;
import com.poli.meets.mentorship.service.dto.MeetingSlotDTO;
import com.poli.meets.mentorship.service.MeetingSlotService;

import com.poli.meets.mentorship.service.dto.MeetingSlotPostDTO;
import com.poli.meets.mentorship.service.dto.PagedResponse;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing {@link MeetingSlot}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MeetingSlotResource {

    private final MeetingSlotService meetingSlotService;

    @DeleteMapping("/meeting-slots/current-user/{id}")
    public ResponseEntity<Void> deleteMeetingSlot(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        meetingSlotService.delete(token, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/meeting-slots/current-user/free")
    public ResponseEntity<List<MeetingSlotDTO>> getAllMeetingSlotsForCurrentMentor(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(meetingSlotService.findFreeSlots(token));
    }

    @GetMapping("/meeting-slots")
    public ResponseEntity<List<MeetingSlotDTO>> getAllMeetingSlotsForMentor(
            @RequestParam Long mentorId) {
        return ResponseEntity.ok().body(meetingSlotService.findFreeSlots(mentorId));
    }

    @PostMapping("/meeting-slots/current-user")
    public ResponseEntity<MeetingSlotDTO> createMeetingSlot(
            @RequestHeader("Authorization") String token,
            @RequestBody MeetingSlotPostDTO meetingSlotDTO) {
        return ResponseEntity.ok().body(meetingSlotService.save(token, meetingSlotDTO));
    }
}
