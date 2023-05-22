package com.poli.meets.mentorship.web.rest;

import com.poli.meets.mentorship.domain.MeetingRequest;
import com.poli.meets.mentorship.service.dto.MeetingRequestDTO;
import com.poli.meets.mentorship.service.MeetingRequestService;

import com.poli.meets.mentorship.service.dto.PagedResponse;
import com.poli.meets.mentorship.web.rest.errors.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing {@link MeetingRequest}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MeetingRequestResource {


    private final MeetingRequestService meetingRequestService;


    /**
     * {@code POST  /meeting-requests} : Create a new meetingRequest.
     *
     * @param meetingRequestDTO the meetingRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meetingRequestDTO, or with status {@code 400 (Bad Request)} if the meetingRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/meeting-requests")
    public ResponseEntity<MeetingRequestDTO> createMeetingRequest(@RequestBody MeetingRequestDTO meetingRequestDTO) throws URISyntaxException {
        log.debug("REST request to save MeetingRequest : {}", meetingRequestDTO);

        MeetingRequestDTO result = meetingRequestService.save(meetingRequestDTO);
        return ResponseEntity.created(new URI("/api/meeting-requests/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /meeting-requests} : Updates an existing meetingRequest.
     *
     * @param meetingRequestDTO the meetingRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meetingRequestDTO,
     * or with status {@code 400 (Bad Request)} if the meetingRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meetingRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/meeting-requests")
    public ResponseEntity<MeetingRequestDTO> updateMeetingRequest(@RequestBody MeetingRequestDTO meetingRequestDTO) throws URISyntaxException {
        log.debug("REST request to update MeetingRequest : {}", meetingRequestDTO);

        MeetingRequestDTO result = meetingRequestService.save(meetingRequestDTO);
        return ResponseEntity.ok()
            .body(result);
    }


    @GetMapping("/meeting-requests")
    public ResponseEntity<PagedResponse<MeetingRequestDTO>> getAllMeetingRequests( Pageable pageable) {

        return ResponseEntity.ok().body(PagedResponse.of(meetingRequestService.findAll(pageable)));
    }



    /**
     * {@code GET  /meeting-requests/:id} : get the "id" meetingRequest.
     *
     * @param id the id of the meetingRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meetingRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/meeting-requests/{id}")
    public ResponseEntity<MeetingRequestDTO> getMeetingRequest(@PathVariable Long id) {
        log.debug("REST request to get MeetingRequest : {}", id);
        MeetingRequestDTO meetingRequestDTO = meetingRequestService.findOne(id).orElseThrow(BadRequestException::new);
        return ResponseEntity.ok(meetingRequestDTO);
    }

    /**
     * {@code DELETE  /meeting-requests/:id} : delete the "id" meetingRequest.
     *
     * @param id the id of the meetingRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/meeting-requests/{id}")
    public ResponseEntity<Void> deleteMeetingRequest(@PathVariable Long id) {
        log.debug("REST request to delete MeetingRequest : {}", id);
        meetingRequestService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
