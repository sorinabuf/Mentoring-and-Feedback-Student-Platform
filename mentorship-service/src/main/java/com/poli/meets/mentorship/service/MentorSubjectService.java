package com.poli.meets.mentorship.service;

import com.poli.meets.mentorship.repository.MentorSubjectRepository;
import com.poli.meets.mentorship.domain.MentorSubject;
import com.poli.meets.mentorship.service.dto.MentorSubjectDTO;
import com.poli.meets.mentorship.service.mapper.MentorSubjectMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for managing {@link MentorSubject}.
 */
@Slf4j
@Service
@Transactional
public class MentorSubjectService {

    private final MentorSubjectRepository mentorSubjectRepository;

    private final MentorSubjectMapper mentorSubjectMapper;

    public MentorSubjectService(MentorSubjectRepository mentorSubjectRepository, MentorSubjectMapper mentorSubjectMapper) {
        this.mentorSubjectRepository = mentorSubjectRepository;
        this.mentorSubjectMapper = mentorSubjectMapper;
    }

    /**
     * Save a mentorSubject.
     *
     * @param mentorSubjectDTO the entity to save.
     * @return the persisted entity.
     */
    public MentorSubjectDTO save(MentorSubjectDTO mentorSubjectDTO) {
        log.debug("Request to save MentorSubject : {}", mentorSubjectDTO);
        MentorSubject mentorSubject = mentorSubjectMapper.toEntity(mentorSubjectDTO);
        mentorSubject = mentorSubjectRepository.save(mentorSubject);
        return mentorSubjectMapper.toDto(mentorSubject);
    }

    /**
     * Get all the mentorSubjects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MentorSubjectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MentorSubjects");
        return mentorSubjectRepository.findAll(pageable)
            .map(mentorSubjectMapper::toDto);
    }


    /**
     * Get one mentorSubject by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MentorSubjectDTO> findOne(Long id) {
        log.debug("Request to get MentorSubject : {}", id);
        return mentorSubjectRepository.findById(id)
            .map(mentorSubjectMapper::toDto);
    }

    /**
     * Delete the mentorSubject by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MentorSubject : {}", id);
        mentorSubjectRepository.deleteById(id);
    }
}
