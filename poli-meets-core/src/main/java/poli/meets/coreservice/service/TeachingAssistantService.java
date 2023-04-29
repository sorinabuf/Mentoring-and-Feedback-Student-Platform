package poli.meets.coreservice.service;

import lombok.AllArgsConstructor;
import poli.meets.coreservice.domain.TeachingAssistant;
import poli.meets.coreservice.repository.TeachingAssistantRepository;
import poli.meets.coreservice.service.dto.TeachingAssistantDTO;
import poli.meets.coreservice.service.mapper.TeachingAssistantMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for managing {@link TeachingAssistant}.
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class TeachingAssistantService {

    private final TeachingAssistantRepository teachingAssistantRepository;

    private final TeachingAssistantMapper teachingAssistantMapper;


    /**
     * Save a teachingAssistant.
     *
     * @param teachingAssistantDTO the entity to save.
     * @return the persisted entity.
     */
    public TeachingAssistantDTO save(TeachingAssistantDTO teachingAssistantDTO) {
        log.debug("Request to save TeachingAssistant : {}", teachingAssistantDTO);
        TeachingAssistant teachingAssistant = teachingAssistantMapper.toEntity(teachingAssistantDTO);
        teachingAssistant = teachingAssistantRepository.save(teachingAssistant);
        return teachingAssistantMapper.toDto(teachingAssistant);
    }

    /**
     * Get all the teachingAssistants.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TeachingAssistantDTO> findAll() {
        log.debug("Request to get all TeachingAssistants");
        return teachingAssistantRepository.findAll().stream()
            .map(teachingAssistantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one teachingAssistant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TeachingAssistantDTO> findOne(Long id) {
        log.debug("Request to get TeachingAssistant : {}", id);
        return teachingAssistantRepository.findById(id)
            .map(teachingAssistantMapper::toDto);
    }

    /**
     * Delete the teachingAssistant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TeachingAssistant : {}", id);
        teachingAssistantRepository.deleteById(id);
    }
}
