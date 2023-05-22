package com.poli.meets.mentorship.service;

import com.poli.meets.mentorship.repository.UniversityYearRepository;
import com.poli.meets.mentorship.domain.UniversityYear;
import com.poli.meets.mentorship.service.dto.UniversityYearDTO;
import com.poli.meets.mentorship.service.mapper.UniversityYearMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for managing {@link UniversityYear}.
 */
@Slf4j
@Service
@Transactional
public class UniversityYearService {

    private final UniversityYearRepository universityYearRepository;

    private final UniversityYearMapper universityYearMapper;

    public UniversityYearService(UniversityYearRepository universityYearRepository, UniversityYearMapper universityYearMapper) {
        this.universityYearRepository = universityYearRepository;
        this.universityYearMapper = universityYearMapper;
    }

    /**
     * Save a universityYear.
     *
     * @param universityYearDTO the entity to save.
     * @return the persisted entity.
     */
    public UniversityYearDTO save(UniversityYearDTO universityYearDTO) {
        log.debug("Request to save UniversityYear : {}", universityYearDTO);
        UniversityYear universityYear = universityYearMapper.toEntity(universityYearDTO);
        universityYear = universityYearRepository.save(universityYear);
        return universityYearMapper.toDto(universityYear);
    }

    /**
     * Get all the universityYears.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UniversityYearDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UniversityYears");
        return universityYearRepository.findAll(pageable)
            .map(universityYearMapper::toDto);
    }


    /**
     * Get one universityYear by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UniversityYearDTO> findOne(Long id) {
        log.debug("Request to get UniversityYear : {}", id);
        return universityYearRepository.findById(id)
            .map(universityYearMapper::toDto);
    }

    /**
     * Delete the universityYear by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UniversityYear : {}", id);
        universityYearRepository.deleteById(id);
    }
}
