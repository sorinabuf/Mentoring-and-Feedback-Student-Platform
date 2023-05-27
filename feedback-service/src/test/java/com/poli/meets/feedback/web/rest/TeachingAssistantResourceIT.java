package com.poli.meets.feedback.web.rest;

import com.poli.meets.feedback.FeedbackServiceApp;
import com.poli.meets.feedback.domain.TeachingAssistant;
import com.poli.meets.feedback.repository.TeachingAssistantRepository;
import com.poli.meets.feedback.service.TeachingAssistantService;
import com.poli.meets.feedback.service.dto.TeachingAssistantDTO;
import com.poli.meets.feedback.service.mapper.TeachingAssistantMapper;

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
 * Integration tests for the {@link TeachingAssistantResource} REST controller.
 */
@SpringBootTest(classes = FeedbackServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TeachingAssistantResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    @Autowired
    private TeachingAssistantRepository teachingAssistantRepository;

    @Autowired
    private TeachingAssistantMapper teachingAssistantMapper;

    @Autowired
    private TeachingAssistantService teachingAssistantService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeachingAssistantMockMvc;

    private TeachingAssistant teachingAssistant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeachingAssistant createEntity(EntityManager em) {
        TeachingAssistant teachingAssistant = new TeachingAssistant()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME);
        return teachingAssistant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeachingAssistant createUpdatedEntity(EntityManager em) {
        TeachingAssistant teachingAssistant = new TeachingAssistant()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);
        return teachingAssistant;
    }

    @BeforeEach
    public void initTest() {
        teachingAssistant = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeachingAssistant() throws Exception {
        int databaseSizeBeforeCreate = teachingAssistantRepository.findAll().size();
        // Create the TeachingAssistant
        TeachingAssistantDTO teachingAssistantDTO = teachingAssistantMapper.toDto(teachingAssistant);
        restTeachingAssistantMockMvc.perform(post("/api/teaching-assistants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teachingAssistantDTO)))
            .andExpect(status().isCreated());

        // Validate the TeachingAssistant in the database
        List<TeachingAssistant> teachingAssistantList = teachingAssistantRepository.findAll();
        assertThat(teachingAssistantList).hasSize(databaseSizeBeforeCreate + 1);
        TeachingAssistant testTeachingAssistant = teachingAssistantList.get(teachingAssistantList.size() - 1);
        assertThat(testTeachingAssistant.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testTeachingAssistant.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
    }

    @Test
    @Transactional
    public void createTeachingAssistantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teachingAssistantRepository.findAll().size();

        // Create the TeachingAssistant with an existing ID
        teachingAssistant.setId(1L);
        TeachingAssistantDTO teachingAssistantDTO = teachingAssistantMapper.toDto(teachingAssistant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeachingAssistantMockMvc.perform(post("/api/teaching-assistants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teachingAssistantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TeachingAssistant in the database
        List<TeachingAssistant> teachingAssistantList = teachingAssistantRepository.findAll();
        assertThat(teachingAssistantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTeachingAssistants() throws Exception {
        // Initialize the database
        teachingAssistantRepository.saveAndFlush(teachingAssistant);

        // Get all the teachingAssistantList
        restTeachingAssistantMockMvc.perform(get("/api/teaching-assistants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teachingAssistant.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)));
    }
    
    @Test
    @Transactional
    public void getTeachingAssistant() throws Exception {
        // Initialize the database
        teachingAssistantRepository.saveAndFlush(teachingAssistant);

        // Get the teachingAssistant
        restTeachingAssistantMockMvc.perform(get("/api/teaching-assistants/{id}", teachingAssistant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teachingAssistant.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingTeachingAssistant() throws Exception {
        // Get the teachingAssistant
        restTeachingAssistantMockMvc.perform(get("/api/teaching-assistants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeachingAssistant() throws Exception {
        // Initialize the database
        teachingAssistantRepository.saveAndFlush(teachingAssistant);

        int databaseSizeBeforeUpdate = teachingAssistantRepository.findAll().size();

        // Update the teachingAssistant
        TeachingAssistant updatedTeachingAssistant = teachingAssistantRepository.findById(teachingAssistant.getId()).get();
        // Disconnect from session so that the updates on updatedTeachingAssistant are not directly saved in db
        em.detach(updatedTeachingAssistant);
        updatedTeachingAssistant
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);
        TeachingAssistantDTO teachingAssistantDTO = teachingAssistantMapper.toDto(updatedTeachingAssistant);

        restTeachingAssistantMockMvc.perform(put("/api/teaching-assistants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teachingAssistantDTO)))
            .andExpect(status().isOk());

        // Validate the TeachingAssistant in the database
        List<TeachingAssistant> teachingAssistantList = teachingAssistantRepository.findAll();
        assertThat(teachingAssistantList).hasSize(databaseSizeBeforeUpdate);
        TeachingAssistant testTeachingAssistant = teachingAssistantList.get(teachingAssistantList.size() - 1);
        assertThat(testTeachingAssistant.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTeachingAssistant.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTeachingAssistant() throws Exception {
        int databaseSizeBeforeUpdate = teachingAssistantRepository.findAll().size();

        // Create the TeachingAssistant
        TeachingAssistantDTO teachingAssistantDTO = teachingAssistantMapper.toDto(teachingAssistant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeachingAssistantMockMvc.perform(put("/api/teaching-assistants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teachingAssistantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TeachingAssistant in the database
        List<TeachingAssistant> teachingAssistantList = teachingAssistantRepository.findAll();
        assertThat(teachingAssistantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTeachingAssistant() throws Exception {
        // Initialize the database
        teachingAssistantRepository.saveAndFlush(teachingAssistant);

        int databaseSizeBeforeDelete = teachingAssistantRepository.findAll().size();

        // Delete the teachingAssistant
        restTeachingAssistantMockMvc.perform(delete("/api/teaching-assistants/{id}", teachingAssistant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TeachingAssistant> teachingAssistantList = teachingAssistantRepository.findAll();
        assertThat(teachingAssistantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
