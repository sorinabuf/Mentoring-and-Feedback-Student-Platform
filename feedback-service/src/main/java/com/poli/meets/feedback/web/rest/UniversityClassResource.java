package com.poli.meets.feedback.web.rest;

import com.poli.meets.feedback.domain.UniversityClass;
import com.poli.meets.feedback.service.dto.FeedbackSubjectsDTO;
import com.poli.meets.feedback.service.dto.SubjectDTO;
import com.poli.meets.feedback.service.dto.UniversityClassDTO;
import com.poli.meets.feedback.service.UniversityClassService;

import com.poli.meets.feedback.web.rest.errors.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing {@link UniversityClass}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UniversityClassResource {

    private final UniversityClassService universityClassService;


    /**
     * {@code POST  /university-classes} : Create a new universityClass.
     *
     * @param universityClassDTO the universityClassDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new universityClassDTO, or with status {@code 400 (Bad Request)} if the universityClass has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/university-classes")
    public ResponseEntity<UniversityClassDTO> createUniversityClass(@RequestBody UniversityClassDTO universityClassDTO) throws URISyntaxException {
        log.debug("REST request to save UniversityClass : {}", universityClassDTO);

        UniversityClassDTO result = universityClassService.save(universityClassDTO);
        return ResponseEntity.created(new URI("/api/university-classes/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /university-classes} : Updates an existing universityClass.
     *
     * @param universityClassDTO the universityClassDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated universityClassDTO,
     * or with status {@code 400 (Bad Request)} if the universityClassDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the universityClassDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/university-classes")
    public ResponseEntity<UniversityClassDTO> updateUniversityClass(@RequestBody UniversityClassDTO universityClassDTO) throws URISyntaxException {
        log.debug("REST request to update UniversityClass : {}", universityClassDTO);

        UniversityClassDTO result = universityClassService.save(universityClassDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /university-classes} : get all the universityClasses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of universityClasses in body.
     */
    @GetMapping("/university-classes")
    public List<UniversityClassDTO> getAllUniversityClasses() {
        log.debug("REST request to get all UniversityClasses");
        return universityClassService.findAll();
    }

    @GetMapping("/university-classes/{id}")
    public ResponseEntity<SubjectDTO> getAllUniversityClass(@PathVariable Long id) {
        log.debug("REST request to get all UniversityClasses");
        return ResponseEntity.ok(universityClassService.findOne(id)
                .orElseThrow(BadRequestException::new));
    }

    /**
     * {@code DELETE  /university-classes/:id} : delete the "id" universityClass.
     *
     * @param id the id of the universityClassDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/university-classes/{id}")
    public ResponseEntity<Void> deleteUniversityClass(@PathVariable Long id) {
        log.debug("REST request to delete UniversityClass : {}", id);
        universityClassService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/university-classes/me")
    public ResponseEntity<FeedbackSubjectsDTO> getAllUniversityClassesForCurrentUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(universityClassService.findAllFeedbackSubjects(token));
    }

}
