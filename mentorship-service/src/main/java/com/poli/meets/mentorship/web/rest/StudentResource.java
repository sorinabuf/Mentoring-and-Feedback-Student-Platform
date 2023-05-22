package com.poli.meets.mentorship.web.rest;

import com.poli.meets.mentorship.domain.Student;
import com.poli.meets.mentorship.service.StudentService;
import com.poli.meets.mentorship.service.dto.PagedResponse;
import com.poli.meets.mentorship.service.dto.StudentDTO;


import com.poli.meets.mentorship.web.rest.errors.BadRequestException;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing {@link Student}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class StudentResource {

    private final StudentService studentService;


    /**
     * {@code POST  /students} : Create a new student.
     *
     * @param studentDTO the studentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentDTO, or with status {@code 400 (Bad Request)} if the student has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/students")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) throws URISyntaxException {
        log.debug("REST request to save Student : {}", studentDTO);

        StudentDTO result = studentService.save(studentDTO);
        return ResponseEntity.created(new URI("/api/students/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /students} : Updates an existing student.
     *
     * @param studentDTO the studentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentDTO,
     * or with status {@code 400 (Bad Request)} if the studentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/students")
    public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentDTO studentDTO) throws URISyntaxException {
        log.debug("REST request to update Student : {}", studentDTO);

        StudentDTO result = studentService.save(studentDTO);
        return ResponseEntity.ok()
            .body(result);
    }


    @GetMapping("/students")
    public ResponseEntity<PagedResponse<StudentDTO>> getAllStudents(Pageable pageable) {
        return ResponseEntity.ok().body(PagedResponse.of(studentService.findAll(pageable)));
    }


    /**
     * {@code GET  /students/:id} : get the "id" student.
     *
     * @param id the id of the studentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/students/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
        log.debug("REST request to get Student : {}", id);
        StudentDTO studentDTO = studentService.findOne(id).orElseThrow(BadRequestException::new);
        return ResponseEntity.ok(studentDTO);
    }

    /**
     * {@code DELETE  /students/:id} : delete the "id" student.
     *
     * @param id the id of the studentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        log.debug("REST request to delete Student : {}", id);
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
