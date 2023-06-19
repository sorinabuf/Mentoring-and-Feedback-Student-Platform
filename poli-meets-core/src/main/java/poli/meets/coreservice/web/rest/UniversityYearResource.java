package poli.meets.coreservice.web.rest;

import lombok.AllArgsConstructor;
import poli.meets.coreservice.client.AuthClient;
import poli.meets.coreservice.domain.enumeration.Year;
import poli.meets.coreservice.service.UniversityYearService;
import poli.meets.coreservice.service.dto.UniversityYearDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import poli.meets.coreservice.web.rest.errors.ForbiddenException;

/**
 * REST controller for managing {@link poli.meets.coreservice.domain.UniversityYear}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UniversityYearResource {

    private final UniversityYearService universityYearService;

    private final AuthClient authClient;

    @PostMapping("/university-years")
    public ResponseEntity<UniversityYearDTO> createUniversityYear(
            @RequestHeader("Authorization") String token,
            @RequestBody UniversityYearDTO universityYearDTO) throws URISyntaxException {
        ResponseEntity<Boolean> isAdmin = authClient.isCurrentUserAdmin(token);
        if (isAdmin == null || isAdmin.getBody() == null || !isAdmin.getBody()) {
            throw new ForbiddenException();
        }

        UniversityYearDTO result = universityYearService.save(universityYearDTO);
        return ResponseEntity.created(new URI("/api/university-years/" + result.getId()))
            .body(result);
    }

    @PutMapping("/university-years")
    public ResponseEntity<UniversityYearDTO> updateUniversityYear(
            @RequestHeader("Authorization") String token,
            @RequestBody UniversityYearDTO universityYearDTO) {
        ResponseEntity<Boolean> isAdmin = authClient.isCurrentUserAdmin(token);
        if (isAdmin == null || isAdmin.getBody() == null || !isAdmin.getBody()) {
            throw new ForbiddenException();
        }

        UniversityYearDTO result = universityYearService.save(universityYearDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/university-years")
    public List<UniversityYearDTO> getAllUniversityYears() {
        return universityYearService.findAll();
    }

    @DeleteMapping("/university-years/{id}")
    public ResponseEntity<Void> deleteUniversityYear(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        ResponseEntity<Boolean> isAdmin = authClient.isCurrentUserAdmin(token);
        if (isAdmin == null || isAdmin.getBody() == null || !isAdmin.getBody()) {
            throw new ForbiddenException();
        }

        universityYearService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/university-years/series")
    public ResponseEntity<List<String>> getSeries(
            @RequestParam Long facultyId,
            @RequestParam Year year) {
        return ResponseEntity.ok(universityYearService.findAllSeriesFromFacultyInYear(facultyId, year));
    }
}
