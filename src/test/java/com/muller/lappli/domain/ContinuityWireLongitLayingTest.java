package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContinuityWireLongitLayingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContinuityWireLongitLaying.class);
        ContinuityWireLongitLaying continuityWireLongitLaying1 = new ContinuityWireLongitLaying();
        continuityWireLongitLaying1.setId(1L);
        ContinuityWireLongitLaying continuityWireLongitLaying2 = new ContinuityWireLongitLaying();
        continuityWireLongitLaying2.setId(continuityWireLongitLaying1.getId());
        assertThat(continuityWireLongitLaying1).isEqualTo(continuityWireLongitLaying2);
        continuityWireLongitLaying2.setId(2L);
        assertThat(continuityWireLongitLaying1).isNotEqualTo(continuityWireLongitLaying2);
        continuityWireLongitLaying1.setId(null);
        assertThat(continuityWireLongitLaying1).isNotEqualTo(continuityWireLongitLaying2);
    }
}
