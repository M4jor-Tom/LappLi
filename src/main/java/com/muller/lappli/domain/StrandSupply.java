package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractAssembly;
import com.muller.lappli.domain.abstracts.AbstractDomainObject;
import com.muller.lappli.domain.abstracts.AbstractNonCentralAssembly;
import com.muller.lappli.domain.abstracts.AbstractOperation;
import com.muller.lappli.domain.abstracts.AbstractSupply;
import com.muller.lappli.domain.enumeration.AssemblyMean;
import com.muller.lappli.domain.enumeration.AssemblyPresetDistribution;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.domain.exception.ImpossibleAssemblyPresetDistributionException;
import com.muller.lappli.domain.interfaces.Designable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
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
public class StrandSupply extends AbstractDomainObject<StrandSupply> implements Designable, Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    private Comparator<AbstractOperation<?>> operationComparator;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "apparitions", nullable = false)
    private Long apparitions;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marking_type", nullable = false)
    private MarkingType markingType;

    @Column(name = "description")
    private String description;

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

    @JsonIgnoreProperties(value = { "ownerStrandSupply", "supplyPosition" }, allowSetters = true)
    @OneToOne(mappedBy = "ownerStrandSupply", cascade = CascadeType.REMOVE)
    private CentralAssembly centralAssembly;

    @OneToMany(mappedBy = "ownerStrandSupply", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ownerStrandSupply" }, allowSetters = true)
    private Set<CoreAssembly> coreAssemblies = new HashSet<>();

    @OneToMany(mappedBy = "ownerStrandSupply", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "supplyPositions", "ownerStrandSupply" }, allowSetters = true)
    private Set<IntersticeAssembly> intersticeAssemblies = new HashSet<>();

    @OneToMany(mappedBy = "ownerStrandSupply", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "material", "ownerStrandSupply" }, allowSetters = true)
    private Set<Sheathing> sheathings = new HashSet<>();

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

    public StrandSupply() {
        this.operationComparator = null;
    }

    @Override
    public StrandSupply getThis() {
        return this;
    }

    @Override
    public String getDesignation() {
        if (getStrand() == null) {
            return getApparitions() + " x [?]";
        }

        return getApparitions().toString() + " x (" + getStrand().getUndividedCountDesignation(getApparitions()) + ")";
    }

    /**
     * As {@link Strand#getOperations()} is a Set, it is unsorted,
     * that's why a sorting algorithm is needed.
     *
     * @return a Comparator for the class AbstractOperation
     */
    private Comparator<AbstractOperation<?>> getOperationComparator() {
        if (operationComparator == null) {
            operationComparator =
                new Comparator<AbstractOperation<?>>() {
                    @Override
                    public int compare(AbstractOperation<?> o1, AbstractOperation<?> o2) {
                        //One is instanceof CentralAssembly, stop thinking. It's it.
                        if (o1 instanceof CentralAssembly) {
                            return -1;
                        } else if (o2 instanceof CentralAssembly) {
                            return 1;
                        } else if (o1 instanceof AbstractNonCentralAssembly && o2 instanceof AbstractNonCentralAssembly) {
                            //o1 and o2 are both Assemblies which are not CenralAssemblies
                            Long o1AssemblyLayer = ((AbstractNonCentralAssembly<?>) o1).getAssemblyLayer();
                            Long o2AssemblyLayer = ((AbstractNonCentralAssembly<?>) o2).getAssemblyLayer();
                            if (o1AssemblyLayer == o2AssemblyLayer) {
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

                            //They're both CoreAssemblies with different assemblyLayers,
                            //let's compare them on that
                            return (int) (o1AssemblyLayer - o2AssemblyLayer);
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

    /**
     * Get the operation before the selected one
     *
     * @param operation the selected operation for which the previous one is seeked
     * @return the seeked operation
     */
    @JsonIgnore
    @JsonIgnoreProperties("ownerStrandSupply")
    public AbstractOperation<?> getLastOperationBefore(AbstractOperation<?> operation) {
        AbstractOperation<?> beforeOperation = null;

        for (AbstractOperation<?> operationChecked : getOperations()) {
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
     * @throws ImpossibleAssemblyPresetDistributionException
     */
    public Double getMilimeterDiameterBefore(AbstractOperation<?> operation) {
        try {
            return getLastOperationBefore(operation).getAfterThisMilimeterDiameter();
        } catch (NullPointerException e) {
            return Double.NaN;
        }
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
        setCentralAssembly(new CentralAssembly());
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

        Long coreAssemblyAssemblyLayer = Long.valueOf(1);
        for (AssemblyPreset assemblyPreset : assemblyPresetDistributionPossibility.getAssemblyPresetsAfterCentral()) {
            addCoreAssemblies(
                new CoreAssembly()
                    .ownerStrandSupply(this)
                    .assemblyLayer(coreAssemblyAssemblyLayer++)
                    .forcedMeanMilimeterComponentDiameter(Double.NaN)
            );
        }

        return this;
    }

    @JsonIgnore
    public AssemblyPresetDistributionPossibility getAssemblyPresetDistributionPossibility() {
        return CalculatorManager.getCalculatorInstance().getAssemblyPresetDistributionPossibility(this);
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

        assemblies.addAll(getCoreAssemblies());
        assemblies.addAll(getIntersticeAssemblies());

        List<AbstractAssembly<?>> sortedAssembliesList = new ArrayList<AbstractAssembly<?>>(assemblies);
        sortedAssembliesList.sort(getOperationComparator());

        return new LinkedHashSet<AbstractAssembly<?>>(sortedAssembliesList);
    }

    /**
     * @return all operations which are not assemblies
     */
    @JsonIgnoreProperties("ownerStrandSupply")
    public Set<AbstractOperation<?>> getNonAssemblyOperations() {
        HashSet<AbstractOperation<?>> operations = new HashSet<>();

        operations.addAll(getSheathings());

        return operations;
    }

    /**
     * @return all the operations
     */
    @JsonIgnoreProperties("ownerStrandSupply")
    public Set<AbstractOperation<?>> getOperations() {
        LinkedHashSet<AbstractOperation<?>> operations = new LinkedHashSet<>();

        operations.addAll(getNonAssemblyOperations());
        operations.addAll(getAssemblies());

        List<AbstractOperation<?>> sortedOperationList = new ArrayList<AbstractOperation<?>>(operations);
        sortedOperationList.sort(getOperationComparator());

        return new LinkedHashSet<AbstractOperation<?>>(sortedOperationList);
    }

    public Long getSuppliedComponentsDividedCount() {
        if (getStrand() != null && getApparitions() != null) {
            return getStrand().getUndividedSuppliedComponentsCount() / getApparitions();
        }

        return DomainManager.ERROR_LONG_POSITIVE_VALUE;
    }

    public Double getSuppliedComponentsAverageMilimeterDiameter() {
        if (getStrand() != null) {
            return getStrand().getSuppliedComponentsMilimeterDiametersSum() / getSuppliedComponentsDividedCount();
        }

        return null;
    }

    public Boolean assemblyPresetDistributionIsPossible() {
        return (
            AssemblyPresetDistribution.forStrandSupply(this).getAssemblyPresetDistributionPossibility(getForceCentralUtilityComponent()) !=
            null
        );
    }

    public StrandSupply checkAssemblyPresetDistributionIsPossible() throws ImpossibleAssemblyPresetDistributionException {
        if (!assemblyPresetDistributionIsPossible()) {
            throw new ImpossibleAssemblyPresetDistributionException();
        }

        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public Long getId() {
        return this.id;
    }

    public StrandSupply id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApparitions() {
        return this.apparitions;
    }

    public StrandSupply apparitions(Long apparitions) {
        this.setApparitions(apparitions);
        return this;
    }

    public void setApparitions(Long apparitions) {
        this.apparitions = apparitions;
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

    public String getDescription() {
        return this.description;
    }

    public StrandSupply description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return id != null && id.equals(((StrandSupply) o).id);
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
