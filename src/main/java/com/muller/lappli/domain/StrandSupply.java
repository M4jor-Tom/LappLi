package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.muller.lappli.domain.abstracts.AbstractAssembly;
import com.muller.lappli.domain.abstracts.AbstractNonCentralAssembly;
import com.muller.lappli.domain.abstracts.AbstractSupply;
import com.muller.lappli.domain.converter.CylindricComponentConverter;
import com.muller.lappli.domain.enumeration.AssemblyMean;
import com.muller.lappli.domain.enumeration.AssemblyPresetDistribution;
import com.muller.lappli.domain.enumeration.CylindricComponentKind;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.domain.exception.ImpossibleAssemblyPresetDistributionException;
import com.muller.lappli.domain.interfaces.CylindricComponent;
import com.muller.lappli.domain.interfaces.Designable;
import com.muller.lappli.domain.interfaces.INonAssemblyOperation;
import com.muller.lappli.domain.interfaces.INonCentralOperation;
import com.muller.lappli.domain.interfaces.IOperation;
import com.muller.lappli.domain.interfaces.PlasticAspectCylindricComponent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A StrandSupply.
 */
@Entity
@Table(name = "strand_supply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StrandSupply extends AbstractSupply<StrandSupply> implements Designable, Serializable {

    private static final long serialVersionUID = 1L;

    private static Comparator<IOperation<?>> operationComparator = null;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marking_type", nullable = false)
    private MarkingType markingType;

    @NotNull
    @Column(name = "diameter_assembly_step", nullable = false)
    private Double diameterAssemblyStep;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "assembly_mean", nullable = false)
    private AssemblyMean assemblyMean;

    @NotNull
    @Column(name = "force_central_utility_component", nullable = false)
    private Boolean forceCentralUtilityComponent;

    @JsonIgnoreProperties(value = { "ownerStrandSupply" }, allowSetters = true)
    @OneToOne(mappedBy = "ownerStrandSupply", cascade = CascadeType.ALL)
    private CentralAssembly centralAssembly;

    @OneToMany(mappedBy = "ownerStrandSupply", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ownerStrandSupply" }, allowSetters = true)
    private Set<CoreAssembly> coreAssemblies = new HashSet<>();

    @OneToMany(mappedBy = "ownerStrandSupply", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "supplyPositions", "ownerStrandSupply" }, allowSetters = true)
    private Set<IntersticeAssembly> intersticeAssemblies = new HashSet<>();

    @OneToMany(mappedBy = "ownerStrandSupply", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ownerStrandSupply" }, allowSetters = true)
    private Set<TapeLaying> tapeLayings = new HashSet<>();

    @OneToMany(mappedBy = "ownerStrandSupply", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ownerStrandSupply" }, allowSetters = true)
    private Set<Screen> screens = new HashSet<>();

    @OneToMany(mappedBy = "ownerStrandSupply", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ownerStrandSupply" }, allowSetters = true)
    private Set<StripLaying> stripLayings = new HashSet<>();

    @OneToMany(mappedBy = "ownerStrandSupply", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ownerStrandSupply" }, allowSetters = true)
    private Set<Plait> plaits = new HashSet<>();

    @OneToMany(mappedBy = "ownerStrandSupply", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ownerStrandSupply" }, allowSetters = true)
    private Set<CarrierPlait> carrierPlaits = new HashSet<>();

    @OneToMany(mappedBy = "ownerStrandSupply", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ownerStrandSupply" }, allowSetters = true)
    private Set<Sheathing> sheathings = new HashSet<>();

    @OneToMany(mappedBy = "ownerStrandSupply", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ownerStrandSupply" }, allowSetters = true)
    private Set<FlatSheathing> flatSheathings = new HashSet<>();

    @OneToMany(mappedBy = "ownerStrandSupply", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ownerStrandSupply" }, allowSetters = true)
    private Set<ContinuityWireLongitLaying> continuityWireLongitLayings = new HashSet<>();

    @OneToMany(mappedBy = "strandSupply")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "ownerCentralAssembly",
            "elementSupply",
            "bangleSupply",
            "customComponentSupply",
            "oneStudySupply",
            "strandSupply",
            "ownerStrand",
            "ownerIntersticeAssembly",
        },
        allowSetters = true
    )
    private Set<SupplyPosition> ownerSupplyPositions = new HashSet<>();

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @NotNull
    @JsonIgnoreProperties(
        value = { "supplyPositions", "coreAssemblies", "intersticeAssemblies", "sheathings", "centralAssembly", "futureStudy" },
        allowSetters = true
    )
    private Strand strand;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "strands", "strandSupplies", "author" }, allowSetters = true)
    private Study study;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public StrandSupply() {
        super();
        setCoreAssemblies(new HashSet<>());
        setIntersticeAssemblies(new HashSet<>());
    }

    @Override
    public StrandSupply getThis() {
        return this;
    }

    @Override
    public CylindricComponent getCylindricComponent() {
        return new CylindricComponent() {
            @Override
            public String getDesignation() {
                if (getStrand() == null) {
                    return "";
                }

                return getStrand().getDesignation();
            }

            @Override
            public Double getMilimeterDiameter() {
                if (getLastOperation() == null) {
                    return Double.NaN;
                }

                return getLastOperation().getAfterThisMilimeterDiameter();
            }

            @Override
            public Double getGramPerMeterLinearMass() {
                // TODO Auto-generated method stub
                return Double.NaN;
            }

            @Override
            public Boolean isUtility() {
                return true;
            }

            @Override
            public CylindricComponentKind getCylindricComponentKind() {
                return CylindricComponentKind.STRAND;
            }
        };
    }

    @Override
    public Optional<PlasticAspectCylindricComponent> getCylindricComponentIfPlasticAspect() {
        if (getLastOperation() == null || !(getLastOperation() instanceof Sheathing)) {
            return Optional.empty();
        }

        Sheathing lastOperationAsSheathing = (Sheathing) getLastOperation();

        return Optional.of(
            CylindricComponentConverter.toPlasticAspectCylindricComponent(getCylindricComponent(), lastOperationAsSheathing.getMaterial())
        );
    }

    @Override
    public Double getMeterPerHourSpeed() {
        // TODO Auto-generated method stub
        return Double.NaN;
    }

    @Override
    public String getDesignation() {
        if (getApparitions() == null) {
            return "";
        } else if (getStrand() == null) {
            return getApparitions() + " x [?]";
        }

        return getApparitions().toString() + " x (" + getStrand().getUndividedCountDesignation(getApparitions()) + ")";
    }

    /**
     * As {@link StrandSupply#getOperations()} is a Set, it is unsorted,
     * that's why a sorting algorithm is needed.
     *
     * @return a Comparator for the class AbstractOperation
     */
    private static Comparator<IOperation<?>> getOperationComparator() {
        if (operationComparator == null) {
            operationComparator =
                new Comparator<IOperation<?>>() {
                    @Override
                    public int compare(IOperation<?> o1, IOperation<?> o2) {
                        //One is instanceof CentralAssembly, stop thinking. It's it.
                        if (o1 instanceof CentralAssembly) {
                            return -1;
                        } else if (o2 instanceof CentralAssembly) {
                            return 1;
                        } else if (o1 instanceof AbstractNonCentralAssembly && o2 instanceof AbstractNonCentralAssembly) {
                            //If they are at the same Assembly level, one MUST be
                            //an IntersticeAssembly
                            if (o1 instanceof IntersticeAssembly && o2 instanceof IntersticeAssembly) {
                                //They're both IntersticeAssemblies,
                                //let's compare them on intersticeLayer
                                Long o1IntersticeLayer = ((IntersticeAssembly) o1).getIntersticeLayer();
                                Long o2IntersticeLayer = ((IntersticeAssembly) o2).getIntersticeLayer();

                                if (o1IntersticeLayer == o2IntersticeLayer) {
                                    //TODO: Handle data corruption,
                                    //this equality cannot be true,
                                    //layer indexation reveals complete equality
                                }

                                return (int) (o1IntersticeLayer - o2IntersticeLayer);
                            } else if (o1 instanceof IntersticeAssembly) {
                                return 1;
                            } else if (o2 instanceof IntersticeAssembly) {
                                return -1;
                            }
                            //TODO: Handle data corruption
                            //at least one must be an IntersticeAssembly
                        }

                        if (o1.getOperationLayer() == o2.getOperationLayer()) {
                            //TODO Handle data corruption
                            //this equality is possible,
                            //if they're not just AbstractOperations,
                            //as it's the case here
                        }

                        //If one or none is an Assembly, operationLayer
                        //will be used to compare operations.
                        return (int) (o1.getOperationLayer() - o2.getOperationLayer());
                    }
                };
        }

        return operationComparator;
    }

    /**
     * @return true if we forced a specific Component, whatever it is,
     * to be at the center of the Strand
     */
    public Boolean forcesCenterDiameterWithSuppliedComponent() {
        try {
            for (AbstractSupply<?> supply : getStrand().getSupplies()) {
                if (getCentralAssembly().getSupplyPosition().getSupply() == supply) {
                    //If one of the CoreAssemblies' dedicated supplies IS the same supply
                    //(having same ref) than the one used in the CentralAssembly, it means that
                    //automatic assembly is used
                    return false;
                }
            }
        } catch (NullPointerException e) {
            //If there's no Supply at the center of the Strand,
            //it means that the center diameter is calculated
            //after AssemblyPresetDistributionPossibility is chosen.
            //We know it's one without any center.
            return false;
        }

        //If not, it means we especialy dedicate a Supply to be in the center
        return true;
    }

    /**
     * Tells if the Strand shall use AssemblyPresetDistributionPossibilities
     * to design the Strand's Assemlies
     *
     * @return a Boolean
     */
    public Boolean shallUseAssemblyPresetDistributionPossibilities() {
        return !forcesCenterDiameterWithSuppliedComponent();
    }

    @JsonIgnore
    @JsonIgnoreProperties("ownerStrandSupply")
    public IOperation<?> getLastOperation() {
        IOperation<?> lastOperation = null;

        for (IOperation<?> operation : getOperations()) {
            lastOperation = operation;
        }

        return lastOperation;
    }

    /**
     * Get the operation before the selected one
     *
     * @param operation the selected operation for which the previous one is seeked
     * @return the seeked operation
     */
    @JsonIgnore
    @JsonIgnoreProperties("ownerStrandSupply")
    public IOperation<?> getLastOperationBefore(IOperation<?> operation) {
        IOperation<?> beforeOperation = null;

        for (IOperation<?> operationChecked : getOperations()) {
            if (operationChecked.equals(operation)) {
                //Current operation is the searched one, we seek the prefious one
                return beforeOperation;
            }

            //Cycle the cursor
            beforeOperation = operationChecked;
        }

        return null;
    }

    /**
     * Get the diameter under an operation
     *
     * @param operation under which we want a diameter
     * @return the diameter in milimeter
     */
    public Double getMilimeterDiameterBefore(IOperation<?> operation) {
        IOperation<?> lastOperationBeforeThis = getLastOperationBefore(operation);

        if (lastOperationBeforeThis == null) {
            return Double.NaN;
        }

        return lastOperationBeforeThis.getAfterThisMilimeterDiameter();
    }

    @JsonIgnoreProperties("ownerStrandSupply")
    public CoreAssembly getFirstCoreAssembly() {
        for (CoreAssembly coreAssembly : getCoreAssemblies()) {
            return coreAssembly;
        }

        return null;
    }

    @JsonIgnoreProperties("ownerStrandSupply")
    public CoreAssembly getLastCoreAssembly() {
        CoreAssembly lastCoreAssembly = null;

        for (CoreAssembly coreAssembly : getCoreAssemblies()) {
            lastCoreAssembly = coreAssembly;
        }

        return lastCoreAssembly;
    }

    public StrandSupply resetAssemblies() {
        setCentralAssembly(null);
        setCoreAssemblies(new HashSet<CoreAssembly>());

        //try {
        //No Exception shall be thrown here
        setIntersticeAssemblies(new HashSet<IntersticeAssembly>());
        //} catch (NoIntersticeAvailableException e) {
        //DomainManager.noticeInPrompt("THIS EXCEPTION IS ABNORMAL, CHECK Strand.setIntersticeAssemblies");
        //e.printStackTrace();
        //}

        return this;
    }

    public StrandSupply autoGenerateAssemblies() {
        resetAssemblies();

        AssemblyPresetDistributionPossibility assemblyPresetDistributionPossibility = getAssemblyPresetDistributionPossibility();

        if (assemblyPresetDistributionPossibility.hasCentralComponent()) {
            setCentralAssembly(
                new CentralAssembly().supplyPosition(new SupplyPosition().supplyApparitionsUsage(Long.valueOf(1)).supply(null))
            );
        }

        Long coreAssemblyOperationLayer = Long.valueOf(1);
        for (AssemblyPreset assemblyPreset : assemblyPresetDistributionPossibility.getAssemblyPresetsAfterCentral()) {
            CoreAssembly coreAssembly = new CoreAssembly()
                .ownerStrandSupply(this)
                .operationLayer(coreAssemblyOperationLayer++)
                .forcedMeanMilimeterComponentDiameter(Double.NaN);

            moveOthersOperationLayersForThisOne(coreAssembly);
            addCoreAssemblies(coreAssembly);
        }

        return this;
    }

    @JsonIgnore
    public AssemblyPresetDistributionPossibility getAssemblyPresetDistributionPossibility() {
        return CalculatorManager.getCalculatorInstance().getAssemblyPresetDistributionPossibility(this);
    }

    @JsonIgnoreProperties("ownerStrandSupply")
    public Set<INonCentralOperation<?>> getNonCentralOperations() {
        Set<INonCentralOperation<?>> nonCentralOperations = new LinkedHashSet<INonCentralOperation<?>>();

        nonCentralOperations.addAll(getNonCentralAssemblies());
        nonCentralOperations.addAll(getNonAssemblyOperations());

        List<INonCentralOperation<?>> sortedNonCentralOperations = new ArrayList<INonCentralOperation<?>>(nonCentralOperations);
        sortedNonCentralOperations.sort(getOperationComparator());

        return new LinkedHashSet<INonCentralOperation<?>>(sortedNonCentralOperations);
    }

    /**
     * @return all the assemblies
     */
    @JsonIgnoreProperties("ownerStrandSupply")
    public Set<AbstractNonCentralAssembly<?>> getNonCentralAssemblies() {
        Set<AbstractNonCentralAssembly<?>> assemblies = new HashSet<>();

        //[NON_CENTRAL_ASSEMBLIES]
        assemblies.addAll(getCoreAssemblies());
        assemblies.addAll(getIntersticeAssemblies());

        return assemblies;
    }

    /**
     * @return all the assemblies
     */
    @JsonIgnoreProperties("ownerStrandSupply")
    public Set<AbstractAssembly<?>> getAssemblies() {
        LinkedHashSet<AbstractAssembly<?>> assemblies = new LinkedHashSet<>();

        if (getCentralAssembly() != null) {
            assemblies.add(getCentralAssembly());
        }

        assemblies.addAll(getNonCentralAssemblies());

        List<AbstractAssembly<?>> sortedAssembliesList = new ArrayList<AbstractAssembly<?>>(assemblies);
        sortedAssembliesList.sort(getOperationComparator());

        return new LinkedHashSet<AbstractAssembly<?>>(sortedAssembliesList);
    }

    /**
     * @return all operations which are not assemblies
     */
    @JsonIgnore
    public Set<INonAssemblyOperation<?>> getNonAssemblyOperations() {
        HashSet<INonAssemblyOperation<?>> operations = new HashSet<>();

        //[NON_ASSEMBLY_OPERATION]
        operations.addAll(getTapeLayings());
        operations.addAll(getScreens());
        operations.addAll(getStripLayings());
        operations.addAll(getPlaits());
        operations.addAll(getCarrierPlaits());
        operations.addAll(getSheathings());
        operations.addAll(getFlatSheathings());
        operations.addAll(getContinuityWireLongitLayings());

        return operations;
    }

    /**
     * @return all the operations
     */
    @JsonIgnoreProperties("ownerStrandSupply")
    public Set<IOperation<?>> getOperations() {
        LinkedHashSet<IOperation<?>> operations = new LinkedHashSet<>();

        for (INonAssemblyOperation<?> nonAssemblyOperation : getNonAssemblyOperations()) {
            operations.add(nonAssemblyOperation.toOperation());
        }
        operations.addAll(getAssemblies());

        List<IOperation<?>> sortedOperationList = new ArrayList<IOperation<?>>(operations);
        sortedOperationList.sort(getOperationComparator());

        return new LinkedHashSet<IOperation<?>>(sortedOperationList);
    }

    @JsonProperty("isFlat")
    public Boolean isFlat() {
        for (IOperation<?> operation : getOperations()) {
            if (operation instanceof FlatSheathing) {
                return true;
            }
        }

        return false;
    }

    @JsonProperty("isCylindric")
    public Boolean isCylindric() {
        return !getAssemblies().equals(Set.of());
    }

    @JsonProperty("couldBeFlat")
    public Boolean couldBeFlat() {
        return !isCylindric();
    }

    @JsonProperty("couldBeCylindric")
    public Boolean couldBeCylindric() {
        return !isFlat();
    }

    public Boolean isUsedOperationLayer(Long operationLayer) {
        if (operationLayer == null) {
            return false;
        }

        for (IOperation<?> operation : getOperations()) {
            if (operationLayer.equals(operation.getOperationLayer())) {
                return true;
            }
        }

        return false;
    }

    private List<Long> getOperationLayerList() {
        List<Long> operationlayerList = new ArrayList<Long>();

        for (IOperation<?> operationToGetLayer : getOperations()) {
            operationlayerList.add(operationToGetLayer.getOperationLayer());
        }

        return operationlayerList;
    }

    private Long getMaxOperationLayer() {
        Long maxOperationLayer = DomainManager.ERROR_LONG_POSITIVE_VALUE;

        for (Long operationLayer : getOperationLayerList()) {
            maxOperationLayer = Math.max(maxOperationLayer, operationLayer);
        }

        return maxOperationLayer;
    }

    public StrandSupply prepareInsertNonCentralOperation(INonCentralOperation<?> nonCentralOperation) {
        if (nonCentralOperation == null) {
            return this;
        } else if (!nonCentralOperation.isOperationLayerDefined()) {
            provideOperationLayerIfNeededTo(nonCentralOperation);
        } else if (isUsedOperationLayer(nonCentralOperation.getOperationLayer())) {
            moveOthersOperationLayersForThisOne(nonCentralOperation);
        }

        return this;
    }

    public void provideOperationLayerIfNeededTo(INonCentralOperation<?> nonCentralOperation) {
        if (nonCentralOperation == null) {
            throw new NullPointerException("nonCentralOperation is null");
        }

        if (nonCentralOperation.isOperationLayerDefined()) {
            return;
        }

        nonCentralOperation.setOperationLayer(getMaxOperationLayer() + 1);
    }

    public void moveOthersOperationLayersForThisOne(INonCentralOperation<?> nonCentralOperation) {
        if (nonCentralOperation == null) {
            throw new NullPointerException("nonCentralOperation is null");
        }

        for (INonCentralOperation<?> nonCentralOperationToMoveLayer : getNonCentralOperations()) {
            if (
                nonCentralOperationToMoveLayer.getOperationLayer() >= nonCentralOperation.getOperationLayer() &&
                !nonCentralOperationToMoveLayer.equals(nonCentralOperation)
            ) {
                nonCentralOperationToMoveLayer.setOperationLayer(nonCentralOperationToMoveLayer.getOperationLayer() + 1);
            }
        }
    }

    public Long getSuppliedComponentsDividedCount() {
        if (getStrand() != null && getApparitions() != null) {
            return getStrand().getUndividedSuppliedComponentsCount() / getApparitions();
        }

        return DomainManager.ERROR_LONG_POSITIVE_VALUE;
    }

    public Boolean assemblyPresetDistributionIsPossible() {
        AssemblyPresetDistribution assemblyPresetDistribution = AssemblyPresetDistribution.forStrandSupply(this);

        if (assemblyPresetDistribution == null) {
            return false;
        }

        return (assemblyPresetDistribution.getAssemblyPresetDistributionPossibility(getForceCentralUtilityComponent()) != null);
    }

    public StrandSupply checkAssemblyPresetDistributionIsPossible() throws ImpossibleAssemblyPresetDistributionException {
        if (!assemblyPresetDistributionIsPossible()) {
            throw new ImpossibleAssemblyPresetDistributionException();
        }

        return this;
    }

    @JsonProperty("hasAssemblies")
    public Boolean hasAssemblies() {
        if (getCentralAssembly() != null) {
            return true;
        } else if (getCoreAssemblies() != null) {
            return !getCoreAssemblies().isEmpty();
        } else if (getIntersticeAssemblies() != null) {
            return !getIntersticeAssemblies().isEmpty();
        }

        return false;
    }

    public MarkingType getMarkingType() {
        return this.markingType;
    }

    public StrandSupply markingType(MarkingType markingType) {
        this.setMarkingType(markingType);
        return this;
    }

    public void setMarkingType(MarkingType markingType) {
        this.markingType = markingType;
    }

    public Double getDiameterAssemblyStep() {
        return this.diameterAssemblyStep;
    }

    public StrandSupply diameterAssemblyStep(Double diameterAssemblyStep) {
        this.setDiameterAssemblyStep(diameterAssemblyStep);
        return this;
    }

    public void setDiameterAssemblyStep(Double diameterAssemblyStep) {
        this.diameterAssemblyStep = diameterAssemblyStep;
    }

    public AssemblyMean getAssemblyMean() {
        return this.assemblyMean;
    }

    public StrandSupply assemblyMean(AssemblyMean assemblyMean) {
        this.setAssemblyMean(assemblyMean);
        return this;
    }

    public void setAssemblyMean(AssemblyMean assemblyMean) {
        this.assemblyMean = assemblyMean;
    }

    public Boolean getForceCentralUtilityComponent() {
        return this.forceCentralUtilityComponent;
    }

    public StrandSupply forceCentralUtilityComponent(Boolean forceCentralUtilityComponent) {
        this.setForceCentralUtilityComponent(forceCentralUtilityComponent);
        return this;
    }

    public void setForceCentralUtilityComponent(Boolean forceCentralUtilityComponent) {
        this.forceCentralUtilityComponent = forceCentralUtilityComponent;
    }

    public Set<CoreAssembly> getCoreAssemblies() {
        return this.coreAssemblies;
    }

    public void setCoreAssemblies(Set<CoreAssembly> coreAssemblies) {
        if (this.coreAssemblies != null) {
            this.coreAssemblies.forEach(i -> i.setOwnerStrandSupply(null));
        }
        if (coreAssemblies != null) {
            coreAssemblies.forEach(i -> i.setOwnerStrandSupply(this));
        }
        this.coreAssemblies = coreAssemblies;
    }

    public StrandSupply coreAssemblies(Set<CoreAssembly> coreAssemblies) {
        this.setCoreAssemblies(coreAssemblies);
        return this;
    }

    public StrandSupply addCoreAssemblies(CoreAssembly coreAssembly) {
        this.coreAssemblies.add(coreAssembly);
        coreAssembly.setOwnerStrandSupply(this);
        return this;
    }

    public StrandSupply removeCoreAssemblies(CoreAssembly coreAssembly) {
        this.coreAssemblies.remove(coreAssembly);
        coreAssembly.setOwnerStrandSupply(null);
        return this;
    }

    public Set<IntersticeAssembly> getIntersticeAssemblies() {
        return this.intersticeAssemblies;
    }

    public void setIntersticeAssemblies(Set<IntersticeAssembly> intersticeAssemblies) {
        if (this.intersticeAssemblies != null) {
            this.intersticeAssemblies.forEach(i -> i.setOwnerStrandSupply(null));
        }
        if (intersticeAssemblies != null) {
            intersticeAssemblies.forEach(i -> i.setOwnerStrandSupply(this));
        }
        this.intersticeAssemblies = intersticeAssemblies;
    }

    public StrandSupply intersticeAssemblies(Set<IntersticeAssembly> intersticeAssemblies) {
        this.setIntersticeAssemblies(intersticeAssemblies);
        return this;
    }

    public StrandSupply addIntersticeAssemblies(IntersticeAssembly intersticeAssembly) {
        this.intersticeAssemblies.add(intersticeAssembly);
        intersticeAssembly.setOwnerStrandSupply(this);
        return this;
    }

    public StrandSupply removeIntersticeAssemblies(IntersticeAssembly intersticeAssembly) {
        this.intersticeAssemblies.remove(intersticeAssembly);
        intersticeAssembly.setOwnerStrandSupply(null);
        return this;
    }

    public Set<TapeLaying> getTapeLayings() {
        return this.tapeLayings;
    }

    public void setTapeLayings(Set<TapeLaying> tapeLayings) {
        if (this.tapeLayings != null) {
            this.tapeLayings.forEach(i -> i.setOwnerStrandSupply(null));
        }
        if (tapeLayings != null) {
            tapeLayings.forEach(i -> i.setOwnerStrandSupply(this));
        }
        this.tapeLayings = tapeLayings;
    }

    public StrandSupply tapeLayings(Set<TapeLaying> tapeLayings) {
        this.setTapeLayings(tapeLayings);
        return this;
    }

    public StrandSupply addTapeLayings(TapeLaying tapeLaying) {
        this.tapeLayings.add(tapeLaying);
        tapeLaying.setOwnerStrandSupply(this);
        return this;
    }

    public StrandSupply removeTapeLayings(TapeLaying tapeLaying) {
        this.tapeLayings.remove(tapeLaying);
        tapeLaying.setOwnerStrandSupply(null);
        return this;
    }

    public Set<Screen> getScreens() {
        return this.screens;
    }

    public void setScreens(Set<Screen> screens) {
        if (this.screens != null) {
            this.screens.forEach(i -> i.setOwnerStrandSupply(null));
        }
        if (screens != null) {
            screens.forEach(i -> i.setOwnerStrandSupply(this));
        }
        this.screens = screens;
    }

    public StrandSupply screens(Set<Screen> screens) {
        this.setScreens(screens);
        return this;
    }

    public StrandSupply addScreens(Screen screen) {
        this.screens.add(screen);
        screen.setOwnerStrandSupply(this);
        return this;
    }

    public StrandSupply removeScreens(Screen screen) {
        this.screens.remove(screen);
        screen.setOwnerStrandSupply(null);
        return this;
    }

    public Set<StripLaying> getStripLayings() {
        return this.stripLayings;
    }

    public void setStripLayings(Set<StripLaying> stripLayings) {
        if (this.stripLayings != null) {
            this.stripLayings.forEach(i -> i.setOwnerStrandSupply(null));
        }
        if (stripLayings != null) {
            stripLayings.forEach(i -> i.setOwnerStrandSupply(this));
        }
        this.stripLayings = stripLayings;
    }

    public StrandSupply stripLayings(Set<StripLaying> stripLayings) {
        this.setStripLayings(stripLayings);
        return this;
    }

    public StrandSupply addStripLayings(StripLaying stripLaying) {
        this.stripLayings.add(stripLaying);
        stripLaying.setOwnerStrandSupply(this);
        return this;
    }

    public StrandSupply removeStripLayings(StripLaying stripLaying) {
        this.stripLayings.remove(stripLaying);
        stripLaying.setOwnerStrandSupply(null);
        return this;
    }

    public Set<Plait> getPlaits() {
        return this.plaits;
    }

    public void setPlaits(Set<Plait> plaits) {
        if (this.plaits != null) {
            this.plaits.forEach(i -> i.setOwnerStrandSupply(null));
        }
        if (plaits != null) {
            plaits.forEach(i -> i.setOwnerStrandSupply(this));
        }
        this.plaits = plaits;
    }

    public StrandSupply plaits(Set<Plait> plaits) {
        this.setPlaits(plaits);
        return this;
    }

    public StrandSupply addPlaits(Plait plait) {
        this.plaits.add(plait);
        plait.setOwnerStrandSupply(this);
        return this;
    }

    public StrandSupply removePlaits(Plait plait) {
        this.plaits.remove(plait);
        plait.setOwnerStrandSupply(null);
        return this;
    }

    public Set<CarrierPlait> getCarrierPlaits() {
        return this.carrierPlaits;
    }

    public void setCarrierPlaits(Set<CarrierPlait> carrierPlaits) {
        if (this.carrierPlaits != null) {
            this.carrierPlaits.forEach(i -> i.setOwnerStrandSupply(null));
        }
        if (carrierPlaits != null) {
            carrierPlaits.forEach(i -> i.setOwnerStrandSupply(this));
        }
        this.carrierPlaits = carrierPlaits;
    }

    public StrandSupply carrierPlaits(Set<CarrierPlait> carrierPlaits) {
        this.setCarrierPlaits(carrierPlaits);
        return this;
    }

    public StrandSupply addCarrierPlaits(CarrierPlait carrierPlait) {
        this.carrierPlaits.add(carrierPlait);
        carrierPlait.setOwnerStrandSupply(this);
        return this;
    }

    public StrandSupply removeCarrierPlaits(CarrierPlait carrierPlait) {
        this.carrierPlaits.remove(carrierPlait);
        carrierPlait.setOwnerStrandSupply(null);
        return this;
    }

    public Set<Sheathing> getSheathings() {
        return this.sheathings;
    }

    public void setSheathings(Set<Sheathing> sheathings) {
        if (this.sheathings != null) {
            this.sheathings.forEach(i -> i.setOwnerStrandSupply(null));
        }
        if (sheathings != null) {
            sheathings.forEach(i -> i.setOwnerStrandSupply(this));
        }
        this.sheathings = sheathings;
    }

    public StrandSupply sheathings(Set<Sheathing> sheathings) {
        this.setSheathings(sheathings);
        return this;
    }

    public StrandSupply addSheathings(Sheathing sheathing) {
        this.sheathings.add(sheathing);
        sheathing.setOwnerStrandSupply(this);
        return this;
    }

    public StrandSupply removeSheathings(Sheathing sheathing) {
        this.sheathings.remove(sheathing);
        sheathing.setOwnerStrandSupply(null);
        return this;
    }

    public Set<FlatSheathing> getFlatSheathings() {
        return this.flatSheathings;
    }

    public void setFlatSheathings(Set<FlatSheathing> flatSheathings) {
        if (this.flatSheathings != null) {
            this.flatSheathings.forEach(i -> i.setOwnerStrandSupply(null));
        }
        if (flatSheathings != null) {
            flatSheathings.forEach(i -> i.setOwnerStrandSupply(this));
        }
        this.flatSheathings = flatSheathings;
    }

    public StrandSupply flatSheathings(Set<FlatSheathing> flatSheathings) {
        this.setFlatSheathings(flatSheathings);
        return this;
    }

    public StrandSupply addFlatSheathings(FlatSheathing flatSheathing) {
        this.flatSheathings.add(flatSheathing);
        flatSheathing.setOwnerStrandSupply(this);
        return this;
    }

    public StrandSupply removeFlatSheathings(FlatSheathing flatSheathing) {
        this.flatSheathings.remove(flatSheathing);
        flatSheathing.setOwnerStrandSupply(null);
        return this;
    }

    public Set<ContinuityWireLongitLaying> getContinuityWireLongitLayings() {
        return this.continuityWireLongitLayings;
    }

    public void setContinuityWireLongitLayings(Set<ContinuityWireLongitLaying> continuityWireLongitLayings) {
        if (this.continuityWireLongitLayings != null) {
            this.continuityWireLongitLayings.forEach(i -> i.setOwnerStrandSupply(null));
        }
        if (continuityWireLongitLayings != null) {
            continuityWireLongitLayings.forEach(i -> i.setOwnerStrandSupply(this));
        }
        this.continuityWireLongitLayings = continuityWireLongitLayings;
    }

    public StrandSupply continuityWireLongitLayings(Set<ContinuityWireLongitLaying> continuityWireLongitLayings) {
        this.setContinuityWireLongitLayings(continuityWireLongitLayings);
        return this;
    }

    public StrandSupply addContinuityWireLongitLayings(ContinuityWireLongitLaying continuityWireLongitLaying) {
        this.continuityWireLongitLayings.add(continuityWireLongitLaying);
        continuityWireLongitLaying.setOwnerStrandSupply(this);
        return this;
    }

    public StrandSupply removeContinuityWireLongitLayings(ContinuityWireLongitLaying continuityWireLongitLaying) {
        this.continuityWireLongitLayings.remove(continuityWireLongitLaying);
        continuityWireLongitLaying.setOwnerStrandSupply(null);
        return this;
    }

    @Override
    public Set<SupplyPosition> getOwnerSupplyPositions() {
        return this.ownerSupplyPositions;
    }

    public void setOwnerSupplyPositions(Set<SupplyPosition> supplyPositions) {
        if (this.ownerSupplyPositions != null) {
            this.ownerSupplyPositions.forEach(i -> i.setStrandSupply(null));
        }
        if (supplyPositions != null) {
            supplyPositions.forEach(i -> i.setStrandSupply(this));
        }
        this.ownerSupplyPositions = supplyPositions;
    }

    public StrandSupply ownerSupplyPositions(Set<SupplyPosition> supplyPositions) {
        this.setOwnerSupplyPositions(supplyPositions);
        return this;
    }

    public StrandSupply addOwnerSupplyPosition(SupplyPosition supplyPosition) {
        this.ownerSupplyPositions.add(supplyPosition);
        supplyPosition.setStrandSupply(this);
        return this;
    }

    public StrandSupply removeOwnerSupplyPosition(SupplyPosition supplyPosition) {
        this.ownerSupplyPositions.remove(supplyPosition);
        supplyPosition.setStrandSupply(null);
        return this;
    }

    public Strand getStrand() {
        return this.strand;
    }

    public void setStrand(Strand strand) {
        this.strand = strand;
    }

    public StrandSupply strand(Strand strand) {
        this.setStrand(strand);
        return this;
    }

    public CentralAssembly getCentralAssembly() {
        return this.centralAssembly;
    }

    public void setCentralAssembly(CentralAssembly centralAssembly) {
        if (this.centralAssembly != null) {
            this.centralAssembly.setOwnerStrandSupply(null);
        }
        if (centralAssembly != null) {
            centralAssembly.setOwnerStrandSupply(this);
        }
        this.centralAssembly = centralAssembly;
    }

    public StrandSupply centralAssembly(CentralAssembly centralAssembly) {
        this.setCentralAssembly(centralAssembly);
        return this;
    }

    public Study getStudy() {
        return this.study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public StrandSupply study(Study study) {
        this.setStudy(study);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StrandSupply)) {
            return false;
        }
        return getId() != null && getId().equals(((StrandSupply) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StrandSupply{" +
            "id=" + getId() +
            ", apparitions=" + getApparitions() +
            ", markingType='" + getMarkingType() + "'" +
            ", description='" + getDescription() + "'" +
            ", diameterAssemblyStep=" + getDiameterAssemblyStep() +
            ", assemblyMean='" + getAssemblyMean() + "'" +
            ", forceCentralUtilityComponent='" + getForceCentralUtilityComponent() + "'" +
            "}";
    }
}
