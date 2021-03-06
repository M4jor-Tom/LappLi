package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractDomainObject;
import com.muller.lappli.domain.abstracts.AbstractSupply;
import com.muller.lappli.domain.enumeration.CylindricComponentKind;
import com.muller.lappli.domain.interfaces.Designable;
import com.muller.lappli.domain.interfaces.ISupplyPositionOwner;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Strand.
 * This represents the gathering of
 * {@link com.muller.lappli.domain.interfaces.CylindricComponent}s
 * through their respective supplies
 *
 * Strand is a bit itself a component, but it needs to be
 * observed through a {@link com.muller.lappli.domain.StrandSupply}
 * for that.
 *
 * In fact, a Strand does know how much
 * {@link com.muller.lappli.domain.interfaces.CylindricComponent}s it
 * owns in total, but only its {@link com.muller.lappli.domain.StrandSupply}
 * knows in how many times it exists.
 *
 * Therefore, if a Strand can specify how much components it
 * will finally include in a Cable, only its
 * {@link com.muller.lappli.domain.StrandSupply} can determine its
 * physical form, its operations.
 */
@Entity
@Table(name = "strand")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Strand extends AbstractDomainObject<Strand> implements Designable, ISupplyPositionOwner, Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "ownerStrand", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "owner", "ownerCentralAssembly", "ownerStrand", "ownerIntersticeAssembly" }, allowSetters = true)
    private Set<SupplyPosition> supplyPositions = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "strands", "strandSupplies", "author" }, allowSetters = true)
    private Study futureStudy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public Strand getThis() {
        return this;
    }

    public Strand() {
        super();
        setSupplyPositions(new HashSet<>());
    }

    public Long getProductionStep() {
        if (getFutureStudy() == null || getFutureStudy().getStrands() == null) {
            return null;
        }

        Object[] strandsOfThisStudy = getFutureStudy()
            .getStrands()
            .stream()
            .sorted(
                new Comparator<Strand>() {
                    @Override
                    public int compare(Strand o1, Strand o2) {
                        return (int) (o1.getId() - o2.getId());
                    }
                }
            )
            .toArray();

        Long index = 1L;
        for (Object strand : strandsOfThisStudy) {
            if (strand == this) {
                return index * 100L;
            }

            index++;
        }

        return null;
    }

    /**
     * Finds the futureStudy's StrandSupply which owns this Strand
     *
     * @return a StrandSupply
     */
    @JsonIgnore
    public StrandSupply getFutureStudyStrandSupply() {
        for (StrandSupply strandSupply : getFutureStudy().getStrandSupplies()) {
            if (strandSupply.getStrand() == this) {
                return strandSupply;
            }
        }

        return null;
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

    @Override
    public String getDesignation() {
        return getUndividedCountDesignation();
    }

    public Double getSuppliedComponentsAverageMilimeterDiameter() {
        return getSuppliedComponentsMilimeterDiametersSum() / getUndividedSuppliedComponentsCount();
    }

    public String getUndividedCountDesignation() {
        String designation = "";
        Boolean isFirstWrittenSupplyDesignation = true;

        if (getSupplies() == null) {
            return "";
        } else for (AbstractSupply<?> supply : getSupplies()) {
            if (isFirstWrittenSupplyDesignation) {
                isFirstWrittenSupplyDesignation = false;
            } else {
                designation += " + ";
            }
            designation += supply.getDesignation();
        }

        return designation;
    }

    public Long getUndividedSuppliedComponentsCount() {
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

    public List<Double> getSuppliedComponentsMilimeterDiameters() {
        List<Double> suppliedComponentsMilimeterDiameter = new ArrayList<Double>();

        for (AbstractSupply<?> supply : getSupplies()) {
            for (Long i = Long.valueOf(0); i < supply.getApparitions(); i++) {
                suppliedComponentsMilimeterDiameter.add(
                    supply == null ? Double.NaN : supply.getCylindricComponent().getMilimeterDiameter()
                );
            }
        }

        return suppliedComponentsMilimeterDiameter;
    }

    public List<Double> getSuppliedComponentsSquareMilimeterSurfaces() {
        List<Double> suppliedComponentsSquareMilimeterSurfaces = new ArrayList<Double>();

        for (AbstractSupply<?> supply : getSupplies()) {
            for (Long i = Long.valueOf(0); i < supply.getApparitions(); i++) {
                suppliedComponentsSquareMilimeterSurfaces.add(
                    supply == null ? Double.NaN : supply.getCylindricComponent().getSquareMilimeterSurface()
                );
            }
        }

        return suppliedComponentsSquareMilimeterSurfaces;
    }

    public Double getSuppliedComponentsMilimeterDiametersSum() {
        Double suppliedComponentsMilimeterDiametersSum = 0.0;

        for (Double suppliedComponentMilimeterDiameter : getSuppliedComponentsMilimeterDiameters()) {
            suppliedComponentsMilimeterDiametersSum += suppliedComponentMilimeterDiameter;
        }

        return suppliedComponentsMilimeterDiametersSum;
    }

    public Double getSuppliedComponentsSquareMilimeterSurfacesSum() {
        Double suppliedComponentsSquareMilimeterSurfaceSum = 0.0;

        for (Double suppliedComponentSquareMilimeterSurface : getSuppliedComponentsSquareMilimeterSurfaces()) {
            suppliedComponentsSquareMilimeterSurfaceSum += suppliedComponentSquareMilimeterSurface;
        }

        return suppliedComponentsSquareMilimeterSurfaceSum;
    }

    //[COMPONENT_KIND]
    public Set<BangleSupply> getBangleSupplies() {
        return getSuppliesByKind(CylindricComponentKind.BANGLE);
    }

    public Set<CustomComponentSupply> getCustomComponentSupplies() {
        return getSuppliesByKind(CylindricComponentKind.CUSTOM_COMPONENT);
    }

    public Set<ElementSupply> getElementSupplies() {
        return getSuppliesByKind(CylindricComponentKind.ELEMENT);
    }

    public Set<OneStudySupply> getOneStudySupplies() {
        return getSuppliesByKind(CylindricComponentKind.ONE_STUDY);
    }

    /**
     * @param <T> The AbstractSupply daughter class represented by supplyKind
     * @param cylindricComponentKind the kind of component expected
     * @return All supplies of supplyKind kind
     */
    @SuppressWarnings("unchecked")
    public <T extends AbstractSupply<T>> Set<T> getSuppliesByKind(CylindricComponentKind cylindricComponentKind) {
        Set<T> sortedSupplies = new HashSet<>();

        for (SupplyPosition supplyPosition : getSupplyPositions()) {
            AbstractSupply<?> supply = supplyPosition.getSupply();
            if (supply != null && cylindricComponentKind.equals(supply.getCylindricComponent().getCylindricComponentKind())) {
                sortedSupplies.add((T) supply);
            }
        }

        return sortedSupplies;
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
        return getId() != null && getId().equals(((Strand) o).getId());
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
            "}";
    }
}
