package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Screen;
import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.domain.enumeration.MetalFiberKind;
import com.muller.lappli.repository.ScreenRepository;
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
 * Integration tests for the {@link ScreenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ScreenResourceIT {

    private static final Long DEFAULT_OPERATION_LAYER = 1L;
    private static final Long UPDATED_OPERATION_LAYER = 2L;

    private static final Boolean DEFAULT_ASSEMBLY_MEAN_IS_SAME_THAN_ASSEMBLYS = false;
    private static final Boolean UPDATED_ASSEMBLY_MEAN_IS_SAME_THAN_ASSEMBLYS = true;

    private static final Long DEFAULT_FORCED_DIAMETER_ASSEMBLY_STEP = 1L;
    private static final Long UPDATED_FORCED_DIAMETER_ASSEMBLY_STEP = 2L;

    private static final Long DEFAULT_ANONYMOUS_COPPER_FIBER_NUMBER = 1L;
    private static final Long UPDATED_ANONYMOUS_COPPER_FIBER_NUMBER = 2L;

    private static final String DEFAULT_ANONYMOUS_COPPER_FIBER_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_ANONYMOUS_COPPER_FIBER_DESIGNATION = "BBBBBBBBBB";

    private static final MetalFiberKind DEFAULT_ANONYMOUS_COPPER_FIBER_KIND = MetalFiberKind.RED_COPPER;
    private static final MetalFiberKind UPDATED_ANONYMOUS_COPPER_FIBER_KIND = MetalFiberKind.TINNED_COPPER;

    private static final Double DEFAULT_ANONYMOUS_COPPER_FIBER_MILIMETER_DIAMETER = 1D;
    private static final Double UPDATED_ANONYMOUS_COPPER_FIBER_MILIMETER_DIAMETER = 2D;

    private static final String ENTITY_API_URL = "/api/screens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScreenMockMvc;

    private Screen screen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Screen createEntity(EntityManager em) {
        Screen screen = new Screen()
            .operationLayer(DEFAULT_OPERATION_LAYER)
            .assemblyMeanIsSameThanAssemblys(DEFAULT_ASSEMBLY_MEAN_IS_SAME_THAN_ASSEMBLYS)
            .forcedDiameterAssemblyStep(DEFAULT_FORCED_DIAMETER_ASSEMBLY_STEP)
            .anonymousCopperFiberNumber(DEFAULT_ANONYMOUS_COPPER_FIBER_NUMBER)
            .anonymousCopperFiberDesignation(DEFAULT_ANONYMOUS_COPPER_FIBER_DESIGNATION)
            .anonymousCopperFiberKind(DEFAULT_ANONYMOUS_COPPER_FIBER_KIND)
            .anonymousCopperFiberMilimeterDiameter(DEFAULT_ANONYMOUS_COPPER_FIBER_MILIMETER_DIAMETER);
        // Add required entity
        StrandSupply strandSupply;
        if (TestUtil.findAll(em, StrandSupply.class).isEmpty()) {
            strandSupply = StrandSupplyResourceIT.createEntity(em);
            em.persist(strandSupply);
            em.flush();
        } else {
            strandSupply = TestUtil.findAll(em, StrandSupply.class).get(0);
        }
        screen.setOwnerStrandSupply(strandSupply);
        return screen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Screen createUpdatedEntity(EntityManager em) {
        Screen screen = new Screen()
            .operationLayer(UPDATED_OPERATION_LAYER)
            .assemblyMeanIsSameThanAssemblys(UPDATED_ASSEMBLY_MEAN_IS_SAME_THAN_ASSEMBLYS)
            .forcedDiameterAssemblyStep(UPDATED_FORCED_DIAMETER_ASSEMBLY_STEP)
            .anonymousCopperFiberNumber(UPDATED_ANONYMOUS_COPPER_FIBER_NUMBER)
            .anonymousCopperFiberDesignation(UPDATED_ANONYMOUS_COPPER_FIBER_DESIGNATION)
            .anonymousCopperFiberKind(UPDATED_ANONYMOUS_COPPER_FIBER_KIND)
            .anonymousCopperFiberMilimeterDiameter(UPDATED_ANONYMOUS_COPPER_FIBER_MILIMETER_DIAMETER);
        // Add required entity
        StrandSupply strandSupply;
        if (TestUtil.findAll(em, StrandSupply.class).isEmpty()) {
            strandSupply = StrandSupplyResourceIT.createUpdatedEntity(em);
            em.persist(strandSupply);
            em.flush();
        } else {
            strandSupply = TestUtil.findAll(em, StrandSupply.class).get(0);
        }
        screen.setOwnerStrandSupply(strandSupply);
        return screen;
    }

    @BeforeEach
    public void initTest() {
        screen = createEntity(em);
    }

    @Test
    @Transactional
    void createScreen() throws Exception {
        int databaseSizeBeforeCreate = screenRepository.findAll().size();
        // Create the Screen
        restScreenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(screen)))
            .andExpect(status().isCreated());

        // Validate the Screen in the database
        List<Screen> screenList = screenRepository.findAll();
        assertThat(screenList).hasSize(databaseSizeBeforeCreate + 1);
        Screen testScreen = screenList.get(screenList.size() - 1);
        assertThat(testScreen.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testScreen.getAssemblyMeanIsSameThanAssemblys()).isEqualTo(DEFAULT_ASSEMBLY_MEAN_IS_SAME_THAN_ASSEMBLYS);
        assertThat(testScreen.getForcedDiameterAssemblyStep()).isEqualTo(DEFAULT_FORCED_DIAMETER_ASSEMBLY_STEP);
        assertThat(testScreen.getAnonymousCopperFiberNumber()).isEqualTo(DEFAULT_ANONYMOUS_COPPER_FIBER_NUMBER);
        assertThat(testScreen.getAnonymousCopperFiberDesignation()).isEqualTo(DEFAULT_ANONYMOUS_COPPER_FIBER_DESIGNATION);
        assertThat(testScreen.getAnonymousCopperFiberKind()).isEqualTo(DEFAULT_ANONYMOUS_COPPER_FIBER_KIND);
        assertThat(testScreen.getAnonymousCopperFiberMilimeterDiameter()).isEqualTo(DEFAULT_ANONYMOUS_COPPER_FIBER_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void createScreenWithExistingId() throws Exception {
        // Create the Screen with an existing ID
        screen.setId(1L);

        int databaseSizeBeforeCreate = screenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restScreenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(screen)))
            .andExpect(status().isBadRequest());

        // Validate the Screen in the database
        List<Screen> screenList = screenRepository.findAll();
        assertThat(screenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOperationLayerIsRequired() throws Exception {
        int databaseSizeBeforeTest = screenRepository.findAll().size();
        // set the field null
        screen.setOperationLayer(null);

        // Create the Screen, which fails.

        restScreenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(screen)))
            .andExpect(status().isBadRequest());

        List<Screen> screenList = screenRepository.findAll();
        assertThat(screenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAssemblyMeanIsSameThanAssemblysIsRequired() throws Exception {
        int databaseSizeBeforeTest = screenRepository.findAll().size();
        // set the field null
        screen.setAssemblyMeanIsSameThanAssemblys(null);

        // Create the Screen, which fails.

        restScreenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(screen)))
            .andExpect(status().isBadRequest());

        List<Screen> screenList = screenRepository.findAll();
        assertThat(screenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllScreens() throws Exception {
        // Initialize the database
        screenRepository.saveAndFlush(screen);

        // Get all the screenList
        restScreenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(screen.getId().intValue())))
            .andExpect(jsonPath("$.[*].operationLayer").value(hasItem(DEFAULT_OPERATION_LAYER.intValue())))
            .andExpect(
                jsonPath("$.[*].assemblyMeanIsSameThanAssemblys")
                    .value(hasItem(DEFAULT_ASSEMBLY_MEAN_IS_SAME_THAN_ASSEMBLYS.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].forcedDiameterAssemblyStep").value(hasItem(DEFAULT_FORCED_DIAMETER_ASSEMBLY_STEP.intValue())))
            .andExpect(jsonPath("$.[*].anonymousCopperFiberNumber").value(hasItem(DEFAULT_ANONYMOUS_COPPER_FIBER_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].anonymousCopperFiberDesignation").value(hasItem(DEFAULT_ANONYMOUS_COPPER_FIBER_DESIGNATION)))
            .andExpect(jsonPath("$.[*].anonymousCopperFiberKind").value(hasItem(DEFAULT_ANONYMOUS_COPPER_FIBER_KIND.toString())))
            .andExpect(
                jsonPath("$.[*].anonymousCopperFiberMilimeterDiameter")
                    .value(hasItem(DEFAULT_ANONYMOUS_COPPER_FIBER_MILIMETER_DIAMETER.doubleValue()))
            );
    }

    @Test
    @Transactional
    void getScreen() throws Exception {
        // Initialize the database
        screenRepository.saveAndFlush(screen);

        // Get the screen
        restScreenMockMvc
            .perform(get(ENTITY_API_URL_ID, screen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(screen.getId().intValue()))
            .andExpect(jsonPath("$.operationLayer").value(DEFAULT_OPERATION_LAYER.intValue()))
            .andExpect(jsonPath("$.assemblyMeanIsSameThanAssemblys").value(DEFAULT_ASSEMBLY_MEAN_IS_SAME_THAN_ASSEMBLYS.booleanValue()))
            .andExpect(jsonPath("$.forcedDiameterAssemblyStep").value(DEFAULT_FORCED_DIAMETER_ASSEMBLY_STEP.intValue()))
            .andExpect(jsonPath("$.anonymousCopperFiberNumber").value(DEFAULT_ANONYMOUS_COPPER_FIBER_NUMBER.intValue()))
            .andExpect(jsonPath("$.anonymousCopperFiberDesignation").value(DEFAULT_ANONYMOUS_COPPER_FIBER_DESIGNATION))
            .andExpect(jsonPath("$.anonymousCopperFiberKind").value(DEFAULT_ANONYMOUS_COPPER_FIBER_KIND.toString()))
            .andExpect(
                jsonPath("$.anonymousCopperFiberMilimeterDiameter").value(DEFAULT_ANONYMOUS_COPPER_FIBER_MILIMETER_DIAMETER.doubleValue())
            );
    }

    @Test
    @Transactional
    void getNonExistingScreen() throws Exception {
        // Get the screen
        restScreenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewScreen() throws Exception {
        // Initialize the database
        screenRepository.saveAndFlush(screen);

        int databaseSizeBeforeUpdate = screenRepository.findAll().size();

        // Update the screen
        Screen updatedScreen = screenRepository.findById(screen.getId()).get();
        // Disconnect from session so that the updates on updatedScreen are not directly saved in db
        em.detach(updatedScreen);
        updatedScreen
            .operationLayer(UPDATED_OPERATION_LAYER)
            .assemblyMeanIsSameThanAssemblys(UPDATED_ASSEMBLY_MEAN_IS_SAME_THAN_ASSEMBLYS)
            .forcedDiameterAssemblyStep(UPDATED_FORCED_DIAMETER_ASSEMBLY_STEP)
            .anonymousCopperFiberNumber(UPDATED_ANONYMOUS_COPPER_FIBER_NUMBER)
            .anonymousCopperFiberDesignation(UPDATED_ANONYMOUS_COPPER_FIBER_DESIGNATION)
            .anonymousCopperFiberKind(UPDATED_ANONYMOUS_COPPER_FIBER_KIND)
            .anonymousCopperFiberMilimeterDiameter(UPDATED_ANONYMOUS_COPPER_FIBER_MILIMETER_DIAMETER);

        restScreenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedScreen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedScreen))
            )
            .andExpect(status().isOk());

        // Validate the Screen in the database
        List<Screen> screenList = screenRepository.findAll();
        assertThat(screenList).hasSize(databaseSizeBeforeUpdate);
        Screen testScreen = screenList.get(screenList.size() - 1);
        assertThat(testScreen.getOperationLayer()).isEqualTo(UPDATED_OPERATION_LAYER);
        assertThat(testScreen.getAssemblyMeanIsSameThanAssemblys()).isEqualTo(UPDATED_ASSEMBLY_MEAN_IS_SAME_THAN_ASSEMBLYS);
        assertThat(testScreen.getForcedDiameterAssemblyStep()).isEqualTo(UPDATED_FORCED_DIAMETER_ASSEMBLY_STEP);
        assertThat(testScreen.getAnonymousCopperFiberNumber()).isEqualTo(UPDATED_ANONYMOUS_COPPER_FIBER_NUMBER);
        assertThat(testScreen.getAnonymousCopperFiberDesignation()).isEqualTo(UPDATED_ANONYMOUS_COPPER_FIBER_DESIGNATION);
        assertThat(testScreen.getAnonymousCopperFiberKind()).isEqualTo(UPDATED_ANONYMOUS_COPPER_FIBER_KIND);
        assertThat(testScreen.getAnonymousCopperFiberMilimeterDiameter()).isEqualTo(UPDATED_ANONYMOUS_COPPER_FIBER_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void putNonExistingScreen() throws Exception {
        int databaseSizeBeforeUpdate = screenRepository.findAll().size();
        screen.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScreenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, screen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(screen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Screen in the database
        List<Screen> screenList = screenRepository.findAll();
        assertThat(screenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchScreen() throws Exception {
        int databaseSizeBeforeUpdate = screenRepository.findAll().size();
        screen.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScreenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(screen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Screen in the database
        List<Screen> screenList = screenRepository.findAll();
        assertThat(screenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamScreen() throws Exception {
        int databaseSizeBeforeUpdate = screenRepository.findAll().size();
        screen.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScreenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(screen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Screen in the database
        List<Screen> screenList = screenRepository.findAll();
        assertThat(screenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateScreenWithPatch() throws Exception {
        // Initialize the database
        screenRepository.saveAndFlush(screen);

        int databaseSizeBeforeUpdate = screenRepository.findAll().size();

        // Update the screen using partial update
        Screen partialUpdatedScreen = new Screen();
        partialUpdatedScreen.setId(screen.getId());

        partialUpdatedScreen
            .assemblyMeanIsSameThanAssemblys(UPDATED_ASSEMBLY_MEAN_IS_SAME_THAN_ASSEMBLYS)
            .forcedDiameterAssemblyStep(UPDATED_FORCED_DIAMETER_ASSEMBLY_STEP);

        restScreenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScreen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScreen))
            )
            .andExpect(status().isOk());

        // Validate the Screen in the database
        List<Screen> screenList = screenRepository.findAll();
        assertThat(screenList).hasSize(databaseSizeBeforeUpdate);
        Screen testScreen = screenList.get(screenList.size() - 1);
        assertThat(testScreen.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testScreen.getAssemblyMeanIsSameThanAssemblys()).isEqualTo(UPDATED_ASSEMBLY_MEAN_IS_SAME_THAN_ASSEMBLYS);
        assertThat(testScreen.getForcedDiameterAssemblyStep()).isEqualTo(UPDATED_FORCED_DIAMETER_ASSEMBLY_STEP);
        assertThat(testScreen.getAnonymousCopperFiberNumber()).isEqualTo(DEFAULT_ANONYMOUS_COPPER_FIBER_NUMBER);
        assertThat(testScreen.getAnonymousCopperFiberDesignation()).isEqualTo(DEFAULT_ANONYMOUS_COPPER_FIBER_DESIGNATION);
        assertThat(testScreen.getAnonymousCopperFiberKind()).isEqualTo(DEFAULT_ANONYMOUS_COPPER_FIBER_KIND);
        assertThat(testScreen.getAnonymousCopperFiberMilimeterDiameter()).isEqualTo(DEFAULT_ANONYMOUS_COPPER_FIBER_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void fullUpdateScreenWithPatch() throws Exception {
        // Initialize the database
        screenRepository.saveAndFlush(screen);

        int databaseSizeBeforeUpdate = screenRepository.findAll().size();

        // Update the screen using partial update
        Screen partialUpdatedScreen = new Screen();
        partialUpdatedScreen.setId(screen.getId());

        partialUpdatedScreen
            .operationLayer(UPDATED_OPERATION_LAYER)
            .assemblyMeanIsSameThanAssemblys(UPDATED_ASSEMBLY_MEAN_IS_SAME_THAN_ASSEMBLYS)
            .forcedDiameterAssemblyStep(UPDATED_FORCED_DIAMETER_ASSEMBLY_STEP)
            .anonymousCopperFiberNumber(UPDATED_ANONYMOUS_COPPER_FIBER_NUMBER)
            .anonymousCopperFiberDesignation(UPDATED_ANONYMOUS_COPPER_FIBER_DESIGNATION)
            .anonymousCopperFiberKind(UPDATED_ANONYMOUS_COPPER_FIBER_KIND)
            .anonymousCopperFiberMilimeterDiameter(UPDATED_ANONYMOUS_COPPER_FIBER_MILIMETER_DIAMETER);

        restScreenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScreen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScreen))
            )
            .andExpect(status().isOk());

        // Validate the Screen in the database
        List<Screen> screenList = screenRepository.findAll();
        assertThat(screenList).hasSize(databaseSizeBeforeUpdate);
        Screen testScreen = screenList.get(screenList.size() - 1);
        assertThat(testScreen.getOperationLayer()).isEqualTo(UPDATED_OPERATION_LAYER);
        assertThat(testScreen.getAssemblyMeanIsSameThanAssemblys()).isEqualTo(UPDATED_ASSEMBLY_MEAN_IS_SAME_THAN_ASSEMBLYS);
        assertThat(testScreen.getForcedDiameterAssemblyStep()).isEqualTo(UPDATED_FORCED_DIAMETER_ASSEMBLY_STEP);
        assertThat(testScreen.getAnonymousCopperFiberNumber()).isEqualTo(UPDATED_ANONYMOUS_COPPER_FIBER_NUMBER);
        assertThat(testScreen.getAnonymousCopperFiberDesignation()).isEqualTo(UPDATED_ANONYMOUS_COPPER_FIBER_DESIGNATION);
        assertThat(testScreen.getAnonymousCopperFiberKind()).isEqualTo(UPDATED_ANONYMOUS_COPPER_FIBER_KIND);
        assertThat(testScreen.getAnonymousCopperFiberMilimeterDiameter()).isEqualTo(UPDATED_ANONYMOUS_COPPER_FIBER_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void patchNonExistingScreen() throws Exception {
        int databaseSizeBeforeUpdate = screenRepository.findAll().size();
        screen.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScreenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, screen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(screen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Screen in the database
        List<Screen> screenList = screenRepository.findAll();
        assertThat(screenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchScreen() throws Exception {
        int databaseSizeBeforeUpdate = screenRepository.findAll().size();
        screen.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScreenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(screen))
            )
            .andExpect(status().isBadRequest());

        // Validate the Screen in the database
        List<Screen> screenList = screenRepository.findAll();
        assertThat(screenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamScreen() throws Exception {
        int databaseSizeBeforeUpdate = screenRepository.findAll().size();
        screen.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScreenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(screen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Screen in the database
        List<Screen> screenList = screenRepository.findAll();
        assertThat(screenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteScreen() throws Exception {
        // Initialize the database
        screenRepository.saveAndFlush(screen);

        int databaseSizeBeforeDelete = screenRepository.findAll().size();

        // Delete the screen
        restScreenMockMvc
            .perform(delete(ENTITY_API_URL_ID, screen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Screen> screenList = screenRepository.findAll();
        assertThat(screenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
