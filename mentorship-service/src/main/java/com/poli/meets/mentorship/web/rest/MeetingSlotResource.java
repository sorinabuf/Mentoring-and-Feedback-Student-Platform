package com.poli.meets.mentorship.web.rest;

import com.poli.meets.mentorship.domain.MeetingSlot;
import com.poli.meets.mentorship.service.dto.MeetingSlotDTO;
import com.poli.meets.mentorship.service.MeetingSlotService;

import com.poli.meets.mentorship.service.dto.PagedResponse;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing {@link MeetingSlot}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MeetingSlotResource {


    private final MeetingSlotService meetingSlotService;


    /**
     * {@code POST  /meeting-slots} : Create a new meetingSlot.
     *
     * @param meetingSlotDTO the meetingSlotDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meetingSlotDTO, or with status {@code 400 (Bad Request)} if the meetingSlot has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/meeting-slots")
    public ResponseEntity<MeetingSlotDTO> createMeetingSlot(@RequestBody MeetingSlotDTO meetingSlotDTO) throws URISyntaxException {
        log.debug("REST request to save MeetingSlot : {}", meetingSlotDTO);

        MeetingSlotDTO result = meetingSlotService.save(meetingSlotDTO);
        return ResponseEntity.created(new URI("/api/meeting-slots/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /meeting-slots} : Updates an existing meetingSlot.
     *
     * @param meetingSlotDTO the meetingSlotDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meetingSlotDTO,
     * or with status {@code 400 (Bad Request)} if the meetingSlotDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meetingSlotDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/meeting-slots")
    public ResponseEntity<MeetingSlotDTO> updateMeetingSlot(@RequestBody MeetingSlotDTO meetingSlotDTO) throws URISyntaxException {
        log.debug("REST request to update MeetingSlot : {}", meetingSlotDTO);

        MeetingSlotDTO result = meetingSlotService.save(meetingSlotDTO);
        return ResponseEntity.ok()
            .body(result);
    }


    @GetMapping("/meeting-slots")
    public ResponseEntity<PagedResponse<MeetingSlotDTO>> getAllMeetingSlots(Pageable pageable) {

        return ResponseEntity.ok().body(PagedResponse.of(meetingSlotService.findAll(pageable)));
    }


    /**
     * {@code DELETE  /meeting-slots/:id} : delete the "id" meetingSlot.
     *
     * @param id the id of the meetingSlotDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/meeting-slots/{id}")
    public ResponseEntity<Void> deleteMeetingSlot(@PathVariable Long id) {
        log.debug("REST request to delete MeetingSlot : {}", id);
        meetingSlotService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
