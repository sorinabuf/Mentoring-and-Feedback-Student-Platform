package poli.meets.coreservice.service;

import lombok.AllArgsConstructor;
import poli.meets.coreservice.domain.UniversityYear;
import poli.meets.coreservice.repository.UniversityYearRepository;
import poli.meets.coreservice.service.dto.UniversityYearDTO;
import poli.meets.coreservice.service.mapper.UniversityYearMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for managing {@link UniversityYear}.
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class UniversityYearService {

    private final UniversityYearRepository universityYearRepository;

    private final UniversityYearMapper universityYearMapper;


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
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UniversityYearDTO> findAll() {
        log.debug("Request to get all UniversityYears");
        return universityYearRepository.findAll().stream()
            .map(universityYearMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
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
