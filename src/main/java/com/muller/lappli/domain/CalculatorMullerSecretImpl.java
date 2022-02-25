package com.muller.lappli.domain;

import com.muller.lappli.domain.abstracts.AbstractAssembly;
import com.muller.lappli.domain.enumeration.AssemblyPresetDistribution;
import com.muller.lappli.domain.interfaces.IAssemblyPresetDistributionCalculator;
import com.muller.lappli.domain.interfaces.ICalculator;
import java.util.ArrayList;
import java.util.List;

public class CalculatorMullerSecretImpl implements ICalculator {

    public static final Double DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT = 1.0;

    public CalculatorMullerSecretImpl() {}

    @Override
    public Double getSuppliedComponentsAverageDiameterCentralVoidDiameter(Long componentsCount, Double diameterAssemblyStep) {
        if (componentsCount == Long.valueOf(0) || componentsCount == Long.valueOf(1)) {
            return 0.0;
        }

        return (
            Math.sqrt((1 + (Math.pow(Math.PI / diameterAssemblyStep, 2))) / (Math.pow(Math.tan(Math.PI / componentsCount), 2)) + 1) - 1
        );
    }

    public AssemblyPresetDistributionPossibility getCalculatedCloneAssemblyPresetDistributionPossibility(StrandSupply strandSupply) {
        return AssemblyPresetDistribution
            .forStrandSupply(strandSupply)
            .getAssemblyPresetDistributionPossibility(strandSupply.getForceCentralUtilityComponent())
            .getCloneWithCalculatedCentralComponent(
                strandSupply.getSuppliedComponentsAverageMilimeterDiameter(),
                strandSupply.getDiameterAssemblyStep()
            );
    }

    @Override
    public AssemblyPresetDistributionPossibility getAssemblyPresetDistributionPossibility(StrandSupply strandSupply) {
        /*DomainManager.noticeInPrompt(   //TODO: prompt out
            "CentralAssembly: " + strand.getCentralAssembly() +
            "\nCentralAssembly's SupplyPosition: " + strand.getCentralAssembly().getSupplyPosition() +
            "\nforces: " + strand.forcesCenterDiameterWithSuppliedComponent() +
            "\nshall: " + strand.shallUseAssemblyPresetDistributionPossibilities()
        );*/

        if (strandSupply.shallUseAssemblyPresetDistributionPossibilities()) {
            return getCalculatedCloneAssemblyPresetDistributionPossibility(strandSupply);
        }

        Double milimeterDiameterBeforeCentralCompletionComponent = Double.NaN;

        try {
            milimeterDiameterBeforeCentralCompletionComponent =
                strandSupply.getCentralAssembly().getSupplyPosition().getSupply().getCylindricComponent().getMilimeterDiameter();
        } catch (NullPointerException e) {
            //e.printStackTrace();
        }

        AssemblyPresetDistributionPossibility assemblyPresetDistributionPossibility = new AssemblyPresetDistributionPossibility(
            milimeterDiameterBeforeCentralCompletionComponent,
            new ArrayList<AssemblyPreset>()
        );

        //The amount of supplied components in the Strand, that we MUST spend
        //as utility components
        Long remainingSuppliesComponentsToSpend = strandSupply.getSuppliedComponentsDividedCount();

        //The central AssemblyPreset, which can have or not an utility component
        AssemblyPreset centralAssemblyPreset = strandSupply.getForceCentralUtilityComponent()
            ? new AssemblyPreset(1, 0)
            : new AssemblyPreset(0, 1);

        //As for each AssemblyPresets, we must remove the utility components count
        //of it to the remaining supplied Components count
        remainingSuppliesComponentsToSpend -= centralAssemblyPreset.getUtilityComponentsCount();

        //We add the central AssemblyPreset to the AssemblyPresetDistributionPossibility
        assemblyPresetDistributionPossibility.getAssemblyPresets().add(centralAssemblyPreset);

        Boolean generatingFirstCoreAssemblyPreset = true;

        //We need to spend all of the supplied Components
        while (remainingSuppliesComponentsToSpend > 0) {
            Long newAssemblyPresetTotalComponentsCount = null;

            //Here, we find out the new AssemblyPreset's total Components count
            if (generatingFirstCoreAssemblyPreset) {
                //TODO: Check first CoreAssembly's has positive assemblyVoid,
                //to suggest a components count
                //Normally it's okay, check this

                try {
                    newAssemblyPresetTotalComponentsCount =
                        Double
                            .valueOf(
                                Math.floor(
                                    suggestSuppliedComponentsCountWithMilimeterDiameters(
                                        milimeterDiameterBeforeCentralCompletionComponent,
                                        strandSupply.getSuppliedComponentsAverageMilimeterDiameter(),
                                        strandSupply.getDiameterAssemblyStep()
                                    )
                                )
                            )
                            .longValue();
                } catch (NullPointerException e) {}

                //TODO: prompt out
                //DomainManager.noticeInPrompt("newAssemblyPresetTotalComponentsCount = " + newAssemblyPresetTotalComponentsCount);

                generatingFirstCoreAssemblyPreset = false;
            } else {
                //To know the new AssemblyPreset total Components count,
                //we need in first the last AssemblyPreset's total Components count
                Long lastAssemblyPresetTotalComponentsCount = assemblyPresetDistributionPossibility
                    .getLastAssemblyPreset()
                    .getTotalComponentsCount();

                //We then add 6 to it, to get this value.
                //As this is purely geometrical, we'll only know the total Components count,
                newAssemblyPresetTotalComponentsCount = lastAssemblyPresetTotalComponentsCount + 6;
            }

            //But we need the utility Components count, to substract it to the
            //remaining supplied Components count
            Long newAssemblyPresetUtilityComponentsCount = Math.max(
                newAssemblyPresetTotalComponentsCount,
                remainingSuppliesComponentsToSpend
            );

            //We now know the needed count of completion Components because
            //we know the total count and the utility count.
            Long newAssemblyPresetCompletionComponentsCount =
                newAssemblyPresetTotalComponentsCount - newAssemblyPresetUtilityComponentsCount;

            AssemblyPreset newAssemblyPreset = new AssemblyPreset(
                newAssemblyPresetUtilityComponentsCount,
                newAssemblyPresetCompletionComponentsCount
            );

            //We add the new AssemblyPreset to the AssemblyPresetDistributionPossibility
            assemblyPresetDistributionPossibility.getAssemblyPresets().add(newAssemblyPreset);

            //As for each AssemblyPresets, we must remove the utility components count
            //of it to the remaining supplied Components count
            remainingSuppliesComponentsToSpend -= newAssemblyPresetUtilityComponentsCount;
        }

        return assemblyPresetDistributionPossibility;
    }

