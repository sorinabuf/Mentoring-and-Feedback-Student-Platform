package com.poli.meets.mentorship.web.rest;

import com.poli.meets.mentorship.domain.Skill;
import com.poli.meets.mentorship.service.SkillService;
import com.poli.meets.mentorship.service.dto.PagedResponse;
import com.poli.meets.mentorship.service.dto.SkillDTO;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing {@link Skill}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SkillResource {

    private final SkillService skillService;


    /**
     * {@code POST  /skills} : Create a new skill.
     *
     * @param skillDTO the skillDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skillDTO, or with status {@code 400 (Bad Request)} if the skill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/skills")
    public ResponseEntity<SkillDTO> createSkill(@RequestBody SkillDTO skillDTO) throws URISyntaxException {
        log.debug("REST request to save Skill : {}", skillDTO);

        SkillDTO result = skillService.save(skillDTO);
        return ResponseEntity.created(new URI("/api/skills/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /skills} : Updates an existing skill.
     *
     * @param skillDTO the skillDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillDTO,
     * or with status {@code 400 (Bad Request)} if the skillDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skillDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/skills")
    public ResponseEntity<SkillDTO> updateSkill(@RequestBody SkillDTO skillDTO) {
        log.debug("REST request to update Skill : {}", skillDTO);

        SkillDTO result = skillService.save(skillDTO);
        return ResponseEntity.ok()
            .body(result);
    }


    @GetMapping("/skills")
    public ResponseEntity<PagedResponse<SkillDTO>> getAllSkills(Pageable pageable) {
        return ResponseEntity.ok().body(PagedResponse.of(skillService.findAll(pageable)));
    }


    /**
     * {@code DELETE  /skills/:id} : delete the "id" skill.
     *
     * @param id the id of the skillDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/skills/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        log.debug("REST request to delete Skill : {}", id);
        skillService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
