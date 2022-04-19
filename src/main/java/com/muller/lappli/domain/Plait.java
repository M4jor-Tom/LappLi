package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractMetalFiber;
import com.muller.lappli.domain.abstracts.AbstractOperation;
import com.muller.lappli.domain.enumeration.MetalFiberKind;
import com.muller.lappli.domain.enumeration.OperationKind;
import com.muller.lappli.domain.exception.UnknownMetalFiberException;
import com.muller.lappli.domain.interfaces.AssemblableOperation;
import com.muller.lappli.domain.interfaces.INonAssemblyOperation;
import com.muller.lappli.domain.interfaces.IOperation;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Plait.
 */
@Entity
@Table(name = "plait")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Plait extends AbstractOperation<Plait> implements Serializable, INonAssemblyOperation<Plait>, AssemblableOperation<Plait> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "operation_layer", nullable = false)
    private Long operationLayer;

    @Column(name = "target_covering_rate")
    private Double targetCoveringRate;

    @Column(name = "target_degree_angle")
    private Double targetDegreeAngle;

    @NotNull
    @Column(name = "targeting_covering_rate_not_angle", nullable = false)
    private Boolean targetingCoveringRateNotAngle;

    @Column(name = "anonymous_metal_fiber_number")
    private Long anonymousMetalFiberNumber;

    @Column(name = "anonymous_metal_fiber_designation")
    private String anonymousMetalFiberDesignation;

    @Enumerated(EnumType.STRING)
    @Column(name = "anonymous_metal_fiber_metal_fiber_kind")
    private MetalFiberKind anonymousMetalFiberMetalFiberKind;

    @Column(name = "anonymous_metal_fiber_milimeter_diameter")
    private Double anonymousMetalFiberMilimeterDiameter;

    @ManyToOne
    private CopperFiber copperFiber;

    @ManyToOne
    private SteelFiber steelFiber;

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
    public Plait getThis() {
        return this;
    }

    @Override
    public Boolean isConform() {
        return (
            getOperationLayer() != null &&
            getTargetingCoveringRateNotAngle() != null &&
            (getCopperFiber() == null) != (getSteelFiber() == null)
        );
    }

    @Override
    public IOperation<Plait> toOperation() {
        return this;
    }

    @Override
    public OperationKind getOperationKind() {
        return OperationKind.PLAIT;
    }

    @Override
    public Double getMilimeterDiameterIncidency() {
        AbstractMetalFiber<?> metalFiber = null;

        try {
            metalFiber = getFinalMetalFiber();
        } catch (UnknownMetalFiberException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (metalFiber == null) {
            return Double.NaN;
        }

        return metalFiber.getMilimeterDiameter();
    }

    @Override
    public Long getProductionStep() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double getDiameterAssemblyStep() {
        // TODO Auto-generated method stub
        return Double.NaN;
    }

    @Override
    public String getProductDesignation() {
        try {
            AbstractMetalFiber<?> metalFiber = getFinalMetalFiber();

            if (metalFiber == null) {
                return "";
            }

            return metalFiber.getDesignation();
        } catch (UnknownMetalFiberException e) {
            e.printStackTrace();
            return "";
        }
    }

    private AbstractMetalFiber<?> getAnonymousMetalFiber() throws UnknownMetalFiberException {
        AbstractMetalFiber<?> metalFiber = null;

        //[PLAIT_METAL_FIBER]
        if (getAnonymousMetalFiberMetalFiberKind() == null) {
            metalFiber = null;
        } else if (getAnonymousMetalFiberMetalFiberKind().isForMetalFiber(CopperFiber.class)) {
            metalFiber = new CopperFiber();
        } else if (getAnonymousMetalFiberMetalFiberKind().isForMetalFiber(SteelFiber.class)) {
            metalFiber = new SteelFiber();
        } else {
            throw new UnknownMetalFiberException();
        }

        return metalFiber
            .id(null)
            .number(getAnonymousMetalFiberNumber())
            .designation(getAnonymousMetalFiberDesignation())
            .metalFiberKind(getAnonymousMetalFiberMetalFiberKind())
            .milimeterDiameter(getAnonymousMetalFiberMilimeterDiameter())
            .getThisIfConform()
            .orElse(null);
    }

    public AbstractMetalFiber<?> getMetalFiber() {
        //[PLAIT_METAL_FIBER]
        if (getCopperFiber() != null) {
            return getCopperFiber();
        } else if (getSteelFiber() != null) {
            return getSteelFiber();
        }

        return null;
    }

    public AbstractMetalFiber<?> getFinalMetalFiber() throws UnknownMetalFiberException {
        if (getMetalFiber() == null) {
            return getAnonymousMetalFiber();
        }

        return getMetalFiber();
    }

    @Override
    public Long getOperationLayer() {
        return this.operationLayer;
    }

    @Override
    public Plait operationLayer(Long operationLayer) {
        this.setOperationLayer(operationLayer);
        return this;
    }

    @Override
    public void setOperationLayer(Long operationLayer) {
        this.operationLayer = operationLayer;
    }

    public Double getTargetCoveringRate() {
        return this.targetCoveringRate;
    }

    public Plait targetCoveringRate(Double targetCoveringRate) {
        this.setTargetCoveringRate(targetCoveringRate);
        return this;
    }

    public void setTargetCoveringRate(Double targetCoveringRate) {
        this.targetCoveringRate = targetCoveringRate;
    }

    public Double getTargetDegreeAngle() {
        return this.targetDegreeAngle;
    }

    public Plait targetDegreeAngle(Double targetDegreeAngle) {
        this.setTargetDegreeAngle(targetDegreeAngle);
        return this;
    }

    public void setTargetDegreeAngle(Double targetDegreeAngle) {
        this.targetDegreeAngle = targetDegreeAngle;
    }

    public Boolean getTargetingCoveringRateNotAngle() {
        return this.targetingCoveringRateNotAngle;
    }

    public Plait targetingCoveringRateNotAngle(Boolean targetingCoveringRateNotAngle) {
        this.setTargetingCoveringRateNotAngle(targetingCoveringRateNotAngle);
        return this;
    }

    public void setTargetingCoveringRateNotAngle(Boolean targetingCoveringRateNotAngle) {
        this.targetingCoveringRateNotAngle = targetingCoveringRateNotAngle;
    }

    public Long getAnonymousMetalFiberNumber() {
        return this.anonymousMetalFiberNumber;
    }

    public Plait anonymousMetalFiberNumber(Long anonymousMetalFiberNumber) {
        this.setAnonymousMetalFiberNumber(anonymousMetalFiberNumber);
        return this;
    }

    public void setAnonymousMetalFiberNumber(Long anonymousMetalFiberNumber) {
        this.anonymousMetalFiberNumber = anonymousMetalFiberNumber;
    }

    public String getAnonymousMetalFiberDesignation() {
        return this.anonymousMetalFiberDesignation;
    }

    public Plait anonymousMetalFiberDesignation(String anonymousMetalFiberDesignation) {
        this.setAnonymousMetalFiberDesignation(anonymousMetalFiberDesignation);
        return this;
    }

    public void setAnonymousMetalFiberDesignation(String anonymousMetalFiberDesignation) {
        this.anonymousMetalFiberDesignation = anonymousMetalFiberDesignation;
    }

    public MetalFiberKind getAnonymousMetalFiberMetalFiberKind() {
        return this.anonymousMetalFiberMetalFiberKind;
    }

    public Plait anonymousMetalFiberMetalFiberKind(MetalFiberKind anonymousMetalFiberMetalFiberKind) {
        this.setAnonymousMetalFiberMetalFiberKind(anonymousMetalFiberMetalFiberKind);
        return this;
    }

    public void setAnonymousMetalFiberMetalFiberKind(MetalFiberKind anonymousMetalFiberMetalFiberKind) {
        this.anonymousMetalFiberMetalFiberKind = anonymousMetalFiberMetalFiberKind;
    }

    public Double getAnonymousMetalFiberMilimeterDiameter() {
        return this.anonymousMetalFiberMilimeterDiameter;
    }

    public Plait anonymousMetalFiberMilimeterDiameter(Double anonymousMetalFiberMilimeterDiameter) {
        this.setAnonymousMetalFiberMilimeterDiameter(anonymousMetalFiberMilimeterDiameter);
        return this;
    }

    public void setAnonymousMetalFiberMilimeterDiameter(Double anonymousMetalFiberMilimeterDiameter) {
        this.anonymousMetalFiberMilimeterDiameter = anonymousMetalFiberMilimeterDiameter;
    }

    public CopperFiber getCopperFiber() {
        return this.copperFiber;
    }

    public void setCopperFiber(CopperFiber copperFiber) {
        this.copperFiber = copperFiber;
    }

    public Plait copperFiber(CopperFiber copperFiber) {
        this.setCopperFiber(copperFiber);
        return this;
    }

    public SteelFiber getSteelFiber() {
        return this.steelFiber;
    }

    public void setSteelFiber(SteelFiber steelFiber) {
        this.steelFiber = steelFiber;
    }

    public Plait steelFiber(SteelFiber steelFiber) {
        this.setSteelFiber(steelFiber);
        return this;
    }

    public StrandSupply getOwnerStrandSupply() {
        return this.ownerStrandSupply;
    }

    public void setOwnerStrandSupply(StrandSupply strandSupply) {
        this.ownerStrandSupply = strandSupply;
    }

    public Plait ownerStrandSupply(StrandSupply strandSupply) {
        this.setOwnerStrandSupply(strandSupply);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plait)) {
            return false;
        }
        return getId() != null && getId().equals(((Plait) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Plait{" +
            "id=" + getId() +
            ", operationLayer=" + getOperationLayer() +
            ", targetCoveringRate=" + getTargetCoveringRate() +
            ", targetDegreeAngle=" + getTargetDegreeAngle() +
            ", targetingCoveringRateNotAngle='" + getTargetingCoveringRateNotAngle() + "'" +
            ", anonymousMetalFiberNumber=" + getAnonymousMetalFiberNumber() +
            ", anonymousMetalFiberDesignation='" + getAnonymousMetalFiberDesignation() + "'" +
            ", anonymousMetalFiberMetalFiberKind='" + getAnonymousMetalFiberMetalFiberKind() + "'" +
            ", anonymousMetalFiberMilimeterDiameter=" + getAnonymousMetalFiberMilimeterDiameter() +
            "}";
    }
}