    @Override
    public Long getSuppliesCountAtAssembly(StrandSupply strandSupply, Long assemblyIndex) {
        if (strandSupply.forcesCenterDiameterWithSuppliedComponent()) {
            return getCalculatedCloneAssemblyPresetDistributionPossibility(strandSupply)
                .getAssemblyPresets()
                .get(assemblyIndex.intValue())
                .getTotalComponentsCount();
        }

        return null;
    }

    @Override
    public List<AssemblyPresetDistributionPossibility> getAssemblyPresetDistributionPossibilitiesForAssemblyPresetDistribution(
        AssemblyPresetDistribution assemblyPresetDistribution
    ) {
        return getCorrespondingAssemblyPresetDistributionSecretImpl(assemblyPresetDistribution)
            .getAssemblyPresetDistributionPossibilities();
    }

    private AssemblyPresetDistributionSecretImpl getCorrespondingAssemblyPresetDistributionSecretImpl(
        AssemblyPresetDistribution assemblyPresetDistribution
    ) {
        for (AssemblyPresetDistributionSecretImpl assemblyPresetDistributionSecretImplInstance : AssemblyPresetDistributionSecretImpl.values()) {
            if (
                assemblyPresetDistributionSecretImplInstance.getCorrespondingAssemblyPresetDistribution().equals(assemblyPresetDistribution)
            ) {
                return assemblyPresetDistributionSecretImplInstance;
            }
        }

        return null;
    }

    @Override
    public IAssemblyPresetDistributionCalculator getCorrespondingAssemblyPresetDistributionCalculator(
        AssemblyPresetDistribution assemblyPresetDistribution
    ) {
        return getCorrespondingAssemblyPresetDistributionSecretImpl(assemblyPresetDistribution);
    }

