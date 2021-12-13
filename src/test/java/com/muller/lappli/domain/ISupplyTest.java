package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ISupplyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ISupply.class);
        ISupply iSupply1 = new ISupply();
        iSupply1.setId(1L);
        ISupply iSupply2 = new ISupply();
        iSupply2.setId(iSupply1.getId());
        assertThat(iSupply1).isEqualTo(iSupply2);
        iSupply2.setId(2L);
        assertThat(iSupply1).isNotEqualTo(iSupply2);
        iSupply1.setId(null);
        assertThat(iSupply1).isNotEqualTo(iSupply2);
    }
}
