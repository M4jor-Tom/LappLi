package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CopperTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Copper.class);
        Copper copper1 = new Copper();
        copper1.setId(1L);
        Copper copper2 = new Copper();
        copper2.setId(copper1.getId());
        assertThat(copper1).isEqualTo(copper2);
        copper2.setId(2L);
        assertThat(copper1).isNotEqualTo(copper2);
        copper1.setId(null);
        assertThat(copper1).isNotEqualTo(copper2);
    }
}
