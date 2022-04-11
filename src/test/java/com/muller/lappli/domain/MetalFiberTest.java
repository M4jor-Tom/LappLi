package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MetalFiberTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetalFiber.class);
        MetalFiber metalFiber1 = new MetalFiber();
        metalFiber1.setId(1L);
        MetalFiber metalFiber2 = new MetalFiber();
        metalFiber2.setId(metalFiber1.getId());
        assertThat(metalFiber1).isEqualTo(metalFiber2);
        metalFiber2.setId(2L);
        assertThat(metalFiber1).isNotEqualTo(metalFiber2);
        metalFiber1.setId(null);
        assertThat(metalFiber1).isNotEqualTo(metalFiber2);
    }
}
