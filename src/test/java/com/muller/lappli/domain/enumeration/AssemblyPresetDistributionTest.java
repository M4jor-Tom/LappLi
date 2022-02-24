package com.muller.lappli.domain.enumeration;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.domain.CalculatorManager;
import com.muller.lappli.domain.DomainManager;
import org.junit.jupiter.api.Test;

public class AssemblyPresetDistributionTest {

    @Test
    void correspondenceVerifier() {
        Long expectedAssemblyPresetDistributionCalculatorCount = CalculatorManager.getCalculatorInstance().isTargetCalculatorInstance()
            ? Long.valueOf(AssemblyPresetDistribution.values().length)
            : DomainManager.ERROR_LONG_POSITIVE_VALUE;

        assertThat(CalculatorManager.getCalculatorInstance().getAssemblyPresetDistributionCalculatorCount())
            .isEqualTo(expectedAssemblyPresetDistributionCalculatorCount);

        for (AssemblyPresetDistribution assemblyPresetDistribution : AssemblyPresetDistribution.values()) {
            assertThat(
                CalculatorManager.getCalculatorInstance().getCorrespondingAssemblyPresetDistributionCalculator(assemblyPresetDistribution)
            )
                .isNotNull();
        }
    }

    @Test
    void assemblyPresetDistributionPossibilitiesVerifier() throws Exception {
        for (AssemblyPresetDistribution assemblyPresetDistribution : AssemblyPresetDistribution.values()) {
            assertThat(assemblyPresetDistribution.assemblyPresetDistributionPossibilitiesAreConform()).isTrue();
        }
    }
}
