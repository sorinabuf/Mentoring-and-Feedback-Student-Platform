package com.poli.meets.mentorship.service;

import com.poli.meets.mentorship.repository.FacultyRepository;
import com.poli.meets.mentorship.service.dto.FacultyDTO;
import com.poli.meets.mentorship.service.mapper.FacultyMapper;
import com.poli.meets.mentorship.domain.Faculty;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for managing {@link Faculty}.
 */
@Slf4j
@Service
@Transactional
public class FacultyService {

    private final FacultyRepository facultyRepository;

    private final FacultyMapper facultyMapper;

    public FacultyService(FacultyRepository facultyRepository, FacultyMapper facultyMapper) {
        this.facultyRepository = facultyRepository;
        this.facultyMapper = facultyMapper;
    }

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
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FacultyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Faculties");
        return facultyRepository.findAll(pageable)
            .map(facultyMapper::toDto);
    }


    /**
     * Get one faculty by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FacultyDTO> findOne(Long id) {
        log.debug("Request to get Faculty : {}", id);
        return facultyRepository.findById(id)
            .map(facultyMapper::toDto);
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
