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
import java.util.Optional;

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

    /**
     * {@code POST  /mentors} : Create a new mentor.
     *
     * @param mentorDTO the mentorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mentorDTO, or with status {@code 400 (Bad Request)} if the mentor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mentors")
    public ResponseEntity<MentorDTO> createMentor(@RequestBody MentorDTO mentorDTO) throws URISyntaxException {
        log.debug("REST request to save Mentor : {}", mentorDTO);

        MentorDTO result = mentorService.save(mentorDTO);
        return ResponseEntity.created(new URI("/api/mentors/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /mentors} : Updates an existing mentor.
     *
     * @param mentorDTO the mentorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mentorDTO,
     * or with status {@code 400 (Bad Request)} if the mentorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mentorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mentors")
    public ResponseEntity<MentorDTO> updateMentor(@RequestBody MentorDTO mentorDTO) throws URISyntaxException {
        log.debug("REST request to update Mentor : {}", mentorDTO);

        MentorDTO result = mentorService.save(mentorDTO);
        return ResponseEntity.ok()
            .body(result);
    }


    @GetMapping("/mentors")
    public ResponseEntity<PagedResponse<MentorDTO>> getAllMentors(Pageable pageable) {

        return ResponseEntity.ok().body(PagedResponse.of(mentorService.findAll(pageable)));
    }



    /**
     * {@code DELETE  /mentors/:id} : delete the "id" mentor.
     *
     * @param id the id of the mentorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mentors/{id}")
    public ResponseEntity<Void> deleteMentor(@PathVariable Long id) {
        log.debug("REST request to delete Mentor : {}", id);
        mentorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mentors/current-user")
    public ResponseEntity<MentorInfoDTO> getCurrentMentor(@RequestHeader(
            "Authorization") String token) {

        return ResponseEntity.ok().body(mentorService.findCurrentMentor(token));
    }

    @PostMapping("/mentors/details")
    public ResponseEntity<MentorDTO> createMentor(@RequestHeader("Authorization") String token,
            @RequestBody MentorInfoDTO mentorInfoDTO) {
        return ResponseEntity.ok().body(mentorService.save(token, mentorInfoDTO));
    }

    @PutMapping("/mentors/details")
    public ResponseEntity<MentorDTO> updateMentor(@RequestHeader("Authorization") String token,
                                                  @RequestBody MentorInfoDTO mentorInfoDTO) {
        return ResponseEntity.ok().body(mentorService.update(token, mentorInfoDTO));
    }
}
