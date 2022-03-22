package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.domain.abstracts.AbstractSupply;
import com.muller.lappli.domain.enumeration.AssemblyPresetDistribution;
import com.muller.lappli.domain.interfaces.IOperation;
import com.muller.lappli.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class StrandSupplyTest {

    private final Logger logger = LoggerFactory.getLogger(StrandSupplyTest.class);

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StrandSupply.class);
        StrandSupply strandSupply1 = new StrandSupply();
        strandSupply1.setId(1L);
        StrandSupply strandSupply2 = new StrandSupply();
        strandSupply2.setId(strandSupply1.getId());
        assertThat(strandSupply1).isEqualTo(strandSupply2);
        strandSupply2.setId(2L);
        assertThat(strandSupply1).isNotEqualTo(strandSupply2);
        strandSupply1.setId(null);
        assertThat(strandSupply1).isNotEqualTo(strandSupply2);
    }

    //@Test TODO Transform into an intergartion test
    void nonCentralOperationInsertionTest() throws Exception {
        StrandSupply strandSupply = new StrandSupply();

        TapeLaying tapeLayingWhichMustRemainAtLayer1 = new TapeLaying().operationLayer(1L);
        TapeLaying tapeLayingAtLayer2ThenShouldBeAtLayer3 = new TapeLaying().operationLayer(2L);
        TapeLaying tapeLayingWhichShouldBeAtLayer3ThenAt4 = new TapeLaying().operationLayer(IOperation.UNDEFINED_OPERATION_LAYER);

        strandSupply.addTapeLayings(tapeLayingWhichMustRemainAtLayer1);
        strandSupply.addTapeLayings(tapeLayingAtLayer2ThenShouldBeAtLayer3);
        strandSupply.addTapeLayings(tapeLayingWhichShouldBeAtLayer3ThenAt4);

        assertThat(tapeLayingWhichMustRemainAtLayer1.getOperationLayer()).isEqualTo(1L);
        assertThat(tapeLayingAtLayer2ThenShouldBeAtLayer3.getOperationLayer()).isEqualTo(2L);
        assertThat(tapeLayingWhichShouldBeAtLayer3ThenAt4.getOperationLayer()).isEqualTo(3L);

        TapeLaying tapeLayingWhichTakesOperationLayer2 = new TapeLaying().operationLayer(2L);

        strandSupply.addTapeLayings(tapeLayingWhichTakesOperationLayer2);

        assertThat(tapeLayingWhichMustRemainAtLayer1.getOperationLayer()).isEqualTo(1L);
        assertThat(tapeLayingWhichTakesOperationLayer2.getOperationLayer()).isEqualTo(2L);
        assertThat(tapeLayingAtLayer2ThenShouldBeAtLayer3.getOperationLayer()).isEqualTo(3L);
        assertThat(tapeLayingWhichShouldBeAtLayer3ThenAt4.getOperationLayer()).isEqualTo(4L);
    }

    @Test
    void autoAssemblyGenerationVerifier() throws Exception {
        logger.info("Running tests with " + CalculatorManager.getCalculatorInstance().getClass().getName());

        for (AssemblyPresetDistribution assemblyPresetDistribution : AssemblyPresetDistribution.values()) {
            Set<SupplyPosition> supplyPositions = new HashSet<SupplyPosition>();
            AbstractSupply<?> loneSupply = new OneStudySupply()
                .milimeterDiameter(0.9)
                .apparitions(assemblyPresetDistribution.getComponentsCount());
            supplyPositions.add(new SupplyPosition().supply(loneSupply));

            Strand strand = new Strand().supplyPositions(supplyPositions);

            StrandSupply strandSupply = new StrandSupply()
                .strand(strand)
                .apparitions(1L)
                .forceCentralUtilityComponent(false)
                .diameterAssemblyStep(5.0)
                .checkAssemblyPresetDistributionIsPossible()
                .autoGenerateAssemblies();

            /*Long centralAssemblyCount = strandSupply.getCentralAssembly() == null
                ? 0L
                : 1L;*/
            Long coreAssemblyCount = Long.valueOf(strandSupply.getCoreAssemblies().size());
            Long generatedAssembliesCount = /*centralAssemblyCount + */coreAssemblyCount;

            logger.info("Try for " + assemblyPresetDistribution.getComponentsCount());
            assertThat(generatedAssembliesCount)
                .isEqualTo(Long.valueOf(strandSupply.getAssemblyPresetDistributionPossibility().getAssemblyPresetsAfterCentral().size()));
        }
    }
}
