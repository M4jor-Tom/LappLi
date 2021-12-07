package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MaterialMarkingStatisticTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialMarkingStatistic.class);
        MaterialMarkingStatistic materialMarkingStatistic1 = new MaterialMarkingStatistic();
        materialMarkingStatistic1.setId(1L);
        MaterialMarkingStatistic materialMarkingStatistic2 = new MaterialMarkingStatistic();
        materialMarkingStatistic2.setId(materialMarkingStatistic1.getId());
        assertThat(materialMarkingStatistic1).isEqualTo(materialMarkingStatistic2);
        materialMarkingStatistic2.setId(2L);
        assertThat(materialMarkingStatistic1).isNotEqualTo(materialMarkingStatistic2);
        materialMarkingStatistic1.setId(null);
        assertThat(materialMarkingStatistic1).isNotEqualTo(materialMarkingStatistic2);
    }
}
