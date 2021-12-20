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
}
