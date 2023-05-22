package com.poli.meets.mentorship.service;

import com.poli.meets.mentorship.repository.MentorSkillRepository;
import com.poli.meets.mentorship.domain.MentorSkill;
import com.poli.meets.mentorship.service.dto.MentorSkillDTO;
import com.poli.meets.mentorship.service.mapper.MentorSkillMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for managing {@link MentorSkill}.
 */
@Slf4j
@Service
@Transactional
public class MentorSkillService {

    private final MentorSkillRepository mentorSkillRepository;

    private final MentorSkillMapper mentorSkillMapper;

    public MentorSkillService(MentorSkillRepository mentorSkillRepository, MentorSkillMapper mentorSkillMapper) {
        this.mentorSkillRepository = mentorSkillRepository;
        this.mentorSkillMapper = mentorSkillMapper;
    }

    /**
     * Save a mentorSkill.
     *
     * @param mentorSkillDTO the entity to save.
     * @return the persisted entity.
     */
    public MentorSkillDTO save(MentorSkillDTO mentorSkillDTO) {
        log.debug("Request to save MentorSkill : {}", mentorSkillDTO);
        MentorSkill mentorSkill = mentorSkillMapper.toEntity(mentorSkillDTO);
        mentorSkill = mentorSkillRepository.save(mentorSkill);
        return mentorSkillMapper.toDto(mentorSkill);
    }

    /**
     * Get all the mentorSkills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MentorSkillDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MentorSkills");
        return mentorSkillRepository.findAll(pageable)
            .map(mentorSkillMapper::toDto);
    }


    /**
     * Get one mentorSkill by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MentorSkillDTO> findOne(Long id) {
        log.debug("Request to get MentorSkill : {}", id);
        return mentorSkillRepository.findById(id)
            .map(mentorSkillMapper::toDto);
    }

    /**
     * Delete the mentorSkill by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MentorSkill : {}", id);
        mentorSkillRepository.deleteById(id);
    }
}
