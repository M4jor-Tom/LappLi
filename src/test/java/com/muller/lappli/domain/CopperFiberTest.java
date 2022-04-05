package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CopperFiberTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CopperFiber.class);
        CopperFiber copperFiber1 = new CopperFiber();
        copperFiber1.setId(1L);
        CopperFiber copperFiber2 = new CopperFiber();
        copperFiber2.setId(copperFiber1.getId());
        assertThat(copperFiber1).isEqualTo(copperFiber2);
        copperFiber2.setId(2L);
        assertThat(copperFiber1).isNotEqualTo(copperFiber2);
        copperFiber1.setId(null);
        assertThat(copperFiber1).isNotEqualTo(copperFiber2);
    }
}
