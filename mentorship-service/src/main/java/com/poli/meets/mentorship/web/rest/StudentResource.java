package com.poli.meets.mentorship.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poli.meets.mentorship.domain.Student;
import com.poli.meets.mentorship.service.StudentService;
import com.poli.meets.mentorship.service.dto.MentorDTO;
import com.poli.meets.mentorship.service.dto.PagedResponse;
import com.poli.meets.mentorship.service.dto.StudentDTO;


import com.poli.meets.mentorship.service.dto.StudentPostDTO;
import com.poli.meets.mentorship.web.rest.errors.BadRequestException;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing {@link Student}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class StudentResource {

    private final StudentService studentService;


    @PostMapping(value = "/students")
    public ResponseEntity<StudentDTO> createStudent(@RequestHeader("Authorization") String token,
                                                    @RequestPart String bodyData,
                                                    @RequestPart(value = "file", required = false) Optional<MultipartFile> file) throws URISyntaxException, IOException {
        log.debug("REST request to save Student : {}", bodyData);
        StudentPostDTO studentPostDTO = new ObjectMapper().readValue(bodyData, StudentPostDTO.class);
        StudentDTO result = studentService.save(studentPostDTO, file, token);
        return ResponseEntity.created(new URI("/api/students/" + result.getId()))
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

    @GetMapping("/students/current-user")
    public ResponseEntity<StudentDTO> getCurrentUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(studentService.findCurrentUser(token));
    }
}
