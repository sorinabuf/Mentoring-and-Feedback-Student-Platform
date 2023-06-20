package poli.meets.coreservice.web.rest;

import lombok.AllArgsConstructor;
import poli.meets.coreservice.client.AuthClient;
import poli.meets.coreservice.producer.TeacherProducer;
import poli.meets.coreservice.service.TeacherService;
import poli.meets.coreservice.service.dto.TeacherDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import poli.meets.coreservice.web.rest.errors.ForbiddenException;

/**
 * REST controller for managing {@link poli.meets.coreservice.domain.Teacher}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TeacherResource {

    private final TeacherService teacherService;

    private final AuthClient authClient;

    private final TeacherProducer teacherProducer;

    @PostMapping("/teachers")
    public ResponseEntity<TeacherDTO> createTeacher(
            @RequestHeader("Authorization") String token,
            @RequestBody TeacherDTO teacherDTO) throws URISyntaxException {
        ResponseEntity<Boolean> isAdmin = authClient.isCurrentUserAdmin(token);
        if (isAdmin == null || isAdmin.getBody() == null || !isAdmin.getBody()) {
            throw new ForbiddenException();
        }

        TeacherDTO result = teacherService.save(teacherDTO);

        teacherProducer.createOrUpdate(result);
        return ResponseEntity.created(new URI("/api/teachers/" + result.getId()))
            .body(result);
    }

    @PutMapping("/teachers")
    public ResponseEntity<TeacherDTO> updateTeacher(
            @RequestHeader("Authorization") String token,
            @RequestBody TeacherDTO teacherDTO) {
        ResponseEntity<Boolean> isAdmin = authClient.isCurrentUserAdmin(token);
        if (isAdmin == null || isAdmin.getBody() == null || !isAdmin.getBody()) {
            throw new ForbiddenException();
        }

        TeacherDTO result = teacherService.save(teacherDTO);
        teacherProducer.createOrUpdate(result);

        return ResponseEntity.ok()
            .body(result);
    }

    @GetMapping("/teachers")
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.findAll());
    }

    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<Void> deleteTeacher(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        ResponseEntity<Boolean> isAdmin = authClient.isCurrentUserAdmin(token);
        if (isAdmin == null || isAdmin.getBody() == null || !isAdmin.getBody()) {
            throw new ForbiddenException();
        }

        teacherService.delete(id);
        teacherProducer.delete(id);

        return ResponseEntity.noContent().build();
    }
}
