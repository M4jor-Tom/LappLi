package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractAssembly;
import com.muller.lappli.domain.abstracts.AbstractSupply;
import com.muller.lappli.domain.enumeration.OperationKind;
import com.muller.lappli.domain.interfaces.ISupplyPositionOwner;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CentralAssembly.
 */
@Entity
@Table(name = "central_assembly")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CentralAssembly extends AbstractAssembly<CentralAssembly> implements ISupplyPositionOwner, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @JsonIgnoreProperties(
        value = { "coreAssemblies", "intersticeAssemblies", "sheathings", "strand", "centralAssembly", "study" },
        allowSetters = true
    )
    @OneToOne(optional = false)
    @NotNull
    @MapsId
    @JoinColumn(name = "id")
    private StrandSupply ownerStrandSupply;

    @JsonIgnoreProperties(value = { "ownerCentralAssembly", "ownerStrand", "ownerIntersticeAssembly" }, allowSetters = true)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private SupplyPosition supplyPosition;

    @Override
    public CentralAssembly getThis() {
        return this;
    }

    @Override
    public OperationKind getOperationKind() {
        return OperationKind.CENTRAL_ASSEMBLY;
    }

    @Override
    public Long getOperationLayer() {
        return Long.valueOf(0);
    }

    @Override
    public Double getAfterThisMilimeterDiameter() {
        return getMilimeterDiameterIncidency();
    }

    public Long getComponentsCount() {
        SupplyPosition supplyPosition = getSupplyPosition();

        if (supplyPosition == null) {
            return Long.valueOf(0);
        }

        AbstractSupply<?> supply = supplyPosition.getSupply();

        if (supply == null) {
            return Long.valueOf(0);
        }

        return supply.getApparitions();
    }

    @Override
    public Double getMilimeterDiameterIncidency() {
        if (getOwnerStrandSupply() == null) {
            return Double.NaN;
        } else {
            AbstractSupply<?> supply = getSupplyPosition() == null ? null : getSupplyPosition().getSupply();
            if (supply == null) {
                //When no Supply has been set, but one is required
                //TODO: return suggestion formula instead of 0-returning formula
                return CalculatorManager.getCalculatorInstance().getMilimeterCentralVoidDiameter(getOwnerStrandSupply());
            } else if (Long.valueOf(1).equals(supply.getApparitions())) {
                return supply.getMilimeterDiameter();
            }
        }

        return Double.NaN;
    }

    @Override
    public Long getProductionStep() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getProductDesignation() {
        return "";
    }

    @Override
    public Long getAssemblyLayer() {
        return Long.valueOf(0);
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public Long getId() {
        return this.id;
    }

    public CentralAssembly id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StrandSupply getOwnerStrandSupply() {
        return this.ownerStrandSupply;
    }

    public void setOwnerStrandSupply(StrandSupply strandSupply) {
        this.ownerStrandSupply = strandSupply;
    }

    public CentralAssembly ownerStrandSupply(StrandSupply strandSupply) {
        this.setOwnerStrandSupply(strandSupply);
        return this;
    }

    public SupplyPosition getSupplyPosition() {
        return this.supplyPosition;
    }

    public void setSupplyPosition(SupplyPosition supplyPosition) {
        this.supplyPosition = supplyPosition;
    }

    public CentralAssembly supplyPosition(SupplyPosition supplyPosition) {
        this.setSupplyPosition(supplyPosition);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CentralAssembly)) {
            return false;
        }
        return id != null && id.equals(((CentralAssembly) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CentralAssembly{" +
            "id=" + getId() +
            ", productionStep=" + getProductionStep() +
            "}";
    }
}
