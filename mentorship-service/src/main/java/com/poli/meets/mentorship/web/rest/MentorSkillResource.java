package com.poli.meets.mentorship.web.rest;

import com.poli.meets.mentorship.domain.MentorSkill;
import com.poli.meets.mentorship.service.MentorSkillService;
import com.poli.meets.mentorship.service.dto.MentorSkillDTO;

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
 * REST controller for managing {@link MentorSkill}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MentorSkillResource {

    private final MentorSkillService mentorSkillService;

    /**
     * {@code POST  /mentor-skills} : Create a new mentorSkill.
     *
     * @param mentorSkillDTO the mentorSkillDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mentorSkillDTO, or with status {@code 400 (Bad Request)} if the mentorSkill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mentor-skills")
    public ResponseEntity<MentorSkillDTO> createMentorSkill(@RequestBody MentorSkillDTO mentorSkillDTO) throws URISyntaxException {
        log.debug("REST request to save MentorSkill : {}", mentorSkillDTO);

        MentorSkillDTO result = mentorSkillService.save(mentorSkillDTO);
        return ResponseEntity.created(new URI("/api/mentor-skills/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /mentor-skills} : Updates an existing mentorSkill.
     *
     * @param mentorSkillDTO the mentorSkillDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mentorSkillDTO,
     * or with status {@code 400 (Bad Request)} if the mentorSkillDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mentorSkillDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mentor-skills")
    public ResponseEntity<MentorSkillDTO> updateMentorSkill(@RequestBody MentorSkillDTO mentorSkillDTO) throws URISyntaxException {
        log.debug("REST request to update MentorSkill : {}", mentorSkillDTO);

        MentorSkillDTO result = mentorSkillService.save(mentorSkillDTO);
        return ResponseEntity.ok()
            .body(result);
    }


    @GetMapping("/mentor-skills")
    public ResponseEntity<PagedResponse<MentorSkillDTO>> getAllMentorSkills(Pageable pageable) {
        return ResponseEntity.ok().body(PagedResponse.of(mentorSkillService.findAll(pageable)));
    }


    /**
     * {@code DELETE  /mentor-skills/:id} : delete the "id" mentorSkill.
     *
     * @param id the id of the mentorSkillDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mentor-skills/{id}")
    public ResponseEntity<Void> deleteMentorSkill(@PathVariable Long id) {
        log.debug("REST request to delete MentorSkill : {}", id);
        mentorSkillService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
