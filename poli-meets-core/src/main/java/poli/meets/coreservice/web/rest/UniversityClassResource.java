package poli.meets.coreservice.web.rest;

import lombok.AllArgsConstructor;
import poli.meets.coreservice.client.AuthClient;
import poli.meets.coreservice.producer.UniversityClassProducer;
import poli.meets.coreservice.service.UniversityClassService;
import poli.meets.coreservice.service.dto.UniversityClassDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import poli.meets.coreservice.web.rest.errors.ForbiddenException;

/**
 * REST controller for managing {@link poli.meets.coreservice.domain.UniversityClass}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UniversityClassResource {

    private final UniversityClassService universityClassService;

    private final AuthClient authClient;

    private final UniversityClassProducer universityClassProducer;

    @PostMapping("/university-classes")
    public ResponseEntity<UniversityClassDTO> createUniversityClass(
            @RequestHeader("Authorization") String token,
            @RequestBody UniversityClassDTO universityClassDTO) throws URISyntaxException {
        ResponseEntity<Boolean> isAdmin = authClient.isCurrentUserAdmin(token);
        if (isAdmin == null || isAdmin.getBody() == null || !isAdmin.getBody()) {
            throw new ForbiddenException();
        }

        UniversityClassDTO result = universityClassService.save(universityClassDTO);
        universityClassProducer.createOrUpdate(result);

        return ResponseEntity.created(new URI("/api/university-classes/" + result.getId()))
            .body(result);
    }

    @PutMapping("/university-classes")
    public ResponseEntity<UniversityClassDTO> updateUniversityClass(
            @RequestHeader("Authorization") String token,
            @RequestBody UniversityClassDTO universityClassDTO) {
        ResponseEntity<Boolean> isAdmin = authClient.isCurrentUserAdmin(token);
        if (isAdmin == null || isAdmin.getBody() == null || !isAdmin.getBody()) {
            throw new ForbiddenException();
        }

        UniversityClassDTO result = universityClassService.save(universityClassDTO);
        universityClassProducer.createOrUpdate(result);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/university-classes")
    public List<UniversityClassDTO> getAllUniversityClasses() {
        return universityClassService.findAll();
    }


    @DeleteMapping("/university-classes/{id}")
    public ResponseEntity<Void> deleteUniversityClass(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        ResponseEntity<Boolean> isAdmin = authClient.isCurrentUserAdmin(token);
        if (isAdmin == null || isAdmin.getBody() == null || !isAdmin.getBody()) {
            throw new ForbiddenException();
        }

        universityClassService.delete(id);
        universityClassProducer.delete(id);

        return ResponseEntity.noContent().build();
    }
}
