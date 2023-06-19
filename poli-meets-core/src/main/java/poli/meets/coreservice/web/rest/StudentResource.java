package poli.meets.coreservice.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import poli.meets.coreservice.producer.StudentProducer;
import poli.meets.coreservice.service.StudentService;
import poli.meets.coreservice.service.dto.StudentDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import poli.meets.coreservice.service.dto.StudentPostDTO;

/**
 * REST controller for managing {@link poli.meets.coreservice.domain.Student}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class StudentResource {


    private final StudentService studentService;

    private final StudentProducer studentProducer;


    /**
     * {@code POST  /students} : Create a new student.
     *
     * @param bodyData the studentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentDTO, or with status {@code 400 (Bad Request)} if the student has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping(value = "/students")
    public ResponseEntity<StudentDTO> createStudent(@RequestHeader("Authorization") String token,
                                                    @RequestPart String bodyData,
                                                    @RequestPart(value = "file", required = false) Optional<MultipartFile> file) throws URISyntaxException, IOException {
        log.debug("REST request to save Student : {}", bodyData);
        StudentPostDTO studentPostDTO = new ObjectMapper().readValue(bodyData, StudentPostDTO.class);
        StudentDTO result = studentService.save(studentPostDTO, file, token);

        studentProducer.createOrUpdateStudent(result);
        return ResponseEntity.created(new URI("/api/students/" + result.getId()))
            .body(result);
    }


    @PutMapping("/students")
    public ResponseEntity<StudentDTO> updateStudent(@RequestHeader("Authorization") String token,
                                                    @RequestPart String bodyData,
                                                    @RequestPart(value = "file", required = false) Optional<MultipartFile> file) throws URISyntaxException, IOException {

        StudentPostDTO studentPostDTO = new ObjectMapper().readValue(bodyData, StudentPostDTO.class);
        StudentDTO result = studentService.save(studentPostDTO, file, token);

        studentProducer.createOrUpdateStudent(result);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /students} : get all the students.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of students in body.
     */
    @GetMapping("/students")
    public List<StudentDTO> getAllStudents() {
        log.debug("REST request to get all Students");
        return studentService.findAll();
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
        StudentDTO studentDTO = studentService.findOne(id);
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

    @GetMapping("/students/completed-user")
    public ResponseEntity<Boolean> hasCurrentUserCompletedData(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(studentService.hasCurrentUserCompletedData(token));
    }

    @GetMapping("/students/current-user")
    public ResponseEntity<StudentDTO> getCurrentUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(studentService.findCurrentUser(token));
    }

}
