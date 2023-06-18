package com.poli.meets.mentorship.web.rest;

import com.poli.meets.mentorship.domain.Mentor;
import com.poli.meets.mentorship.service.MentorService;
import com.poli.meets.mentorship.service.dto.MentorDTO;

import com.poli.meets.mentorship.service.dto.MentorInfoDTO;
import com.poli.meets.mentorship.service.dto.PagedResponse;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing {@link Mentor}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MentorResource {

    private final MentorService mentorService;

    @DeleteMapping("/mentors/current-user")
    public ResponseEntity<Void> deleteMentor(@RequestHeader("Authorization") String token) {
        mentorService.delete(token);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mentors/current-user")
    public ResponseEntity<MentorInfoDTO> getCurrentMentor(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(mentorService.findCurrentMentor(token));
    }

    @PostMapping("/mentors/current-user/details")
    public ResponseEntity<MentorDTO> createMentor(
            @RequestHeader("Authorization") String token,
            @RequestBody MentorInfoDTO mentorInfoDTO) throws URISyntaxException {
        MentorDTO result = mentorService.save(token, mentorInfoDTO);
        return ResponseEntity.created(new URI("/api/mentors/" + result.getId()))
                .body(result);
    }

    @PutMapping("/mentors/current-user/details")
    public ResponseEntity<MentorDTO> updateMentor(
            @RequestHeader("Authorization") String token,
            @RequestBody MentorInfoDTO mentorInfoDTO) {
        return ResponseEntity.ok().body(mentorService.update(token, mentorInfoDTO));
    }

    @GetMapping("/mentors/current-user/all")
    public ResponseEntity<List<MentorInfoDTO>> getCurrentUserMentors(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(mentorService.findStudentMentors(token));
    }
}
