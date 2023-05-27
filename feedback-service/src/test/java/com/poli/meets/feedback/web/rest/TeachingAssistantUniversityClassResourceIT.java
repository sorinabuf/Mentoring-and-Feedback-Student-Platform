package com.poli.meets.feedback.web.rest;

import com.poli.meets.feedback.FeedbackServiceApp;
import com.poli.meets.feedback.service.mapper.TeachingAssistantUniversityClassMapper;
import com.poli.meets.feedback.domain.TeachingAssistantUniversityClass;
import com.poli.meets.feedback.repository.TeachingAssistantUniversityClassRepository;
import com.poli.meets.feedback.service.TeachingAssistantUniversityClassService;
import com.poli.meets.feedback.service.dto.TeachingAssistantUniversityClassDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TeachingAssistantUniversityClassResource} REST controller.
 */
@SpringBootTest(classes = FeedbackServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TeachingAssistantUniversityClassResourceIT {

    @Autowired
    private TeachingAssistantUniversityClassRepository teachingAssistantUniversityClassRepository;

    @Autowired
    private TeachingAssistantUniversityClassMapper teachingAssistantUniversityClassMapper;

    @Autowired
    private TeachingAssistantUniversityClassService teachingAssistantUniversityClassService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeachingAssistantUniversityClassMockMvc;

    private TeachingAssistantUniversityClass teachingAssistantUniversityClass;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeachingAssistantUniversityClass createEntity(EntityManager em) {
        TeachingAssistantUniversityClass teachingAssistantUniversityClass = new TeachingAssistantUniversityClass();
        return teachingAssistantUniversityClass;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeachingAssistantUniversityClass createUpdatedEntity(EntityManager em) {
        TeachingAssistantUniversityClass teachingAssistantUniversityClass = new TeachingAssistantUniversityClass();
        return teachingAssistantUniversityClass;
    }

    @BeforeEach
    public void initTest() {
        teachingAssistantUniversityClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeachingAssistantUniversityClass() throws Exception {
        int databaseSizeBeforeCreate = teachingAssistantUniversityClassRepository.findAll().size();
        // Create the TeachingAssistantUniversityClass
        TeachingAssistantUniversityClassDTO teachingAssistantUniversityClassDTO = teachingAssistantUniversityClassMapper.toDto(teachingAssistantUniversityClass);
        restTeachingAssistantUniversityClassMockMvc.perform(post("/api/teaching-assistant-university-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teachingAssistantUniversityClassDTO)))
            .andExpect(status().isCreated());

        // Validate the TeachingAssistantUniversityClass in the database
        List<TeachingAssistantUniversityClass> teachingAssistantUniversityClassList = teachingAssistantUniversityClassRepository.findAll();
        assertThat(teachingAssistantUniversityClassList).hasSize(databaseSizeBeforeCreate + 1);
        TeachingAssistantUniversityClass testTeachingAssistantUniversityClass = teachingAssistantUniversityClassList.get(teachingAssistantUniversityClassList.size() - 1);
    }

    @Test
    @Transactional
    public void createTeachingAssistantUniversityClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teachingAssistantUniversityClassRepository.findAll().size();

        // Create the TeachingAssistantUniversityClass with an existing ID
        teachingAssistantUniversityClass.setId(1L);
        TeachingAssistantUniversityClassDTO teachingAssistantUniversityClassDTO = teachingAssistantUniversityClassMapper.toDto(teachingAssistantUniversityClass);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeachingAssistantUniversityClassMockMvc.perform(post("/api/teaching-assistant-university-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teachingAssistantUniversityClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TeachingAssistantUniversityClass in the database
        List<TeachingAssistantUniversityClass> teachingAssistantUniversityClassList = teachingAssistantUniversityClassRepository.findAll();
        assertThat(teachingAssistantUniversityClassList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTeachingAssistantUniversityClasses() throws Exception {
        // Initialize the database
        teachingAssistantUniversityClassRepository.saveAndFlush(teachingAssistantUniversityClass);

        // Get all the teachingAssistantUniversityClassList
        restTeachingAssistantUniversityClassMockMvc.perform(get("/api/teaching-assistant-university-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teachingAssistantUniversityClass.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getTeachingAssistantUniversityClass() throws Exception {
        // Initialize the database
        teachingAssistantUniversityClassRepository.saveAndFlush(teachingAssistantUniversityClass);

        // Get the teachingAssistantUniversityClass
        restTeachingAssistantUniversityClassMockMvc.perform(get("/api/teaching-assistant-university-classes/{id}", teachingAssistantUniversityClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teachingAssistantUniversityClass.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTeachingAssistantUniversityClass() throws Exception {
        // Get the teachingAssistantUniversityClass
        restTeachingAssistantUniversityClassMockMvc.perform(get("/api/teaching-assistant-university-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeachingAssistantUniversityClass() throws Exception {
        // Initialize the database
        teachingAssistantUniversityClassRepository.saveAndFlush(teachingAssistantUniversityClass);

        int databaseSizeBeforeUpdate = teachingAssistantUniversityClassRepository.findAll().size();

        // Update the teachingAssistantUniversityClass
        TeachingAssistantUniversityClass updatedTeachingAssistantUniversityClass = teachingAssistantUniversityClassRepository.findById(teachingAssistantUniversityClass.getId()).get();
        // Disconnect from session so that the updates on updatedTeachingAssistantUniversityClass are not directly saved in db
        em.detach(updatedTeachingAssistantUniversityClass);
        TeachingAssistantUniversityClassDTO teachingAssistantUniversityClassDTO = teachingAssistantUniversityClassMapper.toDto(updatedTeachingAssistantUniversityClass);

        restTeachingAssistantUniversityClassMockMvc.perform(put("/api/teaching-assistant-university-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teachingAssistantUniversityClassDTO)))
            .andExpect(status().isOk());

        // Validate the TeachingAssistantUniversityClass in the database
        List<TeachingAssistantUniversityClass> teachingAssistantUniversityClassList = teachingAssistantUniversityClassRepository.findAll();
        assertThat(teachingAssistantUniversityClassList).hasSize(databaseSizeBeforeUpdate);
        TeachingAssistantUniversityClass testTeachingAssistantUniversityClass = teachingAssistantUniversityClassList.get(teachingAssistantUniversityClassList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingTeachingAssistantUniversityClass() throws Exception {
        int databaseSizeBeforeUpdate = teachingAssistantUniversityClassRepository.findAll().size();

        // Create the TeachingAssistantUniversityClass
        TeachingAssistantUniversityClassDTO teachingAssistantUniversityClassDTO = teachingAssistantUniversityClassMapper.toDto(teachingAssistantUniversityClass);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeachingAssistantUniversityClassMockMvc.perform(put("/api/teaching-assistant-university-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teachingAssistantUniversityClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TeachingAssistantUniversityClass in the database
        List<TeachingAssistantUniversityClass> teachingAssistantUniversityClassList = teachingAssistantUniversityClassRepository.findAll();
        assertThat(teachingAssistantUniversityClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTeachingAssistantUniversityClass() throws Exception {
        // Initialize the database
        teachingAssistantUniversityClassRepository.saveAndFlush(teachingAssistantUniversityClass);

        int databaseSizeBeforeDelete = teachingAssistantUniversityClassRepository.findAll().size();

        // Delete the teachingAssistantUniversityClass
        restTeachingAssistantUniversityClassMockMvc.perform(delete("/api/teaching-assistant-university-classes/{id}", teachingAssistantUniversityClass.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TeachingAssistantUniversityClass> teachingAssistantUniversityClassList = teachingAssistantUniversityClassRepository.findAll();
        assertThat(teachingAssistantUniversityClassList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
