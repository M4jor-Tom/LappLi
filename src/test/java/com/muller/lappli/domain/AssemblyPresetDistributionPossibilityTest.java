package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.web.rest.TestUtil;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AssemblyPresetDistributionPossibilityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssemblyPresetDistributionPossibility.class);
    }

    @Test
    public void cloneVerifier() throws Exception {
        AssemblyPresetDistributionPossibility original = new AssemblyPresetDistributionPossibility(4.0, List.of(new AssemblyPreset(2, 1)));
        AssemblyPresetDistributionPossibility clone = original.clone();

        assertThat(original).isEqualTo(clone);
        assertThat(original).isNotSameAs(clone);

        int assemblyPresetindex = 0;
        for (AssemblyPreset assemblyPreset : original.getAssemblyPresets()) {
            AssemblyPreset clonedAssemblyPreset = clone.getAssemblyPresets().get(assemblyPresetindex);

            assertThat(assemblyPreset).isEqualTo(clonedAssemblyPreset);
            assertThat(assemblyPreset).isNotSameAs(clonedAssemblyPreset);

            assemblyPresetindex++;
        }
    }
}
