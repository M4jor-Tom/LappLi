package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SheathingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sheathing.class);
        Sheathing sheathing1 = new Sheathing();
        sheathing1.setId(1L);
        Sheathing sheathing2 = new Sheathing();
        sheathing2.setId(sheathing1.getId());
        assertThat(sheathing1).isEqualTo(sheathing2);
        sheathing2.setId(2L);
        assertThat(sheathing1).isNotEqualTo(sheathing2);
        sheathing1.setId(null);
        assertThat(sheathing1).isNotEqualTo(sheathing2);
    }
}
