package com.poli.meets.feedback.web.rest;

import com.poli.meets.feedback.domain.TeachingAssistant;
import com.poli.meets.feedback.service.dto.TeachingAssistantDTO;
import com.poli.meets.feedback.service.TeachingAssistantService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing {@link TeachingAssistant}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TeachingAssistantResource {

    private final TeachingAssistantService teachingAssistantService;


    /**
     * {@code POST  /teaching-assistants} : Create a new teachingAssistant.
     *
     * @param teachingAssistantDTO the teachingAssistantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teachingAssistantDTO, or with status {@code 400 (Bad Request)} if the teachingAssistant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/teaching-assistants")
    public ResponseEntity<TeachingAssistantDTO> createTeachingAssistant(@RequestBody TeachingAssistantDTO teachingAssistantDTO) throws URISyntaxException {
        log.debug("REST request to save TeachingAssistant : {}", teachingAssistantDTO);

        TeachingAssistantDTO result = teachingAssistantService.save(teachingAssistantDTO);
        return ResponseEntity.created(new URI("/api/teaching-assistants/" + result.getId())).body(result);
    }

    /**
     * {@code PUT  /teaching-assistants} : Updates an existing teachingAssistant.
     *
     * @param teachingAssistantDTO the teachingAssistantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teachingAssistantDTO,
     * or with status {@code 400 (Bad Request)} if the teachingAssistantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teachingAssistantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/teaching-assistants")
    public ResponseEntity<TeachingAssistantDTO> updateTeachingAssistant(@RequestBody TeachingAssistantDTO teachingAssistantDTO) throws URISyntaxException {
        log.debug("REST request to update TeachingAssistant : {}", teachingAssistantDTO);

        TeachingAssistantDTO result = teachingAssistantService.save(teachingAssistantDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /teaching-assistants} : get all the teachingAssistants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teachingAssistants in body.
     */
    @GetMapping("/teaching-assistants")
    public List<TeachingAssistantDTO> getAllTeachingAssistants() {
        log.debug("REST request to get all TeachingAssistants");
        return teachingAssistantService.findAll();
    }


    /**
     * {@code DELETE  /teaching-assistants/:id} : delete the "id" teachingAssistant.
     *
     * @param id the id of the teachingAssistantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/teaching-assistants/{id}")
    public ResponseEntity<Void> deleteTeachingAssistant(@PathVariable Long id) {
        log.debug("REST request to delete TeachingAssistant : {}", id);
        teachingAssistantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
