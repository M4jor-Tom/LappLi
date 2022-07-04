package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractMachine;
import com.muller.lappli.domain.abstracts.AbstractSheathing;
import com.muller.lappli.domain.enumeration.OperationKind;
import com.muller.lappli.domain.interfaces.INonAssemblyOperation;
import com.muller.lappli.domain.interfaces.IOperation;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Sheathing.
 * An Operation consisting in a laying of a
 * {@link com.muller.lappli.domain.Material}
 */
@Entity
@Table(name = "sheathing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sheathing extends AbstractSheathing<Sheathing> implements Serializable, INonAssemblyOperation<Sheathing> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "milimeter_thickness", nullable = false)
    private Double milimeterThickness;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "coreAssemblies", "intersticeAssemblies", "sheathings", "strand", "centralAssembly", "study" },
        allowSetters = true
    )
    private StrandSupply ownerStrandSupply;

    public Sheathing() {
        super();
    }

    @Override
    public IOperation<Sheathing> toOperation() {
        return this;
    }

    @Override
    public Double getMilimeterDiameterIncidency() {
        if (getMilimeterThickness() == null) {
            return Double.NaN;
        }

        return getMilimeterThickness() * 2.0;
    }

    @Override
    public Long getProductionStep() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractMachine<?> getOperatingMachine() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double getHourPreparationTime() {
        // TODO Auto-generated method stub
        return Double.NaN;
    }

    @Override
    public Double getHourExecutionTime() {
        // TODO Auto-generated method stub
        return Double.NaN;
    }

    @Override
    public Sheathing getThis() {
        return this;
    }

    @Override
    public OperationKind getOperationKind() {
        return OperationKind.SHEATHING;
    }

    @Override
    public String getProductDesignation() {
        if (getMaterial() == null) {
            return "";
        }

        return getMaterial().getDesignation();
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Double getMilimeterThickness() {
        return this.milimeterThickness;
    }

    public Sheathing milimeterThickness(Double milimeterThickness) {
        this.setMilimeterThickness(milimeterThickness);
        return this;
    }

    public void setMilimeterThickness(Double milimeterThickness) {
        this.milimeterThickness = milimeterThickness;
    }

    @Override
    public StrandSupply getOwnerStrandSupply() {
        return this.ownerStrandSupply;
    }

    public void setOwnerStrandSupply(StrandSupply strandSupply) {
        this.ownerStrandSupply = strandSupply;
    }

    public Sheathing ownerStrandSupply(StrandSupply strandSupply) {
        this.setOwnerStrandSupply(strandSupply);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sheathing)) {
            return false;
        }
        return getId() != null && getId().equals(((Sheathing) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sheathing{" +
            "id=" + getId() +
            ", operationLayer=" + getOperationLayer() +
            ", milimeterThickness=" + getMilimeterThickness() +
            ", sheathingKind='" + getSheathingKind() + "'" +
            "}";
    }
}
