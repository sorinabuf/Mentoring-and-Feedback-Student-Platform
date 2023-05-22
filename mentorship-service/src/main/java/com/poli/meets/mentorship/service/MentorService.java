package com.poli.meets.mentorship.service;

import com.poli.meets.mentorship.repository.MentorRepository;
import com.poli.meets.mentorship.domain.Mentor;
import com.poli.meets.mentorship.service.dto.MentorDTO;
import com.poli.meets.mentorship.service.mapper.MentorMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for managing {@link Mentor}.
 */
@Slf4j
@Service
@Transactional
public class MentorService {

    private final MentorRepository mentorRepository;

    private final MentorMapper mentorMapper;

    public MentorService(MentorRepository mentorRepository, MentorMapper mentorMapper) {
        this.mentorRepository = mentorRepository;
        this.mentorMapper = mentorMapper;
    }

    /**
     * Save a mentor.
     *
     * @param mentorDTO the entity to save.
     * @return the persisted entity.
     */
    public MentorDTO save(MentorDTO mentorDTO) {
        log.debug("Request to save Mentor : {}", mentorDTO);
        Mentor mentor = mentorMapper.toEntity(mentorDTO);
        mentor = mentorRepository.save(mentor);
        return mentorMapper.toDto(mentor);
    }

    /**
     * Get all the mentors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MentorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Mentors");
        return mentorRepository.findAll(pageable)
            .map(mentorMapper::toDto);
    }


    /**
     * Get one mentor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MentorDTO> findOne(Long id) {
        log.debug("Request to get Mentor : {}", id);
        return mentorRepository.findById(id)
            .map(mentorMapper::toDto);
    }

    /**
     * Delete the mentor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Mentor : {}", id);
        mentorRepository.deleteById(id);
    }
}
