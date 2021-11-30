package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BangleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bangle.class);
        Bangle bangle1 = new Bangle();
        bangle1.setId(1L);
        Bangle bangle2 = new Bangle();
        bangle2.setId(bangle1.getId());
        assertThat(bangle1).isEqualTo(bangle2);
        bangle2.setId(2L);
        assertThat(bangle1).isNotEqualTo(bangle2);
        bangle1.setId(null);
        assertThat(bangle1).isNotEqualTo(bangle2);
    }
}
