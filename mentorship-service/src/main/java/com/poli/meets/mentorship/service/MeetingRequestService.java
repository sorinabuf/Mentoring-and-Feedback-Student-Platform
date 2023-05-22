package com.poli.meets.mentorship.service;

import com.poli.meets.mentorship.repository.MeetingRequestRepository;
import com.poli.meets.mentorship.service.dto.MeetingRequestDTO;
import com.poli.meets.mentorship.service.mapper.MeetingRequestMapper;
import com.poli.meets.mentorship.domain.MeetingRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for managing {@link MeetingRequest}.
 */
@Slf4j
@Service
@Transactional
public class MeetingRequestService {

    private final MeetingRequestRepository meetingRequestRepository;

    private final MeetingRequestMapper meetingRequestMapper;

    public MeetingRequestService(MeetingRequestRepository meetingRequestRepository, MeetingRequestMapper meetingRequestMapper) {
        this.meetingRequestRepository = meetingRequestRepository;
        this.meetingRequestMapper = meetingRequestMapper;
    }

    /**
     * Save a meetingRequest.
     *
     * @param meetingRequestDTO the entity to save.
     * @return the persisted entity.
     */
    public MeetingRequestDTO save(MeetingRequestDTO meetingRequestDTO) {
        log.debug("Request to save MeetingRequest : {}", meetingRequestDTO);
        MeetingRequest meetingRequest = meetingRequestMapper.toEntity(meetingRequestDTO);
        meetingRequest = meetingRequestRepository.save(meetingRequest);
        return meetingRequestMapper.toDto(meetingRequest);
    }

    /**
     * Get all the meetingRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MeetingRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MeetingRequests");
        return meetingRequestRepository.findAll(pageable)
            .map(meetingRequestMapper::toDto);
    }


    /**
     * Get one meetingRequest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MeetingRequestDTO> findOne(Long id) {
        log.debug("Request to get MeetingRequest : {}", id);
        return meetingRequestRepository.findById(id)
            .map(meetingRequestMapper::toDto);
    }

    /**
     * Delete the meetingRequest by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MeetingRequest : {}", id);
        meetingRequestRepository.deleteById(id);
    }
}
