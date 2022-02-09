package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractAssembly;
import com.muller.lappli.domain.abstracts.AbstractDomainObject;
import com.muller.lappli.domain.abstracts.AbstractOperation;
import com.muller.lappli.domain.abstracts.AbstractSupply;
import com.muller.lappli.domain.enumeration.AssemblyMean;
import com.muller.lappli.domain.enumeration.SupplyKind;
import com.muller.lappli.domain.exception.NoIntersticeAvailableException;
import com.muller.lappli.domain.interfaces.ISupplyPositionOwner;
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
 * A Strand.
 */
@Entity
@Table(name = "strand")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Strand extends AbstractDomainObject<Strand> implements ISupplyPositionOwner, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "diameter_assembly_step", nullable = false)
    private Double diameterAssemblyStep;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "assembly_mean", nullable = false)
    private AssemblyMean assemblyMean;

    @OneToMany(mappedBy = "ownerStrand", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ownerCentralAssembly", "ownerStrand", "ownerIntersticeAssembly" }, allowSetters = true)
    private Set<SupplyPosition> supplyPositions = new HashSet<>();

    @OneToMany(mappedBy = "ownerStrand", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ownerStrand" }, allowSetters = true)
    private Set<CoreAssembly> coreAssemblies = new HashSet<>();

    @OneToMany(mappedBy = "ownerStrand", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "supplyPositions", "ownerStrand" }, allowSetters = true)
    private Set<IntersticeAssembly> intersticeAssemblies = new HashSet<>();

    @OneToMany(mappedBy = "ownerStrand", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "material", "ownerStrand" }, allowSetters = true)
    private Set<Sheathing> sheathings = new HashSet<>();

    @JsonIgnoreProperties(value = { "ownerStrand", "supplyPosition" }, allowSetters = true)
    @OneToOne(mappedBy = "ownerStrand")
    private CentralAssembly centralAssembly;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "strands", "strandSupplies", "author" }, allowSetters = true)
    private Study futureStudy;

    public Strand() {
        //initSupplyPositionsIfEmpty();
    }

    @Override
    public Strand getThis() {
        return this;
    }

    @JsonIgnore
    public Strand initSupplyPositionsIfEmpty() {
        if (getSupplyPositions() == null || getSupplyPositions().isEmpty()) {
            addSupplyPositions(new SupplyPosition().supplyApparitionsUsage(Long.valueOf(0)));
        }

        return this;
    }

    private Comparator<AbstractOperation<?>> getOperationComparator() {
        return new Comparator<AbstractOperation<?>>() {
            @Override
            public int compare(AbstractOperation<?> o1, AbstractOperation<?> o2) {
                if (o1 instanceof AbstractAssembly && o2 instanceof AbstractAssembly) {
                    if (o1 instanceof IntersticeAssembly && o2 instanceof IntersticeAssembly) {
                        //Both o1 & o2 are IntersticeAssemblies, have to deduct by layer number
                        return (int) (((IntersticeAssembly) o1).getIntersticeLayer() - ((IntersticeAssembly) o2).getIntersticeLayer());
                    } else if (o1 instanceof IntersticeAssembly || o2 instanceof IntersticeAssembly) {
                        //Case when both are IntersticeAssemblies is nailed up there,
                        //so this case is when ONLY ONE is an IntersticeAssembly
                        if (o1 instanceof IntersticeAssembly) {
                            //o2 cannot be IntersticeAssembly there
                            return 1;
                        }
                        //o1 cannot be IntersticeAssembly there
                        return -1;
                    } else {
                        //None of o1 & o2 are IntersticeAssemblies,
                        //the use of AbstractAssembly.assemblyLayer will suffise
                        return (int) (((AbstractAssembly<?>) o1).getAssemblyLayer() - ((AbstractAssembly<?>) o2).getAssemblyLayer());
                    }
                }

                //If one is an Assembly, its operationLayer will be lower than
                //a non-assembly operation.
                //Otherwise, if both operations are non-assembly, hope
                //it's not equal, in that case error checking failed and this would result
                //in a randomly sorted non-assembly operation list
                return (int) (o1.getOperationLayer() - o2.getOperationLayer());
            }
        };
    }

    /**
     * Get the operation before the selected one
     *
     * @param operation the selected operation for which the previous one is seeked
     * @return the seeked operation
     */
    @JsonIgnore
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
     */
    public Double getMilimeterDiameterBefore(AbstractOperation<?> operation) {
        try {
            return getLastOperationBefore(operation).getAfterThisMilimeterDiameter();
        } catch (NullPointerException e) {
            return Double.NaN;
        }
    }

    @JsonIgnoreProperties("ownerStrand")
    public CoreAssembly getLastCoreAssembly() {
        CoreAssembly lastCoreAssembly = null;

        for (CoreAssembly coreAssembly : getCoreAssemblies()) {
            lastCoreAssembly = coreAssembly;
        }

        return lastCoreAssembly;
    }

    public List<Long> getSuppliesCountsCommonDividers() {
        List<Long> commonDividers = new ArrayList<Long>();

        for (AbstractSupply<?> supply : getSupplies()) {
            //For each supply
            List<Long> supplyDividers = new ArrayList<Long>();

            if (supply != null) {
                for (Long testValue = Long.valueOf(1); testValue < supply.getApparitions() + Long.valueOf(1); testValue++) {
                    //For each of its dividers
                    if (supply.getApparitions() % testValue == Long.valueOf(0)) {
                        //Store it
                        supplyDividers.add(testValue);
                    }
                }

                if (commonDividers.isEmpty()) {
                    //If no common divider was stored
                    commonDividers = supplyDividers;
                } else {
                    List<Long> commonDividersNoLongerCommon = new ArrayList<Long>();
                    for (Long commonDivider : commonDividers) {
                        //For each common divider

                        if (!supplyDividers.contains(commonDivider)) {
                            //Drop it if it is not in the new supply dividers list
                            commonDividersNoLongerCommon.add(commonDivider);
                        }
                    }

                    for (Long commonDividerNoLongerCommon : commonDividersNoLongerCommon) {
                        commonDividers.remove(commonDividerNoLongerCommon);
                    }
                }
            }
        }

        return commonDividers;
    }

    public Long getSuppliesCount() {
        Long count = Long.valueOf(0);

        for (SupplyPosition supplyPosition : getSupplyPositions()) {
            if (supplyPosition.getSupply() != null) {
                count += supplyPosition.getSupply().getApparitions();
            } else {
                return DomainManager.ERROR_LONG_POSITIVE_VALUE;
            }
        }

        return count;
    }

    @JsonIgnore
    public Set<AbstractSupply<?>> getSupplies() {
        HashSet<AbstractSupply<?>> supplies = new HashSet<>();

        for (SupplyPosition supplyPosition : getSupplyPositions()) {
            supplies.add(supplyPosition.getSupply());
        }

        return supplies;
    }

    /**
     * @return all the assemblies
     */
    public Set<AbstractAssembly<?>> getAssemblies() {
        HashSet<AbstractAssembly<?>> assemblies = new HashSet<>();

        if (getCentralAssembly() != null) {
            assemblies.add(getCentralAssembly());
        }

        assemblies.addAll(getCoreAssemblies());
        assemblies.addAll(getIntersticeAssemblies());

        return assemblies;
    }

    /**
     * @return all operations which are not assemblies
     */
    public Set<AbstractOperation<?>> getNonAssemblyOperations() {
        HashSet<AbstractOperation<?>> operations = new HashSet<>();

        operations.addAll(getSheathings());

        return operations;
    }

    /**
     * @return all the operations
     */
    public Set<AbstractOperation<?>> getOperations() {
        LinkedHashSet<AbstractOperation<?>> operations = new LinkedHashSet<>();

        operations.addAll(getNonAssemblyOperations());
        operations.addAll(getAssemblies());

        List<AbstractOperation<?>> sortedOperationList = new ArrayList<AbstractOperation<?>>(operations);
        sortedOperationList.sort(getOperationComparator());

        return new LinkedHashSet<AbstractOperation<?>>(sortedOperationList);
    }

    public List<Double> getSuppliedComponentsMilimeterDiameters() {
        List<Double> suppliedComponentsMilimeterDiameter = new ArrayList<Double>();

        for (AbstractSupply<?> supply : getSupplies()) {
            suppliedComponentsMilimeterDiameter.add(supply == null ? Double.NaN : supply.getCylindricComponent().getMilimeterDiameter());
        }

        return suppliedComponentsMilimeterDiameter;
    }

    private Double getSuppliedComponentsMilimeterDiametersSum() {
        Double suppliedComponentsMilimeterDiametersSum = 0.0;

        for (Double suppliedComponentMilimeterDiameter : getSuppliedComponentsMilimeterDiameters()) {
            suppliedComponentsMilimeterDiametersSum += suppliedComponentMilimeterDiameter;
        }

        return suppliedComponentsMilimeterDiametersSum;
    }

    public Double getSuppliedComponentsAverageMilimeterDiameter() {
        return getSuppliedComponentsMilimeterDiametersSum() / getSuppliesCount();
    }

    public Set<BangleSupply> getBangleSupplies() {
        return getSuppliesByKind(SupplyKind.BANGLE);
    }

    public Set<CustomComponentSupply> getCustomComponentSupplies() {
        return getSuppliesByKind(SupplyKind.CUSTOM_COMPONENT);
    }

    public Set<ElementSupply> getElementSupplies() {
        return getSuppliesByKind(SupplyKind.ELEMENT);
    }

    public Set<OneStudySupply> getOneStudySupplies() {
        return getSuppliesByKind(SupplyKind.ONE_STUDY);
    }

    /**
     * @param <T> The AbstractSupply<T> daughter class represented by supplyKind
     * @param supplyKind the kind of supply expected
     * @return All supplies of supplyKind kind
     */
    @SuppressWarnings("unchecked")
    public <T extends AbstractSupply<T>> Set<T> getSuppliesByKind(SupplyKind supplyKind) {
        Set<T> sortedSupplies = new HashSet<>();

        for (SupplyPosition supplyPosition : getSupplyPositions()) {
            AbstractSupply<?> supply = supplyPosition.getSupply();
            if (supply != null && supplyKind.equals(supply.getSupplyKind())) {
                sortedSupplies.add((T) supply);
            }
        }

        return sortedSupplies;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public Long getId() {
        return this.id;
    }

    public Strand id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return "[DESIGNATION OF " + getId() + "]";
    }

    public Double getDiameterAssemblyStep() {
        return this.diameterAssemblyStep;
    }

    public Strand diameterAssemblyStep(Double diameterAssemblyStep) {
        this.setDiameterAssemblyStep(diameterAssemblyStep);
        return this;
    }

    public void setDiameterAssemblyStep(Double diameterAssemblyStep) {
        this.diameterAssemblyStep = diameterAssemblyStep;
    }

    public AssemblyMean getAssemblyMean() {
        return this.assemblyMean;
    }

    public Strand assemblyMean(AssemblyMean assemblyMean) {
        this.setAssemblyMean(assemblyMean);
        return this;
    }

    public void setAssemblyMean(AssemblyMean assemblyMean) {
        this.assemblyMean = assemblyMean;
    }

    public Set<SupplyPosition> getSupplyPositions() {
        return this.supplyPositions;
    }

    public void setSupplyPositions(Set<SupplyPosition> supplyPositions) {
        if (this.supplyPositions != null) {
            this.supplyPositions.forEach(i -> i.setOwnerStrand(null));
        }
        if (supplyPositions != null) {
            supplyPositions.forEach(i -> i.setOwnerStrand(this));
        }
        this.supplyPositions = supplyPositions;
    }

    public Strand supplyPositions(Set<SupplyPosition> supplyPositions) {
        this.setSupplyPositions(supplyPositions);
        return this;
    }

    public Strand addSupplyPositions(SupplyPosition supplyPosition) {
        this.supplyPositions.add(supplyPosition);
        supplyPosition.setOwnerStrand(this);
        return this;
    }

    public Strand removeSupplyPositions(SupplyPosition supplyPosition) {
        this.supplyPositions.remove(supplyPosition);
        supplyPosition.setOwnerStrand(null);
        return this;
    }

    public Set<CoreAssembly> getCoreAssemblies() {
        return this.coreAssemblies;
    }

    public void setCoreAssemblies(Set<CoreAssembly> coreAssemblies) {
        if (this.coreAssemblies != null) {
            this.coreAssemblies.forEach(i -> i.setOwnerStrand(null));
        }
        if (coreAssemblies != null) {
            coreAssemblies.forEach(i -> i.setOwnerStrand(this));
        }
        this.coreAssemblies = coreAssemblies;
    }

    public Strand coreAssemblies(Set<CoreAssembly> coreAssemblies) {
        this.setCoreAssemblies(coreAssemblies);
        return this;
    }

    public Strand addCoreAssemblies(CoreAssembly coreAssembly) {
        this.coreAssemblies.add(coreAssembly);
        coreAssembly.setOwnerStrand(this);
        return this;
    }

    public Strand removeCoreAssemblies(CoreAssembly coreAssembly) {
        this.coreAssemblies.remove(coreAssembly);
        coreAssembly.setOwnerStrand(null);
        return this;
    }

    public Set<IntersticeAssembly> getIntersticeAssemblies() {
        return this.intersticeAssemblies;
    }

    public void setIntersticeAssemblies(Set<IntersticeAssembly> intersticeAssemblies) throws NoIntersticeAvailableException {
        if (getCoreAssemblies() == null) {
            throw new NoIntersticeAvailableException("No CoreAssembly found in strand");
        }
        if (this.intersticeAssemblies != null) {
            this.intersticeAssemblies.forEach(i -> i.setOwnerStrand(null));
        }
        if (intersticeAssemblies != null) {
            intersticeAssemblies.forEach(i -> i.setOwnerStrand(this));
        }
        this.intersticeAssemblies = intersticeAssemblies;
    }

    public Strand intersticeAssemblies(Set<IntersticeAssembly> intersticeAssemblies) throws NoIntersticeAvailableException {
        this.setIntersticeAssemblies(intersticeAssemblies);
        return this;
    }

    public Strand addIntersticeAssemblies(IntersticeAssembly intersticeAssembly) {
        this.intersticeAssemblies.add(intersticeAssembly);
        intersticeAssembly.setOwnerStrand(this);
        return this;
    }

    public Strand removeIntersticeAssemblies(IntersticeAssembly intersticeAssembly) {
        this.intersticeAssemblies.remove(intersticeAssembly);
        intersticeAssembly.setOwnerStrand(null);
        return this;
    }

    public Set<Sheathing> getSheathings() {
        return this.sheathings;
    }

    public void setSheathings(Set<Sheathing> sheathings) {
        if (this.sheathings != null) {
            this.sheathings.forEach(i -> i.setOwnerStrand(null));
        }
        if (sheathings != null) {
            sheathings.forEach(i -> i.setOwnerStrand(this));
        }
        this.sheathings = sheathings;
    }

    public Strand sheathings(Set<Sheathing> sheathings) {
        this.setSheathings(sheathings);
        return this;
    }

    public Strand addSheathings(Sheathing sheathing) {
        this.sheathings.add(sheathing);
        sheathing.setOwnerStrand(this);
        return this;
    }

    public Strand removeSheathings(Sheathing sheathing) {
        this.sheathings.remove(sheathing);
        sheathing.setOwnerStrand(null);
        return this;
    }

    public CentralAssembly getCentralAssembly() {
        return this.centralAssembly;
    }

    public void setCentralAssembly(CentralAssembly centralAssembly) {
        if (this.centralAssembly != null) {
            this.centralAssembly.setOwnerStrand(null);
        }
        if (centralAssembly != null) {
            centralAssembly.setOwnerStrand(this);
        }
        this.centralAssembly = centralAssembly;
    }

    public Strand centralAssembly(CentralAssembly centralAssembly) {
        this.setCentralAssembly(centralAssembly);
        return this;
    }

    public Study getFutureStudy() {
        return this.futureStudy;
    }

    public void setFutureStudy(Study study) {
        this.futureStudy = study;
    }

    public Strand futureStudy(Study study) {
        this.setFutureStudy(study);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Strand)) {
            return false;
        }
        return id != null && id.equals(((Strand) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Strand{" +
            "id=" + getId() +
            ", diameterAssemblyStep=" + getDiameterAssemblyStep() +
            ", assemblyMean='" + getAssemblyMean() + "'" +
            "}";
    }
}
