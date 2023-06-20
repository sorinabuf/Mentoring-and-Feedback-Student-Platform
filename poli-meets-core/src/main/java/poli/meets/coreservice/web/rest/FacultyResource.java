package poli.meets.coreservice.web.rest;

import lombok.AllArgsConstructor;
import poli.meets.coreservice.client.AuthClient;
import poli.meets.coreservice.producer.FacultyProducer;
import poli.meets.coreservice.service.FacultyService;
import poli.meets.coreservice.service.dto.FacultyDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import poli.meets.coreservice.web.rest.errors.ForbiddenException;

/**
 * REST controller for managing {@link poli.meets.coreservice.domain.Faculty}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class FacultyResource {

    private final FacultyService facultyService;

    private final AuthClient authClient;

    private final FacultyProducer facultyProducer;

    @PostMapping("/faculties")
    public ResponseEntity<FacultyDTO> createFaculty(
            @RequestHeader("Authorization") String token,
            @RequestBody FacultyDTO facultyDTO) throws URISyntaxException {
        ResponseEntity<Boolean> isAdmin = authClient.isCurrentUserAdmin(token);
        if (isAdmin == null || isAdmin.getBody() == null || !isAdmin.getBody()) {
            throw new ForbiddenException();
        }

        FacultyDTO result = facultyService.save(facultyDTO);

        facultyProducer.createOrUpdate(result);
        return ResponseEntity.created(new URI("/api/faculties/" + result.getId()))
                .body(result);
    }

    @PutMapping("/faculties")
    public ResponseEntity<FacultyDTO> updateFaculty(
            @RequestHeader("Authorization") String token,
            @RequestBody FacultyDTO facultyDTO) {
        ResponseEntity<Boolean> isAdmin = authClient.isCurrentUserAdmin(token);
        if (isAdmin == null || isAdmin.getBody() == null || !isAdmin.getBody()) {
            throw new ForbiddenException();
        }

        FacultyDTO result = facultyService.save(facultyDTO);
        facultyProducer.createOrUpdate(result);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/faculties")
    public List<FacultyDTO> getAllFaculties() {
        return facultyService.findAll();
    }

    @DeleteMapping("/faculties/{id}")
    public ResponseEntity<Void> deleteFaculty(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        ResponseEntity<Boolean> isAdmin = authClient.isCurrentUserAdmin(token);
        if (isAdmin == null || isAdmin.getBody() == null || !isAdmin.getBody()) {
            throw new ForbiddenException();
        }

        facultyService.delete(id);
        facultyProducer.delete(id);

        return ResponseEntity.noContent().build();
    }
}
