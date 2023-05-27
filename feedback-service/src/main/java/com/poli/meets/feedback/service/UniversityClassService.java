package com.poli.meets.feedback.service;

import com.poli.meets.feedback.repository.UniversityClassRepository;
import com.poli.meets.feedback.service.dto.UniversityClassDTO;
import com.poli.meets.feedback.service.mapper.UniversityClassMapper;
import com.poli.meets.feedback.domain.UniversityClass;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UniversityClassDTO> findAll() {
        log.debug("Request to get all UniversityClasses");
        return universityClassRepository.findAll().stream()
            .map(universityClassMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
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
