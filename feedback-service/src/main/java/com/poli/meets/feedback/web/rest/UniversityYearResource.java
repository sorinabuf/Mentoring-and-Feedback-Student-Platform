package com.poli.meets.feedback.web.rest;

import com.poli.meets.feedback.domain.UniversityYear;
import com.poli.meets.feedback.service.dto.UniversityYearDTO;
import com.poli.meets.feedback.service.UniversityYearService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing {@link UniversityYear}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UniversityYearResource {


    private final UniversityYearService universityYearService;


    /**
     * {@code POST  /university-years} : Create a new universityYear.
     *
     * @param universityYearDTO the universityYearDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new universityYearDTO, or with status {@code 400 (Bad Request)} if the universityYear has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/university-years")
    public ResponseEntity<UniversityYearDTO> createUniversityYear(@RequestBody UniversityYearDTO universityYearDTO) throws URISyntaxException {
        log.debug("REST request to save UniversityYear : {}", universityYearDTO);

        UniversityYearDTO result = universityYearService.save(universityYearDTO);
        return ResponseEntity.created(new URI("/api/university-years/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /university-years} : Updates an existing universityYear.
     *
     * @param universityYearDTO the universityYearDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated universityYearDTO,
     * or with status {@code 400 (Bad Request)} if the universityYearDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the universityYearDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/university-years")
    public ResponseEntity<UniversityYearDTO> updateUniversityYear(@RequestBody UniversityYearDTO universityYearDTO) throws URISyntaxException {
        log.debug("REST request to update UniversityYear : {}", universityYearDTO);

        UniversityYearDTO result = universityYearService.save(universityYearDTO);
        return ResponseEntity.ok()
            .body(result);
    }

    /**
     * {@code GET  /university-years} : get all the universityYears.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of universityYears in body.
     */
    @GetMapping("/university-years")
    public List<UniversityYearDTO> getAllUniversityYears() {
        log.debug("REST request to get all UniversityYears");
        return universityYearService.findAll();
    }

    @GetMapping("/next-university-year/days")
    public ResponseEntity<Long> getDaysUntilNextUniversityYear() {
        log.debug("REST request to get all UniversityYears");

        LocalDateTime now = LocalDateTime.now();

        int year = now.getMonthValue() >= 10 ? now.getYear() + 1 : now.getYear();

        long daysBetween = Duration
                .between(LocalDateTime.now(), LocalDateTime.of(year, Month.OCTOBER, 1, 0, 0 , 0))
                .toDays();

        return ResponseEntity.ok(daysBetween);
    }


    /**
     * {@code DELETE  /university-years/:id} : delete the "id" universityYear.
     *
     * @param id the id of the universityYearDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/university-years/{id}")
    public ResponseEntity<Void> deleteUniversityYear(@PathVariable Long id) {
        log.debug("REST request to delete UniversityYear : {}", id);
        universityYearService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
