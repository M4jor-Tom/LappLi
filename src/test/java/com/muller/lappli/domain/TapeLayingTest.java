package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TapeLayingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TapeLaying.class);
        TapeLaying tapeLaying1 = new TapeLaying();
        tapeLaying1.setId(1L);
        TapeLaying tapeLaying2 = new TapeLaying();
        tapeLaying2.setId(tapeLaying1.getId());
        assertThat(tapeLaying1).isEqualTo(tapeLaying2);
        tapeLaying2.setId(2L);
        assertThat(tapeLaying1).isNotEqualTo(tapeLaying2);
        tapeLaying1.setId(null);
        assertThat(tapeLaying1).isNotEqualTo(tapeLaying2);
    }
}
