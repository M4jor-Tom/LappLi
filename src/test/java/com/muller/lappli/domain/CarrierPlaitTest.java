package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CarrierPlaitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarrierPlait.class);
        CarrierPlait carrierPlait1 = new CarrierPlait();
        carrierPlait1.setId(1L);
        CarrierPlait carrierPlait2 = new CarrierPlait();
        carrierPlait2.setId(carrierPlait1.getId());
        assertThat(carrierPlait1).isEqualTo(carrierPlait2);
        carrierPlait2.setId(2L);
        assertThat(carrierPlait1).isNotEqualTo(carrierPlait2);
        carrierPlait1.setId(null);
        assertThat(carrierPlait1).isNotEqualTo(carrierPlait2);
    }
}
