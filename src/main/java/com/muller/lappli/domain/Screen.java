package com.muller.lappli.domain;

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
public class Screen implements Serializable {

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

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
