package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractNonCentralAssembly;
import com.muller.lappli.domain.enumeration.AssemblyMean;
import com.muller.lappli.domain.enumeration.OperationKind;
import com.muller.lappli.domain.interfaces.ISupplyPositionOwner;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A IntersticeAssembly.
 */
@Entity
@Table(name = "interstice_assembly")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IntersticeAssembly extends AbstractNonCentralAssembly<IntersticeAssembly> implements ISupplyPositionOwner, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "assembly_layer", nullable = false)
    private Long assemblyLayer;

    @NotNull
    @Column(name = "interstice_layer", nullable = false)
    private Long intersticeLayer;

    @Column(name = "forced_mean_milimeter_component_diameter")
    private Double forcedMeanMilimeterComponentDiameter;

    @OneToMany(mappedBy = "ownerIntersticeAssembly")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "ownerCentralAssembly",
            "elementSupply",
            "bangleSupply",
            "customComponentSupply",
            "oneStudySupply",
            "ownerStrand",
            "ownerIntersticeAssembly",
        },
        allowSetters = true
    )
    private Set<SupplyPosition> supplyPositions = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "supplyPositions", "coreAssemblies", "intersticeAssemblies", "sheathings", "centralAssembly", "futureStudy" },
        allowSetters = true
    )
    private Strand ownerStrand;

    @Override
    public IntersticeAssembly getThis() {
        return this;
    }

    @Override
    public OperationKind getOperationKind() {
        return OperationKind.INTERSTICE_ASSEMBLY;
    }

    @Override
    public Double getDiameterAssemblyStep() {
        try {
            return getOwnerStrand().getDiameterAssemblyStep();
        } catch (NullPointerException e) {
            return Double.NaN;
        }
    }

    @Override
    public Long getProductionStep() {
        return DomainManager.ERROR_LONG_POSITIVE_VALUE;
    }

    @Override
    public AssemblyMean getAssemblyMean() {
        try {
            return getOwnerStrand().getAssemblyMean();
        } catch (NullPointerException e) {
            return null;
        }
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IntersticeAssembly id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssemblyLayer() {
        return this.assemblyLayer;
    }

    public IntersticeAssembly assemblyLayer(Long assemblyLayer) {
        this.setAssemblyLayer(assemblyLayer);
        return this;
    }

    public void setAssemblyLayer(Long assemblyLayer) {
        this.assemblyLayer = assemblyLayer;
    }

    public Long getIntersticeLayer() {
        return this.intersticeLayer;
    }

    public IntersticeAssembly intersticeLayer(Long intersticeLayer) {
        this.setIntersticeLayer(intersticeLayer);
        return this;
    }

    public void setIntersticeLayer(Long intersticeLayer) {
        this.intersticeLayer = intersticeLayer;
    }

    public Double getForcedMeanMilimeterComponentDiameter() {
        return this.forcedMeanMilimeterComponentDiameter;
    }

    public IntersticeAssembly forcedMeanMilimeterComponentDiameter(Double forcedMeanMilimeterComponentDiameter) {
        this.setForcedMeanMilimeterComponentDiameter(forcedMeanMilimeterComponentDiameter);
        return this;
    }

    public void setForcedMeanMilimeterComponentDiameter(Double forcedMeanMilimeterComponentDiameter) {
        this.forcedMeanMilimeterComponentDiameter = forcedMeanMilimeterComponentDiameter;
    }

    public Set<SupplyPosition> getSupplyPositions() {
        return this.supplyPositions;
    }

    public void setSupplyPositions(Set<SupplyPosition> supplyPositions) {
        if (this.supplyPositions != null) {
            this.supplyPositions.forEach(i -> i.setOwnerIntersticeAssembly(null));
        }
        if (supplyPositions != null) {
            supplyPositions.forEach(i -> i.setOwnerIntersticeAssembly(this));
        }
        this.supplyPositions = supplyPositions;
    }

    public IntersticeAssembly supplyPositions(Set<SupplyPosition> supplyPositions) {
        this.setSupplyPositions(supplyPositions);
        return this;
    }

    public IntersticeAssembly addSupplyPositions(SupplyPosition supplyPosition) {
        this.supplyPositions.add(supplyPosition);
        supplyPosition.setOwnerIntersticeAssembly(this);
        return this;
    }

    public IntersticeAssembly removeSupplyPositions(SupplyPosition supplyPosition) {
        this.supplyPositions.remove(supplyPosition);
        supplyPosition.setOwnerIntersticeAssembly(null);
        return this;
    }

    public Strand getOwnerStrand() {
        return this.ownerStrand;
    }

    public void setOwnerStrand(Strand strand) {
        this.ownerStrand = strand;
    }

    public IntersticeAssembly ownerStrand(Strand strand) {
        this.setOwnerStrand(strand);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntersticeAssembly)) {
            return false;
        }
        return id != null && id.equals(((IntersticeAssembly) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntersticeAssembly{" +
            "id=" + getId() +
            ", assemblyLayer=" + getAssemblyLayer() +
            ", intersticeLayer=" + getIntersticeLayer() +
            ", forcedMeanMilimeterComponentDiameter=" + getForcedMeanMilimeterComponentDiameter() +
            "}";
    }
}
