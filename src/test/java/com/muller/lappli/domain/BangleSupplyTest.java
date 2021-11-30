package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BangleSupplyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BangleSupply.class);
        BangleSupply bangleSupply1 = new BangleSupply();
        bangleSupply1.setId(1L);
        BangleSupply bangleSupply2 = new BangleSupply();
        bangleSupply2.setId(bangleSupply1.getId());
        assertThat(bangleSupply1).isEqualTo(bangleSupply2);
        bangleSupply2.setId(2L);
        assertThat(bangleSupply1).isNotEqualTo(bangleSupply2);
        bangleSupply1.setId(null);
        assertThat(bangleSupply1).isNotEqualTo(bangleSupply2);
    }
}
