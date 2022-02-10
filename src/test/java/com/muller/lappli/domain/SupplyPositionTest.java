package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SupplyPositionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyPosition.class);
        SupplyPosition supplyPosition1 = new SupplyPosition();
        supplyPosition1.setId(1L);
        SupplyPosition supplyPosition2 = new SupplyPosition();
        supplyPosition2.setId(supplyPosition1.getId());
        assertThat(supplyPosition1).isEqualTo(supplyPosition2);
        supplyPosition2.setId(2L);
        assertThat(supplyPosition1).isNotEqualTo(supplyPosition2);
        supplyPosition1.setId(null);
        assertThat(supplyPosition1).isNotEqualTo(supplyPosition2);
    }
}
