package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LifterRunMeasureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LifterRunMeasure.class);
        LifterRunMeasure lifterRunMeasure1 = new LifterRunMeasure();
        lifterRunMeasure1.setId(1L);
        LifterRunMeasure lifterRunMeasure2 = new LifterRunMeasure();
        lifterRunMeasure2.setId(lifterRunMeasure1.getId());
        assertThat(lifterRunMeasure1).isEqualTo(lifterRunMeasure2);
        lifterRunMeasure2.setId(2L);
        assertThat(lifterRunMeasure1).isNotEqualTo(lifterRunMeasure2);
        lifterRunMeasure1.setId(null);
        assertThat(lifterRunMeasure1).isNotEqualTo(lifterRunMeasure2);
    }
}
