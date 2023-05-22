package com.poli.meets.mentorship.web.rest;

import com.poli.meets.mentorship.domain.Faculty;
import com.poli.meets.mentorship.service.dto.FacultyDTO;
import com.poli.meets.mentorship.service.FacultyService;

import com.poli.meets.mentorship.service.dto.PagedResponse;
import com.poli.meets.mentorship.web.rest.errors.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;


import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing {@link Faculty}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class FacultyResource {

    private final FacultyService facultyService;

    /**
     * {@code POST  /faculties} : Create a new faculty.
     *
     * @param facultyDTO the facultyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facultyDTO, or with status {@code 400 (Bad Request)} if the faculty has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/faculties")
    public ResponseEntity<FacultyDTO> createFaculty(@RequestBody FacultyDTO facultyDTO) throws URISyntaxException {
        log.debug("REST request to save Faculty : {}", facultyDTO);

        FacultyDTO result = facultyService.save(facultyDTO);
        return ResponseEntity.created(new URI("/api/faculties/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /faculties} : Updates an existing faculty.
     *
     * @param facultyDTO the facultyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facultyDTO,
     * or with status {@code 400 (Bad Request)} if the facultyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facultyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/faculties")
    public ResponseEntity<FacultyDTO> updateFaculty(@RequestBody FacultyDTO facultyDTO) throws URISyntaxException {
        log.debug("REST request to update Faculty : {}", facultyDTO);

        FacultyDTO result = facultyService.save(facultyDTO);
        return ResponseEntity.ok()
            .body(result);
    }

    @GetMapping("/faculties")
    public ResponseEntity<PagedResponse<FacultyDTO>> getAllFaculties(Pageable pageable) {

        return ResponseEntity.ok().body(PagedResponse.of(facultyService.findAll(pageable)));
    }

    /**
     * {@code GET  /faculties/:id} : get the "id" faculty.
     *
     * @param id the id of the facultyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facultyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/faculties/{id}")
    public ResponseEntity<FacultyDTO> getFaculty(@PathVariable Long id) {
        log.debug("REST request to get Faculty : {}", id);
        FacultyDTO facultyDTO = facultyService.findOne(id).orElseThrow(BadRequestException::new);
        return ResponseEntity.ok(facultyDTO);
    }

    /**
     * {@code DELETE  /faculties/:id} : delete the "id" faculty.
     *
     * @param id the id of the facultyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/faculties/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        log.debug("REST request to delete Faculty : {}", id);
        facultyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
