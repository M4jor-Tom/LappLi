package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StripLayingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StripLaying.class);
        StripLaying stripLaying1 = new StripLaying();
        stripLaying1.setId(1L);
        StripLaying stripLaying2 = new StripLaying();
        stripLaying2.setId(stripLaying1.getId());
        assertThat(stripLaying1).isEqualTo(stripLaying2);
        stripLaying2.setId(2L);
        assertThat(stripLaying1).isNotEqualTo(stripLaying2);
        stripLaying1.setId(null);
        assertThat(stripLaying1).isNotEqualTo(stripLaying2);
    }
}
