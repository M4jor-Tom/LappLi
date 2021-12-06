package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Element;
import com.muller.lappli.domain.ElementKind;
import com.muller.lappli.domain.enumeration.Color;
import com.muller.lappli.repository.ElementRepository;
import com.muller.lappli.service.criteria.ElementCriteria;
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
 * Integration tests for the {@link ElementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ElementResourceIT {

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;
    private static final Long SMALLER_NUMBER = 1L - 1L;

    private static final Color DEFAULT_COLOR = Color.NATURAL;
    private static final Color UPDATED_COLOR = Color.WHITE;

    private static final String ENTITY_API_URL = "/api/elements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ElementRepository elementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restElementMockMvc;

    private Element element;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Element createEntity(EntityManager em) {
        Element element = new Element().number(DEFAULT_NUMBER).color(DEFAULT_COLOR);
        // Add required entity
        ElementKind elementKind;
        if (TestUtil.findAll(em, ElementKind.class).isEmpty()) {
            elementKind = ElementKindResourceIT.createEntity(em);
            em.persist(elementKind);
            em.flush();
        } else {
            elementKind = TestUtil.findAll(em, ElementKind.class).get(0);
        }
        element.setElementKind(elementKind);
        return element;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Element createUpdatedEntity(EntityManager em) {
        Element element = new Element().number(UPDATED_NUMBER).color(UPDATED_COLOR);
        // Add required entity
        ElementKind elementKind;
        if (TestUtil.findAll(em, ElementKind.class).isEmpty()) {
            elementKind = ElementKindResourceIT.createUpdatedEntity(em);
            em.persist(elementKind);
            em.flush();
        } else {
            elementKind = TestUtil.findAll(em, ElementKind.class).get(0);
        }
        element.setElementKind(elementKind);
        return element;
    }

    @BeforeEach
    public void initTest() {
        element = createEntity(em);
    }

    @Test
    @Transactional
    void createElement() throws Exception {
        int databaseSizeBeforeCreate = elementRepository.findAll().size();
        // Create the Element
        restElementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(element)))
            .andExpect(status().isCreated());

        // Validate the Element in the database
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeCreate + 1);
        Element testElement = elementList.get(elementList.size() - 1);
        assertThat(testElement.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testElement.getColor()).isEqualTo(DEFAULT_COLOR);
    }

    @Test
    @Transactional
    void createElementWithExistingId() throws Exception {
        // Create the Element with an existing ID
        element.setId(1L);

        int databaseSizeBeforeCreate = elementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restElementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(element)))
            .andExpect(status().isBadRequest());

        // Validate the Element in the database
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = elementRepository.findAll().size();
        // set the field null
        element.setNumber(null);

        // Create the Element, which fails.

        restElementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(element)))
            .andExpect(status().isBadRequest());

        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkColorIsRequired() throws Exception {
        int databaseSizeBeforeTest = elementRepository.findAll().size();
        // set the field null
        element.setColor(null);

        // Create the Element, which fails.

        restElementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(element)))
            .andExpect(status().isBadRequest());

        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllElements() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        // Get all the elementList
        restElementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(element.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())));
    }

    @Test
    @Transactional
    void getElement() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        // Get the element
        restElementMockMvc
            .perform(get(ENTITY_API_URL_ID, element.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(element.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()));
    }

    @Test
    @Transactional
    void getElementsByIdFiltering() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        Long id = element.getId();

        defaultElementShouldBeFound("id.equals=" + id);
        defaultElementShouldNotBeFound("id.notEquals=" + id);

        defaultElementShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultElementShouldNotBeFound("id.greaterThan=" + id);

        defaultElementShouldBeFound("id.lessThanOrEqual=" + id);
        defaultElementShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllElementsByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        // Get all the elementList where number equals to DEFAULT_NUMBER
        defaultElementShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the elementList where number equals to UPDATED_NUMBER
        defaultElementShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllElementsByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        // Get all the elementList where number not equals to DEFAULT_NUMBER
        defaultElementShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the elementList where number not equals to UPDATED_NUMBER
        defaultElementShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllElementsByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        // Get all the elementList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultElementShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the elementList where number equals to UPDATED_NUMBER
        defaultElementShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllElementsByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        // Get all the elementList where number is not null
        defaultElementShouldBeFound("number.specified=true");

        // Get all the elementList where number is null
        defaultElementShouldNotBeFound("number.specified=false");
    }

    @Test
    @Transactional
    void getAllElementsByNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        // Get all the elementList where number is greater than or equal to DEFAULT_NUMBER
        defaultElementShouldBeFound("number.greaterThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the elementList where number is greater than or equal to UPDATED_NUMBER
        defaultElementShouldNotBeFound("number.greaterThanOrEqual=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllElementsByNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        // Get all the elementList where number is less than or equal to DEFAULT_NUMBER
        defaultElementShouldBeFound("number.lessThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the elementList where number is less than or equal to SMALLER_NUMBER
        defaultElementShouldNotBeFound("number.lessThanOrEqual=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    void getAllElementsByNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        // Get all the elementList where number is less than DEFAULT_NUMBER
        defaultElementShouldNotBeFound("number.lessThan=" + DEFAULT_NUMBER);

        // Get all the elementList where number is less than UPDATED_NUMBER
        defaultElementShouldBeFound("number.lessThan=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllElementsByNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        // Get all the elementList where number is greater than DEFAULT_NUMBER
        defaultElementShouldNotBeFound("number.greaterThan=" + DEFAULT_NUMBER);

        // Get all the elementList where number is greater than SMALLER_NUMBER
        defaultElementShouldBeFound("number.greaterThan=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    void getAllElementsByColorIsEqualToSomething() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        // Get all the elementList where color equals to DEFAULT_COLOR
        defaultElementShouldBeFound("color.equals=" + DEFAULT_COLOR);

        // Get all the elementList where color equals to UPDATED_COLOR
        defaultElementShouldNotBeFound("color.equals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllElementsByColorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        // Get all the elementList where color not equals to DEFAULT_COLOR
        defaultElementShouldNotBeFound("color.notEquals=" + DEFAULT_COLOR);

        // Get all the elementList where color not equals to UPDATED_COLOR
        defaultElementShouldBeFound("color.notEquals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllElementsByColorIsInShouldWork() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        // Get all the elementList where color in DEFAULT_COLOR or UPDATED_COLOR
        defaultElementShouldBeFound("color.in=" + DEFAULT_COLOR + "," + UPDATED_COLOR);

        // Get all the elementList where color equals to UPDATED_COLOR
        defaultElementShouldNotBeFound("color.in=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllElementsByColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        // Get all the elementList where color is not null
        defaultElementShouldBeFound("color.specified=true");

        // Get all the elementList where color is null
        defaultElementShouldNotBeFound("color.specified=false");
    }

    @Test
    @Transactional
    void getAllElementsByElementKindIsEqualToSomething() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);
        ElementKind elementKind;
        if (TestUtil.findAll(em, ElementKind.class).isEmpty()) {
            elementKind = ElementKindResourceIT.createEntity(em);
            em.persist(elementKind);
            em.flush();
        } else {
            elementKind = TestUtil.findAll(em, ElementKind.class).get(0);
        }
        em.persist(elementKind);
        em.flush();
        element.setElementKind(elementKind);
        elementRepository.saveAndFlush(element);
        Long elementKindId = elementKind.getId();

        // Get all the elementList where elementKind equals to elementKindId
        defaultElementShouldBeFound("elementKindId.equals=" + elementKindId);

        // Get all the elementList where elementKind equals to (elementKindId + 1)
        defaultElementShouldNotBeFound("elementKindId.equals=" + (elementKindId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultElementShouldBeFound(String filter) throws Exception {
        restElementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(element.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())));

        // Check, that the count call also returns 1
        restElementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultElementShouldNotBeFound(String filter) throws Exception {
        restElementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restElementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingElement() throws Exception {
        // Get the element
        restElementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewElement() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        int databaseSizeBeforeUpdate = elementRepository.findAll().size();

        // Update the element
        Element updatedElement = elementRepository.findById(element.getId()).get();
        // Disconnect from session so that the updates on updatedElement are not directly saved in db
        em.detach(updatedElement);
        updatedElement.number(UPDATED_NUMBER).color(UPDATED_COLOR);

        restElementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedElement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedElement))
            )
            .andExpect(status().isOk());

        // Validate the Element in the database
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeUpdate);
        Element testElement = elementList.get(elementList.size() - 1);
        assertThat(testElement.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testElement.getColor()).isEqualTo(UPDATED_COLOR);
    }

    @Test
    @Transactional
    void putNonExistingElement() throws Exception {
        int databaseSizeBeforeUpdate = elementRepository.findAll().size();
        element.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, element.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(element))
            )
            .andExpect(status().isBadRequest());

        // Validate the Element in the database
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchElement() throws Exception {
        int databaseSizeBeforeUpdate = elementRepository.findAll().size();
        element.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(element))
            )
            .andExpect(status().isBadRequest());

        // Validate the Element in the database
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamElement() throws Exception {
        int databaseSizeBeforeUpdate = elementRepository.findAll().size();
        element.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(element)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Element in the database
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateElementWithPatch() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        int databaseSizeBeforeUpdate = elementRepository.findAll().size();

        // Update the element using partial update
        Element partialUpdatedElement = new Element();
        partialUpdatedElement.setId(element.getId());

        partialUpdatedElement.number(UPDATED_NUMBER).color(UPDATED_COLOR);

        restElementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedElement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedElement))
            )
            .andExpect(status().isOk());

        // Validate the Element in the database
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeUpdate);
        Element testElement = elementList.get(elementList.size() - 1);
        assertThat(testElement.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testElement.getColor()).isEqualTo(UPDATED_COLOR);
    }

    @Test
    @Transactional
    void fullUpdateElementWithPatch() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        int databaseSizeBeforeUpdate = elementRepository.findAll().size();

        // Update the element using partial update
        Element partialUpdatedElement = new Element();
        partialUpdatedElement.setId(element.getId());

        partialUpdatedElement.number(UPDATED_NUMBER).color(UPDATED_COLOR);

        restElementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedElement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedElement))
            )
            .andExpect(status().isOk());

        // Validate the Element in the database
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeUpdate);
        Element testElement = elementList.get(elementList.size() - 1);
        assertThat(testElement.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testElement.getColor()).isEqualTo(UPDATED_COLOR);
    }

    @Test
    @Transactional
    void patchNonExistingElement() throws Exception {
        int databaseSizeBeforeUpdate = elementRepository.findAll().size();
        element.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, element.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(element))
            )
            .andExpect(status().isBadRequest());

        // Validate the Element in the database
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchElement() throws Exception {
        int databaseSizeBeforeUpdate = elementRepository.findAll().size();
        element.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(element))
            )
            .andExpect(status().isBadRequest());

        // Validate the Element in the database
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamElement() throws Exception {
        int databaseSizeBeforeUpdate = elementRepository.findAll().size();
        element.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(element)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Element in the database
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteElement() throws Exception {
        // Initialize the database
        elementRepository.saveAndFlush(element);

        int databaseSizeBeforeDelete = elementRepository.findAll().size();

        // Delete the element
        restElementMockMvc
            .perform(delete(ENTITY_API_URL_ID, element.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Element> elementList = elementRepository.findAll();
        assertThat(elementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
