package com.poli.meets.mentorship.web.rest;

import com.poli.meets.mentorship.domain.MentorSubject;
import com.poli.meets.mentorship.service.MentorSubjectService;
import com.poli.meets.mentorship.service.dto.MentorSubjectDTO;

import com.poli.meets.mentorship.service.dto.PagedResponse;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;


import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing {@link MentorSubject}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MentorSubjectResource {

    private final MentorSubjectService mentorSubjectService;


    /**
     * {@code POST  /mentor-subjects} : Create a new mentorSubject.
     *
     * @param mentorSubjectDTO the mentorSubjectDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mentorSubjectDTO, or with status {@code 400 (Bad Request)} if the mentorSubject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mentor-subjects")
    public ResponseEntity<MentorSubjectDTO> createMentorSubject(@RequestBody MentorSubjectDTO mentorSubjectDTO) throws URISyntaxException {
        log.debug("REST request to save MentorSubject : {}", mentorSubjectDTO);

        MentorSubjectDTO result = mentorSubjectService.save(mentorSubjectDTO);
        return ResponseEntity.created(new URI("/api/mentor-subjects/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /mentor-subjects} : Updates an existing mentorSubject.
     *
     * @param mentorSubjectDTO the mentorSubjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mentorSubjectDTO,
     * or with status {@code 400 (Bad Request)} if the mentorSubjectDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mentorSubjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mentor-subjects")
    public ResponseEntity<MentorSubjectDTO> updateMentorSubject(@RequestBody MentorSubjectDTO mentorSubjectDTO) throws URISyntaxException {
        log.debug("REST request to update MentorSubject : {}", mentorSubjectDTO);

        MentorSubjectDTO result = mentorSubjectService.save(mentorSubjectDTO);
        return ResponseEntity.ok()
            .body(result);
    }


    @GetMapping("/mentor-subjects")
    public ResponseEntity<PagedResponse<MentorSubjectDTO>> getAllMentorSubjects(Pageable pageable) {

        return ResponseEntity.ok().body(PagedResponse.of(mentorSubjectService.findAll(pageable)));
    }

    /**
     * {@code DELETE  /mentor-subjects/:id} : delete the "id" mentorSubject.
     *
     * @param id the id of the mentorSubjectDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mentor-subjects/{id}")
    public ResponseEntity<Void> deleteMentorSubject(@PathVariable Long id) {
        log.debug("REST request to delete MentorSubject : {}", id);
        mentorSubjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