    @Override
    public Boolean isTargetCalculatorInstance() {
        return true;
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public Long getAssemblyPresetDistributionCalculatorCount() {
        return Long.valueOf(AssemblyPresetDistributionSecretImpl.values().length);
    }

    @Override
    public Double getSuppliedComponentsAverageDiameterAssemblyVoid(StrandSupply strandSupply, Long assemblyIndex) {
        Long suppliedComponentsCountAtAssembly = getSuppliesCountAtAssembly(strandSupply, assemblyIndex);

        if (suppliedComponentsCountAtAssembly.equals(Long.valueOf(0)) || suppliedComponentsCountAtAssembly.equals(Long.valueOf(1))) {
            return Double.NaN;
        }

        List<AbstractAssembly<?>> assemblies = new ArrayList<AbstractAssembly<?>>(strandSupply.getCoreAssemblies());

        AbstractAssembly<?> assembly = assemblies.get(assemblyIndex.intValue());

        return suggestSuppliedComponentsCountWithMilimeterDiameters(
            assembly.getBeforeThisMilimeterDiameter(),
            strandSupply.getSuppliedComponentsAverageMilimeterDiameter(),
            strandSupply.getDiameterAssemblyStep()
        );
    }

    @Override
    public Double suggestSuppliedComponentsCountWithMilimeterDiameters(
        Double centralMilimeterDiameter,
        Double roundComponentsAverageDiameter,
        Double diameterAssemblyStep
    ) {
        return suggestSuppliedComponentsCount(centralMilimeterDiameter / roundComponentsAverageDiameter, diameterAssemblyStep);
    }

    @Override
    public Double suggestSuppliedComponentsCount(Double centralDiameterInRoundComponentsDiameter, Double diameterAssemblyStep) {
        return (
            Math.PI /
            Math.atan(
                Math.sqrt(
                    (Math.pow(Math.PI / diameterAssemblyStep, 2) + 1) / (Math.pow(centralDiameterInRoundComponentsDiameter + 1, 2) - 1)
                )
            )
        );
    }

    private enum AssemblyPresetDistributionSecretImpl implements IAssemblyPresetDistributionCalculator {
        FOR_1(new AssemblyPresetDistributionPossibility(Double.NaN, new AssemblyPreset(1, 0))),
        FOR_2(new AssemblyPresetDistributionPossibility(Double.NaN, new AssemblyPreset(2, 0))),
        FOR_3(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(3, 0)
            )
        ),
        FOR_4(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(4, 0)
            )
        ),
        FOR_5(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(5, 0)
            )
        ),
        FOR_6(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(6, 0)
            )
        ),
        FOR_7(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(7, 0)
            ),
            new AssemblyPresetDistributionPossibility(Double.NaN, new AssemblyPreset(1, 0), new AssemblyPreset(6, 0))
        ),
        FOR_8(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(8, 0)
            ),
            new AssemblyPresetDistributionPossibility(Double.NaN, new AssemblyPreset(1, 0), new AssemblyPreset(7, 0))
        ),
        FOR_9(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(9, 0)
            ),
            new AssemblyPresetDistributionPossibility(Double.NaN, new AssemblyPreset(1, 0), new AssemblyPreset(8, 0))
        ),
        FOR_10(new AssemblyPresetDistributionPossibility(Double.NaN, new AssemblyPreset(2, 0), new AssemblyPreset(8, 0))),
        FOR_11(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(3, 0),
                new AssemblyPreset(8, 1)
            )
        ),
        FOR_12(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(3, 0),
                new AssemblyPreset(9, 0)
            )
        ),
        FOR_13(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(4, 0),
                new AssemblyPreset(9, 1)
            )
        ),
        FOR_14(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(4, 0),
                new AssemblyPreset(10, 0)
            )
        ),
        FOR_15(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(5, 0),
                new AssemblyPreset(10, 1)
            )
        ),
        FOR_16(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(5, 0),
                new AssemblyPreset(11, 0)
            )
        ),
        FOR_17(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(6, 0),
                new AssemblyPreset(11, 1)
            )
        ),
        FOR_18(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(6, 0),
                new AssemblyPreset(12, 0)
            )
        ),
        FOR_19(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(7, 0),
                new AssemblyPreset(12, 1)
            ),
            new AssemblyPresetDistributionPossibility(
                Double.NaN,
                new AssemblyPreset(1, 0),
                new AssemblyPreset(6, 0),
                new AssemblyPreset(12, 0)
            )
        ),
        FOR_20(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(7, 0),
                new AssemblyPreset(13, 0)
            )
        ),
        FOR_21(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(8, 0),
                new AssemblyPreset(13, 1)
            ),
            new AssemblyPresetDistributionPossibility(
                Double.NaN,
                new AssemblyPreset(1, 0),
                new AssemblyPreset(7, 0),
                new AssemblyPreset(13, 0)
            )
        ),
        FOR_22(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(8, 0),
                new AssemblyPreset(14, 0)
            )
        ),
        FOR_23(
            new AssemblyPresetDistributionPossibility(
                Double.NaN,
                new AssemblyPreset(2, 0),
                new AssemblyPreset(8, 0),
                new AssemblyPreset(13, 1)
            )
        ),
        FOR_24(
            new AssemblyPresetDistributionPossibility(
                Double.NaN,
                new AssemblyPreset(2, 0),
                new AssemblyPreset(8, 0),
                new AssemblyPreset(14, 0)
            )
        ),
        FOR_25(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(3, 0),
                new AssemblyPreset(9, 0),
                new AssemblyPreset(13, 2)
            )
        ),
        FOR_26(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(3, 0),
                new AssemblyPreset(9, 0),
                new AssemblyPreset(14, 1)
            )
        ),
        FOR_27(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(3, 0),
                new AssemblyPreset(9, 0),
                new AssemblyPreset(15, 0)
            )
        ),
        FOR_28(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(4, 0),
                new AssemblyPreset(10, 0),
                new AssemblyPreset(14, 2)
            )
        ),
        FOR_29(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(4, 0),
                new AssemblyPreset(10, 0),
                new AssemblyPreset(15, 1)
            )
        ),
        FOR_30(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(4, 0),
                new AssemblyPreset(10, 0),
                new AssemblyPreset(16, 0)
            )
        ),
        FOR_31(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(5, 0),
                new AssemblyPreset(11, 0),
                new AssemblyPreset(15, 2)
            )
        ),
        FOR_32(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(5, 0),
                new AssemblyPreset(11, 0),
                new AssemblyPreset(16, 1)
            )
        ),
        FOR_33(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(5, 0),
                new AssemblyPreset(11, 0),
                new AssemblyPreset(17, 0)
            )
        ),
        FOR_34(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(6, 0),
                new AssemblyPreset(12, 0),
                new AssemblyPreset(16, 2)
            )
        ),
        FOR_35(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(6, 0),
                new AssemblyPreset(12, 0),
                new AssemblyPreset(17, 1)
            )
        ),
        FOR_36(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(6, 0),
                new AssemblyPreset(12, 0),
                new AssemblyPreset(18, 0)
            )
        ),
        FOR_37(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(7, 0),
                new AssemblyPreset(13, 0),
                new AssemblyPreset(17, 2)
            ),
            new AssemblyPresetDistributionPossibility(
                Double.NaN,
                new AssemblyPreset(1, 0),
                new AssemblyPreset(6, 0),
                new AssemblyPreset(12, 0),
                new AssemblyPreset(18, 0)
            )
        ),
        FOR_38(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(7, 0),
                new AssemblyPreset(13, 0),
                new AssemblyPreset(18, 1)
            )
        ),
        FOR_39(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(7, 0),
                new AssemblyPreset(13, 0),
                new AssemblyPreset(19, 0)
            )
        ),
        FOR_40(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(8, 0),
                new AssemblyPreset(14, 0),
                new AssemblyPreset(18, 2)
            ),
            new AssemblyPresetDistributionPossibility(
                Double.NaN,
                new AssemblyPreset(1, 0),
                new AssemblyPreset(7, 0),
                new AssemblyPreset(13, 0),
                new AssemblyPreset(19, 2)
            )
        ),
        FOR_41(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(8, 0),
                new AssemblyPreset(14, 0),
                new AssemblyPreset(19, 1)
            )
        ),
        FOR_42(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(8, 0),
                new AssemblyPreset(14, 0),
                new AssemblyPreset(20, 0)
            )
        ),
        FOR_43(
            new AssemblyPresetDistributionPossibility(
                Double.NaN,
                new AssemblyPreset(2, 0),
                new AssemblyPreset(8, 0),
                new AssemblyPreset(14, 0),
                new AssemblyPreset(19, 1)
            )
        ),
        FOR_44(
            new AssemblyPresetDistributionPossibility(
                Double.NaN,
                new AssemblyPreset(2, 0),
                new AssemblyPreset(8, 0),
                new AssemblyPreset(14, 0),
                new AssemblyPreset(20, 0)
            )
        ),
        FOR_45(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(9, 0),
                new AssemblyPreset(15, 0),
                new AssemblyPreset(21, 0)
            )
        ),
        FOR_46(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(3, 0),
                new AssemblyPreset(9, 0),
                new AssemblyPreset(15, 0),
                new AssemblyPreset(19, 2)
            )
        ),
        FOR_47(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(3, 0),
                new AssemblyPreset(9, 0),
                new AssemblyPreset(15, 0),
                new AssemblyPreset(20, 1)
            )
        ),
        FOR_48(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(3, 0),
                new AssemblyPreset(9, 0),
                new AssemblyPreset(15, 0),
                new AssemblyPreset(21, 0)
            )
        ),
        FOR_49(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(4, 0),
                new AssemblyPreset(10, 0),
                new AssemblyPreset(16, 0),
                new AssemblyPreset(19, 3)
            )
        ),
        FOR_50(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(4, 0),
                new AssemblyPreset(10, 0),
                new AssemblyPreset(16, 0),
                new AssemblyPreset(20, 2)
            )
        ),
        FOR_51(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(4, 0),
                new AssemblyPreset(10, 0),
                new AssemblyPreset(16, 0),
                new AssemblyPreset(21, 1)
            )
        ),
        FOR_52(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(4, 0),
                new AssemblyPreset(10, 0),
                new AssemblyPreset(16, 0),
                new AssemblyPreset(22, 0)
            )
        ),
        FOR_53(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(5, 0),
                new AssemblyPreset(11, 0),
                new AssemblyPreset(17, 0),
                new AssemblyPreset(20, 3)
            )
        ),
        FOR_54(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(5, 0),
                new AssemblyPreset(11, 0),
                new AssemblyPreset(17, 0),
                new AssemblyPreset(21, 2)
            )
        ),
        FOR_55(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(5, 0),
                new AssemblyPreset(11, 0),
                new AssemblyPreset(17, 0),
                new AssemblyPreset(22, 1)
            )
        ),
        FOR_56(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(5, 0),
                new AssemblyPreset(11, 0),
                new AssemblyPreset(17, 0),
                new AssemblyPreset(23, 0)
            )
        ),
        FOR_57(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(6, 0),
                new AssemblyPreset(12, 0),
                new AssemblyPreset(18, 0),
                new AssemblyPreset(21, 3)
            )
        ),
        FOR_58(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(6, 0),
                new AssemblyPreset(12, 0),
                new AssemblyPreset(18, 0),
                new AssemblyPreset(22, 2)
            )
        ),
        FOR_59(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(6, 0),
                new AssemblyPreset(12, 0),
                new AssemblyPreset(18, 0),
                new AssemblyPreset(23, 1)
            )
        ),
        FOR_60(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(6, 0),
                new AssemblyPreset(12, 0),
                new AssemblyPreset(18, 0),
                new AssemblyPreset(24, 0)
            )
        ),
        FOR_61(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(7, 0),
                new AssemblyPreset(13, 0),
                new AssemblyPreset(19, 0),
                new AssemblyPreset(22, 3)
            ),
            new AssemblyPresetDistributionPossibility(
                Double.NaN,
                new AssemblyPreset(1, 0),
                new AssemblyPreset(6, 0),
                new AssemblyPreset(12, 0),
                new AssemblyPreset(18, 0),
                new AssemblyPreset(24, 3)
            )
        ),
        FOR_62(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(7, 0),
                new AssemblyPreset(13, 0),
                new AssemblyPreset(19, 0),
                new AssemblyPreset(23, 2)
            )
        ),
        FOR_63(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(7, 0),
                new AssemblyPreset(13, 0),
                new AssemblyPreset(19, 0),
                new AssemblyPreset(24, 1)
            )
        ),
        FOR_64(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(7, 0),
                new AssemblyPreset(13, 0),
                new AssemblyPreset(19, 0),
                new AssemblyPreset(25, 0)
            )
        ),
        FOR_65(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(8, 0),
                new AssemblyPreset(14, 0),
                new AssemblyPreset(20, 0),
                new AssemblyPreset(23, 3)
            ),
            new AssemblyPresetDistributionPossibility(
                Double.NaN,
                new AssemblyPreset(1, 0),
                new AssemblyPreset(7, 0),
                new AssemblyPreset(13, 0),
                new AssemblyPreset(19, 0),
                new AssemblyPreset(25, 3)
            )
        ),
        FOR_66(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(8, 0),
                new AssemblyPreset(14, 0),
                new AssemblyPreset(20, 0),
                new AssemblyPreset(24, 2)
            )
        ),
        FOR_67(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(8, 0),
                new AssemblyPreset(14, 0),
                new AssemblyPreset(20, 0),
                new AssemblyPreset(25, 1)
            )
        ),
        FOR_68(
            new AssemblyPresetDistributionPossibility(
                DEFAULT_MILIMETER_DIAMETER_BEFORE_CENTRAL_COMPLETION_COMPONENT,
                new AssemblyPreset(8, 0),
                new AssemblyPreset(14, 0),
                new AssemblyPreset(20, 0),
                new AssemblyPreset(26, 0)
            )
        ),
        FOR_69(
            new AssemblyPresetDistributionPossibility(
                Double.NaN,
                new AssemblyPreset(2, 0),
                new AssemblyPreset(8, 0),
                new AssemblyPreset(14, 0),
                new AssemblyPreset(20, 0),
                new AssemblyPreset(25, 1)
            )
        ),
        FOR_70(
            new AssemblyPresetDistributionPossibility(
                Double.NaN,
                new AssemblyPreset(2, 0),
                new AssemblyPreset(8, 0),
                new AssemblyPreset(14, 0),
                new AssemblyPreset(20, 0),
                new AssemblyPreset(26, 0)
            )
        );

        private AssemblyPresetDistributionPossibility faclutativeCentralCompletionComponentAssemblyPresetDistributionPossibilities;
        private AssemblyPresetDistributionPossibility forcedCentralUtilityComponentAssemblyPresetDistributionPossibilities;

        private AssemblyPresetDistributionSecretImpl() {}

        private AssemblyPresetDistributionSecretImpl(
            AssemblyPresetDistributionPossibility faclutativeCentralCompletionComponentAssemblyPresetDistributionPossibilities
        ) {
            this(faclutativeCentralCompletionComponentAssemblyPresetDistributionPossibilities, null);
        }

        private AssemblyPresetDistributionSecretImpl(
            AssemblyPresetDistributionPossibility faclutativeCentralCompletionComponentAssemblyPresetDistributionPossibilities,
            AssemblyPresetDistributionPossibility forcedCentralUtilityComponentAssemblyPresetDistributionPossibilities
        ) {
            setFaclutativeCentralCompletionComponentAssemblyPresetDistributionPossibilities(
                faclutativeCentralCompletionComponentAssemblyPresetDistributionPossibilities
            );
            setForcedCentralUtilityComponentAssemblyPresetDistributionPossibilities(
                forcedCentralUtilityComponentAssemblyPresetDistributionPossibilities
            );
        }

        public AssemblyPresetDistribution getCorrespondingAssemblyPresetDistribution() {
            return AssemblyPresetDistribution.values()[ordinal()];
        }

        @Override
        public List<AssemblyPresetDistributionPossibility> getAssemblyPresetDistributionPossibilities() {
            return getForcedCentralUtilityComponentAssemblyPresetDistributionPossibilities() == null
                ? List.of(getFaclutativeCentralCompletionComponentAssemblyPresetDistributionPossibilities())
                : List.of(
                    getFaclutativeCentralCompletionComponentAssemblyPresetDistributionPossibilities(),
                    getForcedCentralUtilityComponentAssemblyPresetDistributionPossibilities()
                );
        }

        @Override
        public AssemblyPresetDistributionPossibility getFaclutativeCentralCompletionComponentAssemblyPresetDistributionPossibilities() {
            return this.faclutativeCentralCompletionComponentAssemblyPresetDistributionPossibilities;
        }

        private void setFaclutativeCentralCompletionComponentAssemblyPresetDistributionPossibilities(
            AssemblyPresetDistributionPossibility faclutativeCentralCompletionComponentAssemblyPresetDistributionPossibilities
        ) {
            this.faclutativeCentralCompletionComponentAssemblyPresetDistributionPossibilities =
                faclutativeCentralCompletionComponentAssemblyPresetDistributionPossibilities;
        }

        @Override
        public AssemblyPresetDistributionPossibility getForcedCentralUtilityComponentAssemblyPresetDistributionPossibilities() {
            return this.forcedCentralUtilityComponentAssemblyPresetDistributionPossibilities;
        }

        private void setForcedCentralUtilityComponentAssemblyPresetDistributionPossibilities(
            AssemblyPresetDistributionPossibility forcedCentralUtilityComponentAssemblyPresetDistributionPossibilities
        ) {
            this.forcedCentralUtilityComponentAssemblyPresetDistributionPossibilities =
                forcedCentralUtilityComponentAssemblyPresetDistributionPossibilities;
        }
    }
}
