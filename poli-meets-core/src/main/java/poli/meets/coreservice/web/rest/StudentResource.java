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


    @PostMapping(value = "/students")
    public ResponseEntity<StudentDTO> createStudent(
            @RequestHeader("Authorization") String token,
            @RequestPart String bodyData,
            @RequestPart(value = "file", required = false) Optional<MultipartFile> file)
            throws URISyntaxException, IOException {
        StudentPostDTO studentPostDTO = new ObjectMapper().readValue(bodyData, StudentPostDTO.class);
        StudentDTO result = studentService.save(studentPostDTO, file, token);

        studentProducer.createOrUpdateStudent(result);
        return ResponseEntity.created(new URI("/api/students/" + result.getId()))
            .body(result);
    }

    @PutMapping("/students")
    public ResponseEntity<StudentDTO> updateStudent(
            @RequestHeader("Authorization") String token,
            @RequestPart String bodyData,
            @RequestPart(value = "file", required = false) Optional<MultipartFile> file)
            throws IOException {
        StudentPostDTO studentPostDTO = new ObjectMapper().readValue(bodyData, StudentPostDTO.class);
        StudentDTO result = studentService.save(studentPostDTO, file, token);

        studentProducer.createOrUpdateStudent(result);

        return ResponseEntity.ok().body(result);
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
