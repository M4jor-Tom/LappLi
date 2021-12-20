package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OneStudySupplyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OneStudySupply.class);
        OneStudySupply oneStudySupply1 = new OneStudySupply();
        oneStudySupply1.setId(1L);
        OneStudySupply oneStudySupply2 = new OneStudySupply();
        oneStudySupply2.setId(oneStudySupply1.getId());
        assertThat(oneStudySupply1).isEqualTo(oneStudySupply2);
        oneStudySupply2.setId(2L);
        assertThat(oneStudySupply1).isNotEqualTo(oneStudySupply2);
        oneStudySupply1.setId(null);
        assertThat(oneStudySupply1).isNotEqualTo(oneStudySupply2);
    }
}
