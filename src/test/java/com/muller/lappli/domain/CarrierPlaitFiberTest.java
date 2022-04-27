package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CarrierPlaitFiberTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarrierPlaitFiber.class);
        CarrierPlaitFiber carrierPlaitFiber1 = new CarrierPlaitFiber();
        carrierPlaitFiber1.setId(1L);
        CarrierPlaitFiber carrierPlaitFiber2 = new CarrierPlaitFiber();
        carrierPlaitFiber2.setId(carrierPlaitFiber1.getId());
        assertThat(carrierPlaitFiber1).isEqualTo(carrierPlaitFiber2);
        carrierPlaitFiber2.setId(2L);
        assertThat(carrierPlaitFiber1).isNotEqualTo(carrierPlaitFiber2);
        carrierPlaitFiber1.setId(null);
        assertThat(carrierPlaitFiber1).isNotEqualTo(carrierPlaitFiber2);
    }
}
