package poli.meets.coreservice.web.rest;

import lombok.AllArgsConstructor;
import poli.meets.coreservice.service.FacultyService;
import poli.meets.coreservice.service.dto.FacultyDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing {@link poli.meets.coreservice.domain.Faculty}.
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

    /**
     * {@code GET  /faculties} : get all the faculties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of faculties in body.
     */
    @GetMapping("/faculties")
    public List<FacultyDTO> getAllFaculties() {
        log.debug("REST request to get all Faculties");
        return facultyService.findAll();
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
