package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StripTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Strip.class);
        Strip strip1 = new Strip();
        strip1.setId(1L);
        Strip strip2 = new Strip();
        strip2.setId(strip1.getId());
        assertThat(strip1).isEqualTo(strip2);
        strip2.setId(2L);
        assertThat(strip1).isNotEqualTo(strip2);
        strip1.setId(null);
        assertThat(strip1).isNotEqualTo(strip2);
    }
}
