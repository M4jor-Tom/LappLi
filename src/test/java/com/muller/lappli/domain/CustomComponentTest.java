package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomComponentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomComponent.class);
        CustomComponent customComponent1 = new CustomComponent();
        customComponent1.setId(1L);
        CustomComponent customComponent2 = new CustomComponent();
        customComponent2.setId(customComponent1.getId());
        assertThat(customComponent1).isEqualTo(customComponent2);
        customComponent2.setId(2L);
        assertThat(customComponent1).isNotEqualTo(customComponent2);
        customComponent1.setId(null);
        assertThat(customComponent1).isNotEqualTo(customComponent2);
    }
}
