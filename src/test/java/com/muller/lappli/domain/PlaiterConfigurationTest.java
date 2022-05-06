package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlaiterConfigurationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaiterConfiguration.class);
        PlaiterConfiguration plaiterConfiguration1 = new PlaiterConfiguration();
        plaiterConfiguration1.setId(1L);
        PlaiterConfiguration plaiterConfiguration2 = new PlaiterConfiguration();
        plaiterConfiguration2.setId(plaiterConfiguration1.getId());
        assertThat(plaiterConfiguration1).isEqualTo(plaiterConfiguration2);
        plaiterConfiguration2.setId(2L);
        assertThat(plaiterConfiguration1).isNotEqualTo(plaiterConfiguration2);
        plaiterConfiguration1.setId(null);
        assertThat(plaiterConfiguration1).isNotEqualTo(plaiterConfiguration2);
    }
}
