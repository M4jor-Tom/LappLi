package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StrandSupplyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StrandSupply.class);
        StrandSupply strandSupply1 = new StrandSupply();
        strandSupply1.setId(1L);
        StrandSupply strandSupply2 = new StrandSupply();
        strandSupply2.setId(strandSupply1.getId());
        assertThat(strandSupply1).isEqualTo(strandSupply2);
        strandSupply2.setId(2L);
        assertThat(strandSupply1).isNotEqualTo(strandSupply2);
        strandSupply1.setId(null);
        assertThat(strandSupply1).isNotEqualTo(strandSupply2);
    }

    @Test
    void nonCentralOperationInsertionTest() throws Exception {
        StrandSupply strandSupply = new StrandSupply();

        TapeLaying tapeLayingWhichMustRemainAtLayer1 = new TapeLaying().operationLayer(1L);
        TapeLaying tapeLayingAtLayer2ThenShouldBeAtLayer3 = new TapeLaying().operationLayer(2L);
        TapeLaying tapeLayingWhichShouldBeAtLayer3ThenAt4 = new TapeLaying().operationLayer(null);

        strandSupply.insertNonCentralOperation(tapeLayingWhichMustRemainAtLayer1);
        strandSupply.insertNonCentralOperation(tapeLayingAtLayer2ThenShouldBeAtLayer3);
        strandSupply.insertNonCentralOperation(tapeLayingWhichShouldBeAtLayer3ThenAt4);

        assertThat(tapeLayingWhichMustRemainAtLayer1.getOperationLayer()).isEqualTo(1L);
        assertThat(tapeLayingAtLayer2ThenShouldBeAtLayer3.getOperationLayer()).isEqualTo(2L);
        assertThat(tapeLayingWhichShouldBeAtLayer3ThenAt4.getOperationLayer()).isEqualTo(3L);

        TapeLaying tapeLayingWhichTakesOperationLayer2 = new TapeLaying().operationLayer(2L);

        strandSupply.insertNonCentralOperation(tapeLayingWhichTakesOperationLayer2);

        assertThat(tapeLayingWhichMustRemainAtLayer1.getOperationLayer()).isEqualTo(1L);
        assertThat(tapeLayingWhichTakesOperationLayer2.getOperationLayer()).isEqualTo(2L);
        assertThat(tapeLayingAtLayer2ThenShouldBeAtLayer3.getOperationLayer()).isEqualTo(3L);
        assertThat(tapeLayingWhichShouldBeAtLayer3ThenAt4.getOperationLayer()).isEqualTo(4L);
    }
}
