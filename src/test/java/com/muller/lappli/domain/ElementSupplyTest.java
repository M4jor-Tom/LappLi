package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ElementSupplyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElementSupply.class);
        ElementSupply elementSupply1 = new ElementSupply();
        elementSupply1.setId(1L);
        ElementSupply elementSupply2 = new ElementSupply();
        elementSupply2.setId(elementSupply1.getId());
        assertThat(elementSupply1).isEqualTo(elementSupply2);
        elementSupply2.setId(2L);
        assertThat(elementSupply1).isNotEqualTo(elementSupply2);
        elementSupply1.setId(null);
        assertThat(elementSupply1).isNotEqualTo(elementSupply2);
    }
}
