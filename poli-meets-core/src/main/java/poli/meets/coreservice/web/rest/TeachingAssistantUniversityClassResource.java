package poli.meets.coreservice.web.rest;

import lombok.AllArgsConstructor;
import poli.meets.coreservice.service.TeachingAssistantUniversityClassService;
import poli.meets.coreservice.service.dto.TeachingAssistantUniversityClassDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing {@link poli.meets.coreservice.domain.TeachingAssistantUniversityClass}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TeachingAssistantUniversityClassResource {


    private final TeachingAssistantUniversityClassService teachingAssistantUniversityClassService;


    /**
     * {@code POST  /teaching-assistant-university-classes} : Create a new teachingAssistantUniversityClass.
     *
     * @param teachingAssistantUniversityClassDTO the teachingAssistantUniversityClassDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teachingAssistantUniversityClassDTO, or with status {@code 400 (Bad Request)} if the teachingAssistantUniversityClass has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/teaching-assistant-university-classes")
    public ResponseEntity<TeachingAssistantUniversityClassDTO> createTeachingAssistantUniversityClass(@RequestBody TeachingAssistantUniversityClassDTO teachingAssistantUniversityClassDTO) throws URISyntaxException {
        log.debug("REST request to save TeachingAssistantUniversityClass : {}", teachingAssistantUniversityClassDTO);

        TeachingAssistantUniversityClassDTO result = teachingAssistantUniversityClassService.save(teachingAssistantUniversityClassDTO);
        return ResponseEntity.created(new URI("/api/teaching-assistant-university-classes/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /teaching-assistant-university-classes} : Updates an existing teachingAssistantUniversityClass.
     *
     * @param teachingAssistantUniversityClassDTO the teachingAssistantUniversityClassDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teachingAssistantUniversityClassDTO,
     * or with status {@code 400 (Bad Request)} if the teachingAssistantUniversityClassDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teachingAssistantUniversityClassDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/teaching-assistant-university-classes")
    public ResponseEntity<TeachingAssistantUniversityClassDTO> updateTeachingAssistantUniversityClass(@RequestBody TeachingAssistantUniversityClassDTO teachingAssistantUniversityClassDTO) throws URISyntaxException {
        log.debug("REST request to update TeachingAssistantUniversityClass : {}", teachingAssistantUniversityClassDTO);

        TeachingAssistantUniversityClassDTO result = teachingAssistantUniversityClassService.save(teachingAssistantUniversityClassDTO);
        return ResponseEntity.ok()
            .body(result);
    }

    /**
     * {@code GET  /teaching-assistant-university-classes} : get all the teachingAssistantUniversityClasses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teachingAssistantUniversityClasses in body.
     */
    @GetMapping("/teaching-assistant-university-classes")
    public List<TeachingAssistantUniversityClassDTO> getAllTeachingAssistantUniversityClasses() {
        log.debug("REST request to get all TeachingAssistantUniversityClasses");
        return teachingAssistantUniversityClassService.findAll();
    }


    /**
     * {@code DELETE  /teaching-assistant-university-classes/:id} : delete the "id" teachingAssistantUniversityClass.
     *
     * @param id the id of the teachingAssistantUniversityClassDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/teaching-assistant-university-classes/{id}")
    public ResponseEntity<Void> deleteTeachingAssistantUniversityClass(@PathVariable Long id) {
        log.debug("REST request to delete TeachingAssistantUniversityClass : {}", id);
        teachingAssistantUniversityClassService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
