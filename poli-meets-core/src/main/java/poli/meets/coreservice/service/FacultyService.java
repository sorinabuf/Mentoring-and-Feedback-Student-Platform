package poli.meets.coreservice.service;

import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import poli.meets.coreservice.domain.Faculty;
import poli.meets.coreservice.repository.FacultyRepository;
import poli.meets.coreservice.service.dto.FacultyDTO;
import poli.meets.coreservice.service.mapper.FacultyMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for managing {@link Faculty}.
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class FacultyService {

    private final FacultyRepository facultyRepository;

    private final FacultyMapper facultyMapper;

    /**
     * Save a faculty.
     *
     * @param facultyDTO the entity to save.
     * @return the persisted entity.
     */
    public FacultyDTO save(FacultyDTO facultyDTO) {
        log.debug("Request to save Faculty : {}", facultyDTO);
        Faculty faculty = facultyMapper.toEntity(facultyDTO);
        faculty = facultyRepository.save(faculty);
        return facultyMapper.toDto(faculty);
    }

    /**
     * Get all the faculties.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FacultyDTO> findAll() {
        log.debug("Request to get all Faculties");
        return facultyRepository.findAll().stream()
            .map(facultyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one faculty by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public FacultyDTO findOne(Long id) {
        log.debug("Request to get Faculty : {}", id);
        return facultyRepository.findById(id)
            .map(facultyMapper::toDto)
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Delete the faculty by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Faculty : {}", id);
        facultyRepository.deleteById(id);
    }
}
