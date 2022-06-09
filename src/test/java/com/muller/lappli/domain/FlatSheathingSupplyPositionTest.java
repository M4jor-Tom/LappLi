package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FlatSheathingSupplyPositionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FlatSheathingSupplyPosition.class);
        FlatSheathingSupplyPosition flatSheathingSupplyPosition1 = new FlatSheathingSupplyPosition();
        flatSheathingSupplyPosition1.setId(1L);
        FlatSheathingSupplyPosition flatSheathingSupplyPosition2 = new FlatSheathingSupplyPosition();
        flatSheathingSupplyPosition2.setId(flatSheathingSupplyPosition1.getId());
        assertThat(flatSheathingSupplyPosition1).isEqualTo(flatSheathingSupplyPosition2);
        flatSheathingSupplyPosition2.setId(2L);
        assertThat(flatSheathingSupplyPosition1).isNotEqualTo(flatSheathingSupplyPosition2);
        flatSheathingSupplyPosition1.setId(null);
        assertThat(flatSheathingSupplyPosition1).isNotEqualTo(flatSheathingSupplyPosition2);
    }
}
