package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractOperation;
import com.muller.lappli.domain.enumeration.AssemblyMean;
import com.muller.lappli.domain.enumeration.OperationKind;
import com.muller.lappli.domain.interfaces.INonAssemblyOperation;
import com.muller.lappli.domain.interfaces.IOperation;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Screen.
 */
@Entity
@Table(name = "screen")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Screen extends AbstractOperation<Screen> implements Serializable, INonAssemblyOperation<Screen> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "operation_layer", nullable = false)
    private Long operationLayer;

    @NotNull
    @Column(name = "assembly_mean_is_same_than_assemblys", nullable = false)
    private Boolean assemblyMeanIsSameThanAssemblys;

    @Column(name = "forced_diameter_assembly_step")
    private Long forcedDiameterAssemblyStep;

    @ManyToOne(optional = false)
    @NotNull
    private CopperFiber copperFiber;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "coreAssemblies", "intersticeAssemblies", "tapeLayings", "screens", "sheathings", "strand", "centralAssembly", "study" },
        allowSetters = true
    )
    private StrandSupply ownerStrandSupply;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public Screen getThis() {
        return this;
    }

    @Override
    public IOperation<Screen> toOperation() {
        return this;
    }

    @Override
    public OperationKind getOperationKind() {
        return OperationKind.SCREEN;
    }

    @Override
    public Double getMilimeterDiameterIncidency() {
        if (getCopperFiber() == null) {
            return Double.NaN;
        }

        return getCopperFiber().getMilimeterDiameter() * 2;
    }

    @Override
    public Long getProductionStep() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getProductDesignation() {
        if (getCopperFiber() == null) {
            return "";
        }

        return getCopperFiber().getDesignation();
    }

    public Long getId() {
        return this.id;
    }

    public Screen id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOperationLayer() {
        return this.operationLayer;
    }

    public Screen operationLayer(Long operationLayer) {
        this.setOperationLayer(operationLayer);
        return this;
    }

    public void setOperationLayer(Long operationLayer) {
        this.operationLayer = operationLayer;
    }

    public Boolean getAssemblyMeanIsSameThanAssemblys() {
        return this.assemblyMeanIsSameThanAssemblys;
    }

    public Screen assemblyMeanIsSameThanAssemblys(Boolean assemblyMeanIsSameThanAssemblys) {
        this.setAssemblyMeanIsSameThanAssemblys(assemblyMeanIsSameThanAssemblys);
        return this;
    }

    public void setAssemblyMeanIsSameThanAssemblys(Boolean assemblyMeanIsSameThanAssemblys) {
        this.assemblyMeanIsSameThanAssemblys = assemblyMeanIsSameThanAssemblys;
    }

    public Long getForcedDiameterAssemblyStep() {
        return this.forcedDiameterAssemblyStep;
    }

    public Screen forcedDiameterAssemblyStep(Long forcedDiameterAssemblyStep) {
        this.setForcedDiameterAssemblyStep(forcedDiameterAssemblyStep);
        return this;
    }

    public void setForcedDiameterAssemblyStep(Long forcedDiameterAssemblyStep) {
        this.forcedDiameterAssemblyStep = forcedDiameterAssemblyStep;
    }

    public CopperFiber getCopperFiber() {
        return this.copperFiber;
    }

    public void setCopperFiber(CopperFiber copperFiber) {
        this.copperFiber = copperFiber;
    }

    public Screen copperFiber(CopperFiber copperFiber) {
        this.setCopperFiber(copperFiber);
        return this;
    }

    public StrandSupply getOwnerStrandSupply() {
        return this.ownerStrandSupply;
    }

    public void setOwnerStrandSupply(StrandSupply strandSupply) {
        this.ownerStrandSupply = strandSupply;
    }

    public Screen ownerStrandSupply(StrandSupply strandSupply) {
        this.setOwnerStrandSupply(strandSupply);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Screen)) {
            return false;
        }
        return id != null && id.equals(((Screen) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Screen{" +
            "id=" + getId() +
            ", operationLayer=" + getOperationLayer() +
            ", assemblyMeanIsSameThanAssemblys='" + getAssemblyMeanIsSameThanAssemblys() + "'" +
            ", forcedDiameterAssemblyStep=" + getForcedDiameterAssemblyStep() +
            "}";
    }
}
