package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomComponentSupplyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomComponentSupply.class);
        CustomComponentSupply customComponentSupply1 = new CustomComponentSupply();
        customComponentSupply1.setId(1L);
        CustomComponentSupply customComponentSupply2 = new CustomComponentSupply();
        customComponentSupply2.setId(customComponentSupply1.getId());
        assertThat(customComponentSupply1).isEqualTo(customComponentSupply2);
        customComponentSupply2.setId(2L);
        assertThat(customComponentSupply1).isNotEqualTo(customComponentSupply2);
        customComponentSupply1.setId(null);
        assertThat(customComponentSupply1).isNotEqualTo(customComponentSupply2);
    }
}
