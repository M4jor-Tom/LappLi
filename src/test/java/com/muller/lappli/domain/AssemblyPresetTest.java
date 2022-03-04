package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class AssemblyPresetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssemblyPreset.class);
    }

    @Test
    public void instanceVerifier() throws Exception {
        assertThat(AssemblyPreset.forCentralCompletionComponent().isCentralAccordingToTotalComponentsCount()).isTrue();

        assertThat(AssemblyPreset.forCentralUtilityComponent().isCentralAccordingToTotalComponentsCount()).isTrue();
    }

    @Test
    public void cloneVerifier() throws Exception {
        AssemblyPreset original = new AssemblyPreset(10, 5);
        AssemblyPreset clone = original.clone();

        assertThat(original).isEqualTo(clone);
        assertThat(original).isNotSameAs(clone);
    }
}
