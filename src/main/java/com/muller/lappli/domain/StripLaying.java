package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractMachine;
import com.muller.lappli.domain.abstracts.AbstractOperation;
import com.muller.lappli.domain.enumeration.AssemblyMean;
import com.muller.lappli.domain.enumeration.OperationKind;
import com.muller.lappli.domain.interfaces.INonAssemblyOperation;
import com.muller.lappli.domain.interfaces.IOperation;
import com.muller.lappli.domain.interfaces.MeanedAssemblableOperation;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A StripLaying.
 */
@Entity
@Table(name = "strip_laying")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StripLaying
    extends AbstractOperation<StripLaying>
    implements Serializable, INonAssemblyOperation<StripLaying>, MeanedAssemblableOperation<StripLaying> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "operation_layer", nullable = false)
    private Long operationLayer;

    @ManyToOne(optional = false)
    @NotNull
    private Strip strip;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "coreAssemblies",
            "intersticeAssemblies",
            "tapeLayings",
            "screens",
            "stripLayings",
            "sheathings",
            "strand",
            "centralAssembly",
            "study",
        },
        allowSetters = true
    )
    private StrandSupply ownerStrandSupply;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public StripLaying getThis() {
        return this;
    }

    @Override
    public OperationKind getOperationKind() {
        return OperationKind.STRIP_LAYING;
    }

    @Override
    public Double getMilimeterDiameterIncidency() {
        if (getStrip() == null) {
            return Double.NaN;
        }

        return getStrip().getMilimeterThickness() * 2;
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
    public String getProductDesignation() {
        if (getStrip() == null) {
            return "";
        }

        return getStrip().getDesignation();
    }

    @Override
    public IOperation<StripLaying> toOperation() {
        return this;
    }

    @Override
    public Double getDiameterAssemblyStep() {
        // TODO Auto-generated method stub
        return Double.NaN;
    }

    @Override
    public AssemblyMean getAssemblyMean() {
        // TODO Auto-generated method stub
        return null;
    }

    public Long getOperationLayer() {
        return this.operationLayer;
    }

    public StripLaying operationLayer(Long operationLayer) {
        this.setOperationLayer(operationLayer);
        return this;
    }

    public void setOperationLayer(Long operationLayer) {
        this.operationLayer = operationLayer;
    }

    public Strip getStrip() {
        return this.strip;
    }

    public void setStrip(Strip strip) {
        this.strip = strip;
    }

    public StripLaying strip(Strip strip) {
        this.setStrip(strip);
        return this;
    }

    public StrandSupply getOwnerStrandSupply() {
        return this.ownerStrandSupply;
    }

    public void setOwnerStrandSupply(StrandSupply strandSupply) {
        this.ownerStrandSupply = strandSupply;
    }

    public StripLaying ownerStrandSupply(StrandSupply strandSupply) {
        this.setOwnerStrandSupply(strandSupply);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StripLaying)) {
            return false;
        }
        return getId() != null && getId().equals(((StripLaying) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StripLaying{" +
            "id=" + getId() +
            ", operationLayer=" + getOperationLayer() +
            "}";
    }
}
