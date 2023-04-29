package poli.meets.coreservice.service;

import lombok.AllArgsConstructor;
import poli.meets.coreservice.domain.TeachingAssistantUniversityClass;
import poli.meets.coreservice.repository.TeachingAssistantUniversityClassRepository;
import poli.meets.coreservice.service.dto.TeachingAssistantUniversityClassDTO;
import poli.meets.coreservice.service.mapper.TeachingAssistantUniversityClassMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for managing {@link TeachingAssistantUniversityClass}.
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class TeachingAssistantUniversityClassService {

    private final TeachingAssistantUniversityClassRepository teachingAssistantUniversityClassRepository;

    private final TeachingAssistantUniversityClassMapper teachingAssistantUniversityClassMapper;


    /**
     * Save a teachingAssistantUniversityClass.
     *
     * @param teachingAssistantUniversityClassDTO the entity to save.
     * @return the persisted entity.
     */
    public TeachingAssistantUniversityClassDTO save(TeachingAssistantUniversityClassDTO teachingAssistantUniversityClassDTO) {
        log.debug("Request to save TeachingAssistantUniversityClass : {}", teachingAssistantUniversityClassDTO);
        TeachingAssistantUniversityClass teachingAssistantUniversityClass = teachingAssistantUniversityClassMapper.toEntity(teachingAssistantUniversityClassDTO);
        teachingAssistantUniversityClass = teachingAssistantUniversityClassRepository.save(teachingAssistantUniversityClass);
        return teachingAssistantUniversityClassMapper.toDto(teachingAssistantUniversityClass);
    }

    /**
     * Get all the teachingAssistantUniversityClasses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TeachingAssistantUniversityClassDTO> findAll() {
        log.debug("Request to get all TeachingAssistantUniversityClasses");
        return teachingAssistantUniversityClassRepository.findAll().stream()
            .map(teachingAssistantUniversityClassMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one teachingAssistantUniversityClass by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TeachingAssistantUniversityClassDTO> findOne(Long id) {
        log.debug("Request to get TeachingAssistantUniversityClass : {}", id);
        return teachingAssistantUniversityClassRepository.findById(id)
            .map(teachingAssistantUniversityClassMapper::toDto);
    }

    /**
     * Delete the teachingAssistantUniversityClass by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TeachingAssistantUniversityClass : {}", id);
        teachingAssistantUniversityClassRepository.deleteById(id);
    }
}
