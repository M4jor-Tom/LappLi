package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FlatSheathingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FlatSheathing.class);
        FlatSheathing flatSheathing1 = new FlatSheathing();
        flatSheathing1.setId(1L);
        FlatSheathing flatSheathing2 = new FlatSheathing();
        flatSheathing2.setId(flatSheathing1.getId());
        assertThat(flatSheathing1).isEqualTo(flatSheathing2);
        flatSheathing2.setId(2L);
        assertThat(flatSheathing1).isNotEqualTo(flatSheathing2);
        flatSheathing1.setId(null);
        assertThat(flatSheathing1).isNotEqualTo(flatSheathing2);
    }
}
