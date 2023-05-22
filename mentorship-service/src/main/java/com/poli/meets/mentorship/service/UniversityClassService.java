package com.poli.meets.mentorship.service;

import com.poli.meets.mentorship.repository.UniversityClassRepository;
import com.poli.meets.mentorship.domain.UniversityClass;
import com.poli.meets.mentorship.service.dto.UniversityClassDTO;
import com.poli.meets.mentorship.service.mapper.UniversityClassMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for managing {@link UniversityClass}.
 */
@Slf4j
@Service
@Transactional
public class UniversityClassService {

    private final UniversityClassRepository universityClassRepository;

    private final UniversityClassMapper universityClassMapper;

    public UniversityClassService(UniversityClassRepository universityClassRepository, UniversityClassMapper universityClassMapper) {
        this.universityClassRepository = universityClassRepository;
        this.universityClassMapper = universityClassMapper;
    }

    /**
     * Save a universityClass.
     *
     * @param universityClassDTO the entity to save.
     * @return the persisted entity.
     */
    public UniversityClassDTO save(UniversityClassDTO universityClassDTO) {
        log.debug("Request to save UniversityClass : {}", universityClassDTO);
        UniversityClass universityClass = universityClassMapper.toEntity(universityClassDTO);
        universityClass = universityClassRepository.save(universityClass);
        return universityClassMapper.toDto(universityClass);
    }

    /**
     * Get all the universityClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UniversityClassDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UniversityClasses");
        return universityClassRepository.findAll(pageable)
            .map(universityClassMapper::toDto);
    }


    /**
     * Get one universityClass by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UniversityClassDTO> findOne(Long id) {
        log.debug("Request to get UniversityClass : {}", id);
        return universityClassRepository.findById(id)
            .map(universityClassMapper::toDto);
    }

    /**
     * Delete the universityClass by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UniversityClass : {}", id);
        universityClassRepository.deleteById(id);
    }
}
