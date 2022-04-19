package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SteelFiberTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SteelFiber.class);
        SteelFiber steelFiber1 = new SteelFiber();
        steelFiber1.setId(1L);
        SteelFiber steelFiber2 = new SteelFiber();
        steelFiber2.setId(steelFiber1.getId());
        assertThat(steelFiber1).isEqualTo(steelFiber2);
        steelFiber2.setId(2L);
        assertThat(steelFiber1).isNotEqualTo(steelFiber2);
        steelFiber1.setId(null);
        assertThat(steelFiber1).isNotEqualTo(steelFiber2);
    }
}
