package com.poli.meets.mentorship.service;

import com.poli.meets.mentorship.repository.MeetingSlotRepository;
import com.poli.meets.mentorship.service.dto.MeetingSlotDTO;
import com.poli.meets.mentorship.service.mapper.MeetingSlotMapper;
import com.poli.meets.mentorship.domain.MeetingSlot;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for managing {@link MeetingSlot}.
 */
@Slf4j
@Service
@Transactional
public class MeetingSlotService {

    private final MeetingSlotRepository meetingSlotRepository;

    private final MeetingSlotMapper meetingSlotMapper;

    public MeetingSlotService(MeetingSlotRepository meetingSlotRepository, MeetingSlotMapper meetingSlotMapper) {
        this.meetingSlotRepository = meetingSlotRepository;
        this.meetingSlotMapper = meetingSlotMapper;
    }

    /**
     * Save a meetingSlot.
     *
     * @param meetingSlotDTO the entity to save.
     * @return the persisted entity.
     */
    public MeetingSlotDTO save(MeetingSlotDTO meetingSlotDTO) {
        log.debug("Request to save MeetingSlot : {}", meetingSlotDTO);
        MeetingSlot meetingSlot = meetingSlotMapper.toEntity(meetingSlotDTO);
        meetingSlot = meetingSlotRepository.save(meetingSlot);
        return meetingSlotMapper.toDto(meetingSlot);
    }

    /**
     * Get all the meetingSlots.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MeetingSlotDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MeetingSlots");
        return meetingSlotRepository.findAll(pageable)
            .map(meetingSlotMapper::toDto);
    }


    /**
     * Get one meetingSlot by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MeetingSlotDTO> findOne(Long id) {
        log.debug("Request to get MeetingSlot : {}", id);
        return meetingSlotRepository.findById(id)
            .map(meetingSlotMapper::toDto);
    }

    /**
     * Delete the meetingSlot by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MeetingSlot : {}", id);
        meetingSlotRepository.deleteById(id);
    }
}
