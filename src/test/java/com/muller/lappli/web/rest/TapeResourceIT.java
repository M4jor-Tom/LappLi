package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Tape;
import com.muller.lappli.domain.TapeKind;
import com.muller.lappli.repository.TapeRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TapeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TapeResourceIT {

    private static final Long DEFAULT_NUMBER = 0L;
    private static final Long UPDATED_NUMBER = 1L;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final Double DEFAULT_MILIMETER_WIDTH = 0D;
    private static final Double UPDATED_MILIMETER_WIDTH = 1D;

    private static final Double DEFAULT_MILIMETER_DIAMETER_INCIDENCY = 1D;
    private static final Double UPDATED_MILIMETER_DIAMETER_INCIDENCY = 2D;

    private static final String ENTITY_API_URL = "/api/tapes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TapeRepository tapeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTapeMockMvc;

    private Tape tape;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tape createEntity(EntityManager em) {
        Tape tape = new Tape()
            .number(DEFAULT_NUMBER)
            .designation(DEFAULT_DESIGNATION)
            .milimeterWidth(DEFAULT_MILIMETER_WIDTH)
            .milimeterDiameterIncidency(DEFAULT_MILIMETER_DIAMETER_INCIDENCY);
        // Add required entity
        TapeKind tapeKind;
        if (TestUtil.findAll(em, TapeKind.class).isEmpty()) {
            tapeKind = TapeKindResourceIT.createEntity(em);
            em.persist(tapeKind);
            em.flush();
        } else {
            tapeKind = TestUtil.findAll(em, TapeKind.class).get(0);
        }
        tape.setTapeKind(tapeKind);
        return tape;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tape createUpdatedEntity(EntityManager em) {
        Tape tape = new Tape()
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .milimeterWidth(UPDATED_MILIMETER_WIDTH)
            .milimeterDiameterIncidency(UPDATED_MILIMETER_DIAMETER_INCIDENCY);
        // Add required entity
        TapeKind tapeKind;
        if (TestUtil.findAll(em, TapeKind.class).isEmpty()) {
            tapeKind = TapeKindResourceIT.createUpdatedEntity(em);
            em.persist(tapeKind);
            em.flush();
        } else {
            tapeKind = TestUtil.findAll(em, TapeKind.class).get(0);
        }
        tape.setTapeKind(tapeKind);
        return tape;
    }

    @BeforeEach
    public void initTest() {
        tape = createEntity(em);
    }

    @Test
    @Transactional
    void createTape() throws Exception {
        int databaseSizeBeforeCreate = tapeRepository.findAll().size();
        // Create the Tape
        restTapeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tape)))
            .andExpect(status().isCreated());

        // Validate the Tape in the database
        List<Tape> tapeList = tapeRepository.findAll();
        assertThat(tapeList).hasSize(databaseSizeBeforeCreate + 1);
        Tape testTape = tapeList.get(tapeList.size() - 1);
        assertThat(testTape.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testTape.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testTape.getMilimeterWidth()).isEqualTo(DEFAULT_MILIMETER_WIDTH);
        assertThat(testTape.getMilimeterDiameterIncidency()).isEqualTo(DEFAULT_MILIMETER_DIAMETER_INCIDENCY);
    }

    @Test
    @Transactional
    void createTapeWithExistingId() throws Exception {
        // Create the Tape with an existing ID
        tape.setId(1L);

        int databaseSizeBeforeCreate = tapeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTapeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tape)))
            .andExpect(status().isBadRequest());

        // Validate the Tape in the database
        List<Tape> tapeList = tapeRepository.findAll();
        assertThat(tapeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = tapeRepository.findAll().size();
        // set the field null
        tape.setNumber(null);

        // Create the Tape, which fails.

        restTapeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tape)))
            .andExpect(status().isBadRequest());

        List<Tape> tapeList = tapeRepository.findAll();
        assertThat(tapeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = tapeRepository.findAll().size();
        // set the field null
        tape.setDesignation(null);

        // Create the Tape, which fails.

        restTapeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tape)))
            .andExpect(status().isBadRequest());

        List<Tape> tapeList = tapeRepository.findAll();
        assertThat(tapeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMilimeterWidthIsRequired() throws Exception {
        int databaseSizeBeforeTest = tapeRepository.findAll().size();
        // set the field null
        tape.setMilimeterWidth(null);

        // Create the Tape, which fails.

        restTapeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tape)))
            .andExpect(status().isBadRequest());

        List<Tape> tapeList = tapeRepository.findAll();
        assertThat(tapeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMilimeterDiameterIncidencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = tapeRepository.findAll().size();
        // set the field null
        tape.setMilimeterDiameterIncidency(null);

        // Create the Tape, which fails.

        restTapeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tape)))
            .andExpect(status().isBadRequest());

        List<Tape> tapeList = tapeRepository.findAll();
        assertThat(tapeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTapes() throws Exception {
        // Initialize the database
        tapeRepository.saveAndFlush(tape);

        // Get all the tapeList
        restTapeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tape.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].milimeterWidth").value(hasItem(DEFAULT_MILIMETER_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].milimeterDiameterIncidency").value(hasItem(DEFAULT_MILIMETER_DIAMETER_INCIDENCY.doubleValue())));
    }

    @Test
    @Transactional
    void getTape() throws Exception {
        // Initialize the database
        tapeRepository.saveAndFlush(tape);

        // Get the tape
        restTapeMockMvc
            .perform(get(ENTITY_API_URL_ID, tape.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tape.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.milimeterWidth").value(DEFAULT_MILIMETER_WIDTH.doubleValue()))
            .andExpect(jsonPath("$.milimeterDiameterIncidency").value(DEFAULT_MILIMETER_DIAMETER_INCIDENCY.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingTape() throws Exception {
        // Get the tape
        restTapeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTape() throws Exception {
        // Initialize the database
        tapeRepository.saveAndFlush(tape);

        int databaseSizeBeforeUpdate = tapeRepository.findAll().size();

        // Update the tape
        Tape updatedTape = tapeRepository.findById(tape.getId()).get();
        // Disconnect from session so that the updates on updatedTape are not directly saved in db
        em.detach(updatedTape);
        updatedTape
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .milimeterWidth(UPDATED_MILIMETER_WIDTH)
            .milimeterDiameterIncidency(UPDATED_MILIMETER_DIAMETER_INCIDENCY);

        restTapeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTape.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTape))
            )
            .andExpect(status().isOk());

        // Validate the Tape in the database
        List<Tape> tapeList = tapeRepository.findAll();
        assertThat(tapeList).hasSize(databaseSizeBeforeUpdate);
        Tape testTape = tapeList.get(tapeList.size() - 1);
        assertThat(testTape.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testTape.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testTape.getMilimeterWidth()).isEqualTo(UPDATED_MILIMETER_WIDTH);
        assertThat(testTape.getMilimeterDiameterIncidency()).isEqualTo(UPDATED_MILIMETER_DIAMETER_INCIDENCY);
    }

    @Test
    @Transactional
    void putNonExistingTape() throws Exception {
        int databaseSizeBeforeUpdate = tapeRepository.findAll().size();
        tape.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTapeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tape.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tape))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tape in the database
        List<Tape> tapeList = tapeRepository.findAll();
        assertThat(tapeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTape() throws Exception {
        int databaseSizeBeforeUpdate = tapeRepository.findAll().size();
        tape.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTapeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tape))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tape in the database
        List<Tape> tapeList = tapeRepository.findAll();
        assertThat(tapeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTape() throws Exception {
        int databaseSizeBeforeUpdate = tapeRepository.findAll().size();
        tape.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTapeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tape)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tape in the database
        List<Tape> tapeList = tapeRepository.findAll();
        assertThat(tapeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTapeWithPatch() throws Exception {
        // Initialize the database
        tapeRepository.saveAndFlush(tape);

        int databaseSizeBeforeUpdate = tapeRepository.findAll().size();

        // Update the tape using partial update
        Tape partialUpdatedTape = new Tape();
        partialUpdatedTape.setId(tape.getId());

        partialUpdatedTape.number(UPDATED_NUMBER).milimeterDiameterIncidency(UPDATED_MILIMETER_DIAMETER_INCIDENCY);

        restTapeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTape.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTape))
            )
            .andExpect(status().isOk());

        // Validate the Tape in the database
        List<Tape> tapeList = tapeRepository.findAll();
        assertThat(tapeList).hasSize(databaseSizeBeforeUpdate);
        Tape testTape = tapeList.get(tapeList.size() - 1);
        assertThat(testTape.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testTape.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testTape.getMilimeterWidth()).isEqualTo(DEFAULT_MILIMETER_WIDTH);
        assertThat(testTape.getMilimeterDiameterIncidency()).isEqualTo(UPDATED_MILIMETER_DIAMETER_INCIDENCY);
    }

    @Test
    @Transactional
    void fullUpdateTapeWithPatch() throws Exception {
        // Initialize the database
        tapeRepository.saveAndFlush(tape);

        int databaseSizeBeforeUpdate = tapeRepository.findAll().size();

        // Update the tape using partial update
        Tape partialUpdatedTape = new Tape();
        partialUpdatedTape.setId(tape.getId());

        partialUpdatedTape
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .milimeterWidth(UPDATED_MILIMETER_WIDTH)
            .milimeterDiameterIncidency(UPDATED_MILIMETER_DIAMETER_INCIDENCY);

        restTapeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTape.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTape))
            )
            .andExpect(status().isOk());

        // Validate the Tape in the database
        List<Tape> tapeList = tapeRepository.findAll();
        assertThat(tapeList).hasSize(databaseSizeBeforeUpdate);
        Tape testTape = tapeList.get(tapeList.size() - 1);
        assertThat(testTape.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testTape.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testTape.getMilimeterWidth()).isEqualTo(UPDATED_MILIMETER_WIDTH);
        assertThat(testTape.getMilimeterDiameterIncidency()).isEqualTo(UPDATED_MILIMETER_DIAMETER_INCIDENCY);
    }

    @Test
    @Transactional
    void patchNonExistingTape() throws Exception {
        int databaseSizeBeforeUpdate = tapeRepository.findAll().size();
        tape.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTapeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tape.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tape))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tape in the database
        List<Tape> tapeList = tapeRepository.findAll();
        assertThat(tapeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTape() throws Exception {
        int databaseSizeBeforeUpdate = tapeRepository.findAll().size();
        tape.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTapeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tape))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tape in the database
        List<Tape> tapeList = tapeRepository.findAll();
        assertThat(tapeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTape() throws Exception {
        int databaseSizeBeforeUpdate = tapeRepository.findAll().size();
        tape.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTapeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tape)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tape in the database
        List<Tape> tapeList = tapeRepository.findAll();
        assertThat(tapeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTape() throws Exception {
        // Initialize the database
        tapeRepository.saveAndFlush(tape);

        int databaseSizeBeforeDelete = tapeRepository.findAll().size();

        // Delete the tape
        restTapeMockMvc
            .perform(delete(ENTITY_API_URL_ID, tape.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tape> tapeList = tapeRepository.findAll();
        assertThat(tapeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
