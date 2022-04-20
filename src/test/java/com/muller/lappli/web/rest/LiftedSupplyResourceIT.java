package com.muller.lappli.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Lifter;
import com.muller.lappli.domain.OneStudySupply;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.repository.LifterRepository;
import com.muller.lappli.repository.OneStudySupplyRepository;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class LiftedSupplyResourceIT {

    @Autowired
    private LifterRepository lifterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMockMvc;

    @Autowired
    private OneStudySupplyRepository oneStudySupplyRepository;

    private OneStudySupply correspondingOneStudySupply;

    private OneStudySupply unCorrespondingOneStudySupply;

    private Lifter lifter;

    @BeforeEach
    void initTest() {
        correspondingOneStudySupply = OneStudySupplyResourceIT.createEntity(em);
        unCorrespondingOneStudySupply = OneStudySupplyResourceIT.createEntity(em);
        lifter = LifterResourceIT.createEntity(em);
    }

    @Test
    @Transactional
    void bestLifterListSetsOnRead() throws Exception {
        correspondingOneStudySupply.milimeterDiameter(0.5);
        lifter.index(1L).minimumMilimeterDiameter(0.0).maximumMilimeterDiameter(1.0);

        correspondingOneStudySupply.markingType(MarkingType.LIFTING);
        oneStudySupplyRepository.saveAndFlush(correspondingOneStudySupply);
        lifterRepository.saveAndFlush(lifter);

        restMockMvc
            .perform(get(OneStudySupplyResourceIT.ENTITY_API_URL_ID, correspondingOneStudySupply.getId()))
            .andExpect(jsonPath("$.bestLiftersNames").value("MR01 "));
    }

    @Test
    @Transactional
    void bestLifterListDoesntSetOnUnCorrespondingRead() throws Exception {
        unCorrespondingOneStudySupply.milimeterDiameter(1.5);
        lifter.index(1L).minimumMilimeterDiameter(0.0).maximumMilimeterDiameter(1.0);

        unCorrespondingOneStudySupply.markingType(MarkingType.LIFTING);
        oneStudySupplyRepository.saveAndFlush(unCorrespondingOneStudySupply);
        lifterRepository.saveAndFlush(lifter);

        restMockMvc
            .perform(get(OneStudySupplyResourceIT.ENTITY_API_URL_ID, unCorrespondingOneStudySupply.getId()))
            .andExpect(jsonPath("$.bestLiftersNames").value(""));
    }
}
