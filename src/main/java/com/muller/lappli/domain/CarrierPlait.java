package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractMachine;
import com.muller.lappli.domain.abstracts.AbstractOperation;
import com.muller.lappli.domain.enumeration.OperationKind;
import com.muller.lappli.domain.interfaces.INonAssemblyOperation;
import com.muller.lappli.domain.interfaces.IOperation;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CarrierPlait.
 */
@Entity
@Table(name = "carrier_plait")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CarrierPlait extends AbstractOperation<CarrierPlait> implements Serializable, INonAssemblyOperation<CarrierPlait> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "operation_layer", nullable = false)
    private Long operationLayer;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "minimum_deca_newton_load", nullable = false)
    private Double minimumDecaNewtonLoad;

    @NotNull
    @Min(value = 0L)
    @Max(value = 89L)
    @Column(name = "degree_assembly_angle", nullable = false)
    private Long degreeAssemblyAngle;

    @Min(value = 0L)
    @Column(name = "forced_end_per_bobins_count")
    private Long forcedEndPerBobinsCount;

    @Min(value = 0L)
    @Column(name = "anonymous_carrier_plait_fiber_number")
    private Long anonymousCarrierPlaitFiberNumber;

    @Column(name = "anonymous_carrier_plait_fiber_designation")
    private String anonymousCarrierPlaitFiberDesignation;

    @Min(value = 0L)
    @Column(name = "anonymous_carrier_plait_fiber_decitex_titration")
    private Long anonymousCarrierPlaitFiberDecitexTitration;

    @DecimalMin(value = "0")
    @Column(name = "anonymous_carrier_plait_fiber_gram_per_square_milimeter_per_meter_density")
    private Double anonymousCarrierPlaitFiberGramPerSquareMilimeterPerMeterDensity;

    @DecimalMin(value = "0")
    @Column(name = "anonymous_carrier_plait_fiber_deca_newton_load")
    private Double anonymousCarrierPlaitFiberDecaNewtonLoad;

    @ManyToOne
    private CarrierPlaitFiber carrierPlaitFiber;

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
    public IOperation<CarrierPlait> toOperation() {
        return this;
    }

    @Override
    public CarrierPlait getThis() {
        return this;
    }

    @Override
    public OperationKind getOperationKind() {
        return OperationKind.CARRIER_PLAIT;
    }

    @Override
    public Double getMilimeterDiameterIncidency() {
        // TODO Auto-generated method stub
        if (getFinalCarrierPlaitFiber() == null) {
            return Double.NaN;
        }

        return Double.NaN;
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
        if (getFinalCarrierPlaitFiber() == null) {
            return null;
        }

        return getFinalCarrierPlaitFiber().getDesignation();
    }

    public Double getRealDecaNewtonLoad() {
        // TODO Empty method stub
        return Double.NaN;
    }

    public Long getSuggestedEndPerBobinsCount() {
        // TODO Empty method stub
        return null;
    }

    public Long getFinalEndPerBobinsCount() {
        if (getForcedEndPerBobinsCount() == null) {
            return getSuggestedEndPerBobinsCount();
        }

        return getForcedEndPerBobinsCount();
    }

    public CarrierPlaitFiber getFinalCarrierPlaitFiber() {
        if (getCarrierPlaitFiber() == null) {
            return getAnonymousCarrierPlaitFiber();
        }

        return getCarrierPlaitFiber();
    }

    private CarrierPlaitFiber getAnonymousCarrierPlaitFiber() {
        return new CarrierPlaitFiber()
            .number(getAnonymousCarrierPlaitFiberNumber())
            .designation(getAnonymousCarrierPlaitFiberDesignation())
            .decitexTitration(getAnonymousCarrierPlaitFiberDecitexTitration())
            .gramPerSquareMilimeterPerMeterDensity(getAnonymousCarrierPlaitFiberGramPerSquareMilimeterPerMeterDensity())
            .decaNewtonLoad(getAnonymousCarrierPlaitFiberDecaNewtonLoad())
            .getThisIfConform()
            .orElse(null);
    }

    public Long getOperationLayer() {
        return this.operationLayer;
    }

    public CarrierPlait operationLayer(Long operationLayer) {
        this.setOperationLayer(operationLayer);
        return this;
    }

    public void setOperationLayer(Long operationLayer) {
        this.operationLayer = operationLayer;
    }

    public Double getMinimumDecaNewtonLoad() {
        return this.minimumDecaNewtonLoad;
    }

    public CarrierPlait minimumDecaNewtonLoad(Double minimumDecaNewtonLoad) {
        this.setMinimumDecaNewtonLoad(minimumDecaNewtonLoad);
        return this;
    }

    public void setMinimumDecaNewtonLoad(Double minimumDecaNewtonLoad) {
        this.minimumDecaNewtonLoad = minimumDecaNewtonLoad;
    }

    public Long getDegreeAssemblyAngle() {
        return this.degreeAssemblyAngle;
    }

    public CarrierPlait degreeAssemblyAngle(Long degreeAssemblyAngle) {
        this.setDegreeAssemblyAngle(degreeAssemblyAngle);
        return this;
    }

    public void setDegreeAssemblyAngle(Long degreeAssemblyAngle) {
        this.degreeAssemblyAngle = degreeAssemblyAngle;
    }

    public Long getForcedEndPerBobinsCount() {
        return this.forcedEndPerBobinsCount;
    }

    public CarrierPlait forcedEndPerBobinsCount(Long forcedEndPerBobinsCount) {
        this.setForcedEndPerBobinsCount(forcedEndPerBobinsCount);
        return this;
    }

    public void setForcedEndPerBobinsCount(Long forcedEndPerBobinsCount) {
        this.forcedEndPerBobinsCount = forcedEndPerBobinsCount;
    }

    public Long getAnonymousCarrierPlaitFiberNumber() {
        return this.anonymousCarrierPlaitFiberNumber;
    }

    public CarrierPlait anonymousCarrierPlaitFiberNumber(Long anonymousCarrierPlaitFiberNumber) {
        this.setAnonymousCarrierPlaitFiberNumber(anonymousCarrierPlaitFiberNumber);
        return this;
    }

    public void setAnonymousCarrierPlaitFiberNumber(Long anonymousCarrierPlaitFiberNumber) {
        this.anonymousCarrierPlaitFiberNumber = anonymousCarrierPlaitFiberNumber;
    }

    public String getAnonymousCarrierPlaitFiberDesignation() {
        return this.anonymousCarrierPlaitFiberDesignation;
    }

    public CarrierPlait anonymousCarrierPlaitFiberDesignation(String anonymousCarrierPlaitFiberDesignation) {
        this.setAnonymousCarrierPlaitFiberDesignation(anonymousCarrierPlaitFiberDesignation);
        return this;
    }

    public void setAnonymousCarrierPlaitFiberDesignation(String anonymousCarrierPlaitFiberDesignation) {
        this.anonymousCarrierPlaitFiberDesignation = anonymousCarrierPlaitFiberDesignation;
    }

    public Long getAnonymousCarrierPlaitFiberDecitexTitration() {
        return this.anonymousCarrierPlaitFiberDecitexTitration;
    }

    public CarrierPlait anonymousCarrierPlaitFiberDecitexTitration(Long anonymousCarrierPlaitFiberDecitexTitration) {
        this.setAnonymousCarrierPlaitFiberDecitexTitration(anonymousCarrierPlaitFiberDecitexTitration);
        return this;
    }

    public void setAnonymousCarrierPlaitFiberDecitexTitration(Long anonymousCarrierPlaitFiberDecitexTitration) {
        this.anonymousCarrierPlaitFiberDecitexTitration = anonymousCarrierPlaitFiberDecitexTitration;
    }

    public Double getAnonymousCarrierPlaitFiberGramPerSquareMilimeterPerMeterDensity() {
        return this.anonymousCarrierPlaitFiberGramPerSquareMilimeterPerMeterDensity;
    }

    public CarrierPlait anonymousCarrierPlaitFiberGramPerSquareMilimeterPerMeterDensity(
        Double anonymousCarrierPlaitFiberGramPerSquareMilimeterPerMeterDensity
    ) {
        this.setAnonymousCarrierPlaitFiberGramPerSquareMilimeterPerMeterDensity(
                anonymousCarrierPlaitFiberGramPerSquareMilimeterPerMeterDensity
            );
        return this;
    }

    public void setAnonymousCarrierPlaitFiberGramPerSquareMilimeterPerMeterDensity(
        Double anonymousCarrierPlaitFiberGramPerSquareMilimeterPerMeterDensity
    ) {
        this.anonymousCarrierPlaitFiberGramPerSquareMilimeterPerMeterDensity =
            anonymousCarrierPlaitFiberGramPerSquareMilimeterPerMeterDensity;
    }

    public Double getAnonymousCarrierPlaitFiberDecaNewtonLoad() {
        return this.anonymousCarrierPlaitFiberDecaNewtonLoad;
    }

    public CarrierPlait anonymousCarrierPlaitFiberDecaNewtonLoad(Double anonymousCarrierPlaitFiberDecaNewtonLoad) {
        this.setAnonymousCarrierPlaitFiberDecaNewtonLoad(anonymousCarrierPlaitFiberDecaNewtonLoad);
        return this;
    }

    public void setAnonymousCarrierPlaitFiberDecaNewtonLoad(Double anonymousCarrierPlaitFiberDecaNewtonLoad) {
        this.anonymousCarrierPlaitFiberDecaNewtonLoad = anonymousCarrierPlaitFiberDecaNewtonLoad;
    }

    public CarrierPlaitFiber getCarrierPlaitFiber() {
        return this.carrierPlaitFiber;
    }

    public void setCarrierPlaitFiber(CarrierPlaitFiber carrierPlaitFiber) {
        this.carrierPlaitFiber = carrierPlaitFiber;
    }

    public CarrierPlait carrierPlaitFiber(CarrierPlaitFiber carrierPlaitFiber) {
        this.setCarrierPlaitFiber(carrierPlaitFiber);
        return this;
    }

    public StrandSupply getOwnerStrandSupply() {
        return this.ownerStrandSupply;
    }

    public void setOwnerStrandSupply(StrandSupply strandSupply) {
        this.ownerStrandSupply = strandSupply;
    }

    public CarrierPlait ownerStrandSupply(StrandSupply strandSupply) {
        this.setOwnerStrandSupply(strandSupply);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarrierPlait)) {
            return false;
        }
        return getId() != null && getId().equals(((CarrierPlait) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarrierPlait{" +
            "id=" + getId() +
            ", operationLayer=" + getOperationLayer() +
            ", minimumDecaNewtonLoad=" + getMinimumDecaNewtonLoad() +
            ", degreeAssemblyAngle=" + getDegreeAssemblyAngle() +
            ", forcedEndPerBobinsCount=" + getForcedEndPerBobinsCount() +
            ", anonymousCarrierPlaitFiberNumber=" + getAnonymousCarrierPlaitFiberNumber() +
            ", anonymousCarrierPlaitFiberDesignation='" + getAnonymousCarrierPlaitFiberDesignation() + "'" +
            ", anonymousCarrierPlaitFiberDecitexTitration=" + getAnonymousCarrierPlaitFiberDecitexTitration() +
            ", anonymousCarrierPlaitFiberGramPerSquareMilimeterPerMeterDensity=" + getAnonymousCarrierPlaitFiberGramPerSquareMilimeterPerMeterDensity() +
            ", anonymousCarrierPlaitFiberDecaNewtonLoad=" + getAnonymousCarrierPlaitFiberDecaNewtonLoad() +
            "}";
    }
}
