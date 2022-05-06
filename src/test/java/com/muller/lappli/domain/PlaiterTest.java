package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlaiterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plaiter.class);
        Plaiter plaiter1 = new Plaiter();
        plaiter1.setId(1L);
        Plaiter plaiter2 = new Plaiter();
        plaiter2.setId(plaiter1.getId());
        assertThat(plaiter1).isEqualTo(plaiter2);
        plaiter2.setId(2L);
        assertThat(plaiter1).isNotEqualTo(plaiter2);
        plaiter1.setId(null);
        assertThat(plaiter1).isNotEqualTo(plaiter2);
    }
}
