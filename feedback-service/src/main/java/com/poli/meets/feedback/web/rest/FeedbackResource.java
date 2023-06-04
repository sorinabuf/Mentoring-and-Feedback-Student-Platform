package com.poli.meets.feedback.web.rest;

import com.poli.meets.feedback.domain.Feedback;
import com.poli.meets.feedback.service.dto.FeedbackCountDTO;
import com.poli.meets.feedback.service.dto.FeedbackPostDTO;
import com.poli.meets.feedback.service.FeedbackService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing {@link Feedback}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class FeedbackResource {

    private final FeedbackService feedbackService;


    /**
     * {@code POST  /feedbacks} : Create a new feedback.
     *
     * @param feedbackPostDTO the feedbackDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedbackDTO, or with status {@code 400 (Bad Request)} if the feedback has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feedbacks")
    public ResponseEntity<FeedbackPostDTO> createFeedback(@RequestHeader("Authorization") String token, @RequestBody FeedbackPostDTO feedbackPostDTO) throws URISyntaxException {
        log.debug("REST request to save Feedback : {}", feedbackPostDTO);

        FeedbackPostDTO result = feedbackService.save(token, feedbackPostDTO);
        return ResponseEntity.created(new URI("/api/feedbacks/" + result.getId()))
            .body(result);
    }


    /**
     * {@code GET  /feedbacks} : get all the feedbacks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedbacks in body.
     */
    @GetMapping("/feedbacks")
    public List<FeedbackPostDTO> getAllFeedbacks() {
        log.debug("REST request to get all Feedbacks");
        return feedbackService.findAll();
    }

    /**
     * {@code DELETE  /feedbacks/:id} : delete the "id" feedback.
     *
     * @param id the id of the feedbackDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feedbacks/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        log.debug("REST request to delete Feedback : {}", id);
        feedbackService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/feedbacks/count")
    public ResponseEntity<FeedbackCountDTO> getFeedbackCount(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(feedbackService.getFeedbackCount(token));
    }

}
