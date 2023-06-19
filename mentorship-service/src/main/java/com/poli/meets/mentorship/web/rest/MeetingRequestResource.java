package com.poli.meets.mentorship.web.rest;

import com.poli.meets.mentorship.domain.MeetingRequest;
import com.poli.meets.mentorship.domain.enumeration.MeetingRequestStatus;
import com.poli.meets.mentorship.service.dto.MeetingDTO;
import com.poli.meets.mentorship.service.dto.MeetingRequestDTO;
import com.poli.meets.mentorship.service.MeetingRequestService;

import com.poli.meets.mentorship.service.dto.MeetingSlotDTO;
import com.poli.meets.mentorship.service.dto.PagedResponse;
import com.poli.meets.mentorship.web.rest.errors.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing {@link MeetingRequest}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MeetingRequestResource {

    private final MeetingRequestService meetingRequestService;

    @GetMapping("/meeting-requests/current-user/mentor/approved")
    public ResponseEntity<List<MeetingDTO>> getAllUpcomingMeetingSlots(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(meetingRequestService
                .findAllMeetings(token, MeetingRequestStatus.APPROVED));
    }

    @GetMapping("/meeting-requests/current-user/mentor/pending")
    public ResponseEntity<List<MeetingDTO>> getAllPendingMeetingSlots(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(meetingRequestService
                .findAllMeetings(token, MeetingRequestStatus.PENDING));
    }

    @GetMapping("/meeting-requests/current-user/student/approved")
    public ResponseEntity<List<MeetingDTO>> getStudentUpcomingMeetingSlots(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(meetingRequestService.findStudentMeetings(token,
                MeetingRequestStatus.APPROVED));
    }

    @GetMapping("/meeting-requests/current-user/student/pending")
    public ResponseEntity<List<MeetingDTO>> getStudentPendingMeetingSlots(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(meetingRequestService.findStudentMeetings(token,
                MeetingRequestStatus.PENDING));
    }

    @DeleteMapping("/meeting-requests/approved")
    public ResponseEntity<Void> deleteUpcomingMeetingRequest(@RequestBody MeetingDTO meetingDTO) {
        meetingRequestService.deleteMeeting(meetingDTO,
                MeetingRequestStatus.APPROVED);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/meeting-requests/pending")
    public ResponseEntity<Void> deletePendingMeetingRequest(@RequestBody MeetingDTO meetingDTO) {
        meetingRequestService.deleteMeeting(meetingDTO,
                MeetingRequestStatus.PENDING);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/meeting-requests/current-user/mentor/pending")
    public ResponseEntity<Void> updatePendingMeeting(
            @RequestHeader("Authorization") String token,
            @RequestBody MeetingDTO meetingDTO) {
        meetingRequestService.approveMeeting(token, meetingDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/meeting-requests/current-user")
    public ResponseEntity<MeetingRequestDTO> createMeetingRequest(
            @RequestHeader("Authorization") String token,
            @RequestBody MeetingRequestDTO meetingRequestDTO) {
        return ResponseEntity.ok().body(
                meetingRequestService.saveStudentRequest(token, meetingRequestDTO));
    }
}
