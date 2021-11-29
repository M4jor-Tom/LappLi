package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LifterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lifter.class);
        Lifter lifter1 = new Lifter();
        lifter1.setId(1L);
        Lifter lifter2 = new Lifter();
        lifter2.setId(lifter1.getId());
        assertThat(lifter1).isEqualTo(lifter2);
        lifter2.setId(2L);
        assertThat(lifter1).isNotEqualTo(lifter2);
        lifter1.setId(null);
        assertThat(lifter1).isNotEqualTo(lifter2);
    }
}
