package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ScreenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Screen.class);
        Screen screen1 = new Screen();
        screen1.setId(1L);
        Screen screen2 = new Screen();
        screen2.setId(screen1.getId());
        assertThat(screen1).isEqualTo(screen2);
        screen2.setId(2L);
        assertThat(screen1).isNotEqualTo(screen2);
        screen1.setId(null);
        assertThat(screen1).isNotEqualTo(screen2);
    }
}
