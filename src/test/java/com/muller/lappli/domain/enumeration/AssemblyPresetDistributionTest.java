package com.muller.lappli.domain.enumeration;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.domain.interfaces.ICalculator;
import org.junit.jupiter.api.Test;

public class AssemblyPresetDistributionTest {

    @Test
    void correspondenceVerifier() {
        assertThat(ICalculator.getInstance().assemblyPresetDistributionCalculatorCount())
            .isEqualTo(Long.valueOf(AssemblyPresetDistribution.values().length));
    }

    @Test
    void assemblyPresetDistributionPossibilitiesVerifier() throws Exception {
        for (AssemblyPresetDistribution assemblyPresetDistribution : AssemblyPresetDistribution.values()) {
            assertThat(assemblyPresetDistribution.assemblyPresetDistributionPossibilitiesAreConform()).isTrue();
        }
    }
}
