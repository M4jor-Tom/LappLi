package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlaitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plait.class);
        Plait plait1 = new Plait();
        plait1.setId(1L);
        Plait plait2 = new Plait();
        plait2.setId(plait1.getId());
        assertThat(plait1).isEqualTo(plait2);
        plait2.setId(2L);
        assertThat(plait1).isNotEqualTo(plait2);
        plait1.setId(null);
        assertThat(plait1).isNotEqualTo(plait2);
    }
}
