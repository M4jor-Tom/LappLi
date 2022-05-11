package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractMachine;
import com.muller.lappli.domain.abstracts.AbstractOperation;
import com.muller.lappli.domain.enumeration.AssemblyMean;
import com.muller.lappli.domain.enumeration.MetalFiberKind;
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
 * A Screen.
 */
@Entity
@Table(name = "screen")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Screen
    extends AbstractOperation<Screen>
    implements Serializable, INonAssemblyOperation<Screen>, MeanedAssemblableOperation<Screen> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "operation_layer", nullable = false)
    private Long operationLayer;

    @NotNull
    @Column(name = "assembly_mean_is_same_than_assemblys", nullable = false)
    private Boolean assemblyMeanIsSameThanAssemblys;

    @Min(value = 0L)
    @Column(name = "forced_diameter_assembly_step")
    private Long forcedDiameterAssemblyStep;

    @Min(value = 0L)
    @Column(name = "anonymous_copper_fiber_number")
    private Long anonymousCopperFiberNumber;

    @Column(name = "anonymous_copper_fiber_designation")
    private String anonymousCopperFiberDesignation;

    @Enumerated(EnumType.STRING)
    @Column(name = "anonymous_copper_fiber_kind")
    private MetalFiberKind anonymousCopperFiberKind;

    @DecimalMin(value = "0")
    @Column(name = "anonymous_copper_fiber_milimeter_diameter")
    private Double anonymousCopperFiberMilimeterDiameter;

    @ManyToOne
    private CopperFiber copperFiber;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "coreAssemblies",
            "intersticeAssemblies",
            "tapeLayings",
            "screens",
            "stripLayings",
            "plaits",
            "carrierPlaits",
            "sheathings",
            "continuityWireLongitLayings",
            "strand",
            "centralAssembly",
            "study",
        },
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
        if (getFinalCopperFiber() == null) {
            return Double.NaN;
        } else if (getFinalCopperFiber().getMilimeterDiameter() == null) {
            return Double.NaN;
        }

        return getFinalCopperFiber().getMilimeterDiameter() * 2;
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
    public Double getHourExecutionTime() {
        // TODO Auto-generated method stub
        return Double.NaN;
    }

    @Override
    public String getProductDesignation() {
        if (getFinalCopperFiber() == null) {
            return "";
        } else if (getFinalCopperFiber().getDesignation() == null) {
            return "";
        }

        return getFinalCopperFiber().getDesignation();
    }

    @Override
    public Double getDiameterAssemblyStep() {
        if (getForcedDiameterAssemblyStep() == null) {
            return getAssemblyMeanIsSameThanAssemblys() ? 3.0 : 8.0;
        }

        return getForcedDiameterAssemblyStep().doubleValue();
    }

    @Override
    public AssemblyMean getAssemblyMean() {
        if (getOwnerStrandSupply() == null) {
            return null;
        } else if (getOwnerStrandSupply().getAssemblyMean() == null) {
            return null;
        } else if (getAssemblyMeanIsSameThanAssemblys() == null) {
            return null;
        } else if (AssemblyMean.STRAIGHT.equals(getOwnerStrandSupply().getAssemblyMean())) {
            return AssemblyMean.STRAIGHT;
        } else if (getAssemblyMeanIsSameThanAssemblys()) {
            return getOwnerStrandSupply().getAssemblyMean();
        }

        return AssemblyMean.LEFT.equals(getOwnerStrandSupply().getAssemblyMean()) ? AssemblyMean.RIGHT : AssemblyMean.LEFT;
    }

    public CopperFiber getAnonymousCopperFiber() {
        return new CopperFiber()
            .id(null)
            .number(getAnonymousCopperFiberNumber())
            .designation(getAnonymousCopperFiberDesignation())
            .metalFiberKind(getAnonymousCopperFiberKind())
            .milimeterDiameter(getAnonymousCopperFiberMilimeterDiameter())
            .getThisIfConform()
            .orElse(null);
    }

    public CopperFiber getFinalCopperFiber() {
        if (getCopperFiber() == null) {
            return getAnonymousCopperFiber();
        }

        return getCopperFiber();
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

    public Long getAnonymousCopperFiberNumber() {
        return this.anonymousCopperFiberNumber;
    }

    public Screen anonymousCopperFiberNumber(Long anonymousCopperFiberNumber) {
        this.setAnonymousCopperFiberNumber(anonymousCopperFiberNumber);
        return this;
    }

    public void setAnonymousCopperFiberNumber(Long anonymousCopperFiberNumber) {
        this.anonymousCopperFiberNumber = anonymousCopperFiberNumber;
    }

    public String getAnonymousCopperFiberDesignation() {
        return this.anonymousCopperFiberDesignation;
    }

    public Screen anonymousCopperFiberDesignation(String anonymousCopperFiberDesignation) {
        this.setAnonymousCopperFiberDesignation(anonymousCopperFiberDesignation);
        return this;
    }

    public void setAnonymousCopperFiberDesignation(String anonymousCopperFiberDesignation) {
        this.anonymousCopperFiberDesignation = anonymousCopperFiberDesignation;
    }

    public MetalFiberKind getAnonymousCopperFiberKind() {
        return this.anonymousCopperFiberKind;
    }

    public Screen anonymousCopperFiberKind(MetalFiberKind anonymousCopperFiberKind) {
        this.setAnonymousCopperFiberKind(anonymousCopperFiberKind);
        return this;
    }

    public void setAnonymousCopperFiberKind(MetalFiberKind anonymousCopperFiberKind) {
        this.anonymousCopperFiberKind = anonymousCopperFiberKind;
    }

    public Double getAnonymousCopperFiberMilimeterDiameter() {
        return this.anonymousCopperFiberMilimeterDiameter;
    }

    public Screen anonymousCopperFiberMilimeterDiameter(Double anonymousCopperFiberMilimeterDiameter) {
        this.setAnonymousCopperFiberMilimeterDiameter(anonymousCopperFiberMilimeterDiameter);
        return this;
    }

    public void setAnonymousCopperFiberMilimeterDiameter(Double anonymousCopperFiberMilimeterDiameter) {
        this.anonymousCopperFiberMilimeterDiameter = anonymousCopperFiberMilimeterDiameter;
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
        return getId() != null && getId().equals(((Screen) o).getId());
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
            ", anonymousCopperFiberNumber=" + getAnonymousCopperFiberNumber() +
            ", anonymousCopperFiberDesignation='" + getAnonymousCopperFiberDesignation() + "'" +
            ", anonymousCopperFiberKind='" + getAnonymousCopperFiberKind() + "'" +
            ", anonymousCopperFiberMilimeterDiameter=" + getAnonymousCopperFiberMilimeterDiameter() +
            "}";
    }
}
