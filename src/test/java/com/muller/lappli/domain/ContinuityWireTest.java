package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContinuityWireTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContinuityWire.class);
        ContinuityWire continuityWire1 = new ContinuityWire();
        continuityWire1.setId(1L);
        ContinuityWire continuityWire2 = new ContinuityWire();
        continuityWire2.setId(continuityWire1.getId());
        assertThat(continuityWire1).isEqualTo(continuityWire2);
        continuityWire2.setId(2L);
        assertThat(continuityWire1).isNotEqualTo(continuityWire2);
        continuityWire1.setId(null);
        assertThat(continuityWire1).isNotEqualTo(continuityWire2);
    }
}
