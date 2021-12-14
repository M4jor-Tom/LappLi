package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StrandTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Strand.class);
        Strand strand1 = new Strand();
        strand1.setId(1L);
        Strand strand2 = new Strand();
        strand2.setId(strand1.getId());
        assertThat(strand1).isEqualTo(strand2);
        strand2.setId(2L);
        assertThat(strand1).isNotEqualTo(strand2);
        strand1.setId(null);
        assertThat(strand1).isNotEqualTo(strand2);
    }
}
