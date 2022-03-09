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
    @Column(name = "operation_layer", nullable = false)
    private Long operationLayer;

    @NotNull
    @Column(name = "interstice_layer", nullable = false)
    private Long intersticeLayer;

    @Column(name = "forced_mean_milimeter_component_diameter")
    private Double forcedMeanMilimeterComponentDiameter;

    @OneToMany(mappedBy = "ownerIntersticeAssembly", fetch = FetchType.EAGER)
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
        value = { "coreAssemblies", "intersticeAssemblies", "tapeLayings", "sheathings", "strand", "centralAssembly", "study" },
        allowSetters = true
    )
    private StrandSupply ownerStrandSupply;

    public IntersticeAssembly() {
        super();
        setOperationLayer(null);
        setIntersticeLayer(null);
        setForcedMeanMilimeterComponentDiameter(null);
        setSupplyPositions(new HashSet<>());
        setOwnerStrandSupply(null);
    }

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
            return getOwnerStrandSupply().getDiameterAssemblyStep();
        } catch (NullPointerException e) {
            return Double.NaN;
        }
    }

    @Override
    public Double getMilimeterDiameterIncidency() {
        return 0.0;
    }

    @Override
    public Long getProductionStep() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AssemblyMean getAssemblyMean() {
        try {
            return getOwnerStrandSupply().getAssemblyMean();
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

    public Long getOperationLayer() {
        return this.operationLayer;
    }

    public IntersticeAssembly operationLayer(Long operationLayer) {
        this.setOperationLayer(operationLayer);
        return this;
    }

    public void setOperationLayer(Long operationLayer) {
        this.operationLayer = operationLayer;
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

    public StrandSupply getOwnerStrandSupply() {
        return this.ownerStrandSupply;
    }

    public void setOwnerStrandSupply(StrandSupply strandSupply) {
        this.ownerStrandSupply = strandSupply;
    }

    public IntersticeAssembly ownerStrandSupply(StrandSupply strandSupply) {
        this.setOwnerStrandSupply(strandSupply);
        return this;
    }

    @Override
    public Long getComponentsCount() {
        if (getOwnerStrandSupply() == null) {
            return DomainManager.ERROR_LONG_POSITIVE_VALUE;
        }

        Long componentsCount = Long.valueOf(0);

        for (SupplyPosition supplyPosition : getSupplyPositions()) {
            componentsCount += supplyPosition.getSupply().getApparitions();
        }

        return componentsCount / getOwnerStrandSupply().getApparitions();
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
            ", operationLayer=" + getOperationLayer() +
            ", intersticeLayer=" + getIntersticeLayer() +
            ", forcedMeanMilimeterComponentDiameter=" + getForcedMeanMilimeterComponentDiameter() +
            "}";
    }
}
