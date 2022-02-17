package com.muller.lappli.domain.enumeration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class AssemblyPresetDistributionTest {

    @Test
    void assemblyPresetDistributionPossibilitiesVerifier() throws Exception {
        for (AssemblyPresetDistribution assemblyPresetDistribution : AssemblyPresetDistribution.values()) {
            assertThat(assemblyPresetDistribution.assemblyPresetDistributionPossibilitiesAreConform()).isTrue();
        }
    }
}
