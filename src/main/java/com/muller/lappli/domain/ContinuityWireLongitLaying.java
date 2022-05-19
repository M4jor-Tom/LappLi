package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractMachine;
import com.muller.lappli.domain.abstracts.AbstractOperation;
import com.muller.lappli.domain.enumeration.Flexibility;
import com.muller.lappli.domain.enumeration.MetalFiberKind;
import com.muller.lappli.domain.enumeration.OperationKind;
import com.muller.lappli.domain.interfaces.INonAssemblyOperation;
import com.muller.lappli.domain.interfaces.IOperation;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContinuityWireLongitLaying.
 */
@Entity
@Table(name = "continuity_wire_longit_laying")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContinuityWireLongitLaying
    extends AbstractOperation<ContinuityWireLongitLaying>
    implements Serializable, INonAssemblyOperation<ContinuityWireLongitLaying> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "operation_layer", nullable = false)
    private Long operationLayer;

    @Column(name = "anonymous_continuity_wire_designation")
    private String anonymousContinuityWireDesignation;

    @DecimalMin(value = "0")
    @Column(name = "anonymous_continuity_wire_gram_per_meter_linear_mass")
    private Double anonymousContinuityWireGramPerMeterLinearMass;

    @Enumerated(EnumType.STRING)
    @Column(name = "anonymous_continuity_wire_metal_fiber_kind")
    private MetalFiberKind anonymousContinuityWireMetalFiberKind;

    @DecimalMin(value = "0")
    @Column(name = "anonymous_continuity_wire_milimeter_diameter")
    private Double anonymousContinuityWireMilimeterDiameter;

    @Enumerated(EnumType.STRING)
    @Column(name = "anonymous_continuity_wire_flexibility")
    private Flexibility anonymousContinuityWireFlexibility;

    @ManyToOne
    private ContinuityWire continuityWire;

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
    public ContinuityWireLongitLaying getThis() {
        return this;
    }

    @Override
    public IOperation<ContinuityWireLongitLaying> toOperation() {
        return this;
    }

    @Override
    public OperationKind getOperationKind() {
        return OperationKind.CONTINUITY_WIRE_LONGIT_LAYING;
    }

    private ContinuityWire getAnonymousContinuityWire() {
        return new ContinuityWire()
            .id(null)
            .designation(getAnonymousContinuityWireDesignation())
            .flexibility(getAnonymousContinuityWireFlexibility())
            .gramPerMeterLinearMass(getAnonymousContinuityWireGramPerMeterLinearMass())
            .milimeterDiameter(getAnonymousContinuityWireMilimeterDiameter())
            .metalFiberKind(getAnonymousContinuityWireMetalFiberKind())
            .getThisIfConform()
            .orElse(null);
    }

    public ContinuityWire getFinalContinuityWire() {
        if (getContinuityWire() == null) {
            return getAnonymousContinuityWire();
        }

        return getContinuityWire();
    }

    @Override
    public Double getMilimeterDiameterIncidency() {
        if (getFinalContinuityWire() == null) {
            return Double.NaN;
        }

        return getFinalContinuityWire().getMilimeterDiameter();
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
    public String getProductDesignation() {
        ContinuityWire continuityWire = getFinalContinuityWire();

        if (continuityWire == null) {
            return "";
        }

        return continuityWire.getDesignation();
    }

    public Long getId() {
        return this.id;
    }

    public ContinuityWireLongitLaying id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOperationLayer() {
        return this.operationLayer;
    }

    public ContinuityWireLongitLaying operationLayer(Long operationLayer) {
        this.setOperationLayer(operationLayer);
        return this;
    }

    public void setOperationLayer(Long operationLayer) {
        this.operationLayer = operationLayer;
    }

    public String getAnonymousContinuityWireDesignation() {
        return this.anonymousContinuityWireDesignation;
    }

    public ContinuityWireLongitLaying anonymousContinuityWireDesignation(String anonymousContinuityWireDesignation) {
        this.setAnonymousContinuityWireDesignation(anonymousContinuityWireDesignation);
        return this;
    }

    public void setAnonymousContinuityWireDesignation(String anonymousContinuityWireDesignation) {
        this.anonymousContinuityWireDesignation = anonymousContinuityWireDesignation;
    }

    public Double getAnonymousContinuityWireGramPerMeterLinearMass() {
        return this.anonymousContinuityWireGramPerMeterLinearMass;
    }

    public ContinuityWireLongitLaying anonymousContinuityWireGramPerMeterLinearMass(Double anonymousContinuityWireGramPerMeterLinearMass) {
        this.setAnonymousContinuityWireGramPerMeterLinearMass(anonymousContinuityWireGramPerMeterLinearMass);
        return this;
    }

    public void setAnonymousContinuityWireGramPerMeterLinearMass(Double anonymousContinuityWireGramPerMeterLinearMass) {
        this.anonymousContinuityWireGramPerMeterLinearMass = anonymousContinuityWireGramPerMeterLinearMass;
    }

    public MetalFiberKind getAnonymousContinuityWireMetalFiberKind() {
        return this.anonymousContinuityWireMetalFiberKind;
    }

    public ContinuityWireLongitLaying anonymousContinuityWireMetalFiberKind(MetalFiberKind anonymousContinuityWireMetalFiberKind) {
        this.setAnonymousContinuityWireMetalFiberKind(anonymousContinuityWireMetalFiberKind);
        return this;
    }

    public void setAnonymousContinuityWireMetalFiberKind(MetalFiberKind anonymousContinuityWireMetalFiberKind) {
        this.anonymousContinuityWireMetalFiberKind = anonymousContinuityWireMetalFiberKind;
    }

    public Double getAnonymousContinuityWireMilimeterDiameter() {
        return this.anonymousContinuityWireMilimeterDiameter;
    }

    public ContinuityWireLongitLaying anonymousContinuityWireMilimeterDiameter(Double anonymousContinuityWireMilimeterDiameter) {
        this.setAnonymousContinuityWireMilimeterDiameter(anonymousContinuityWireMilimeterDiameter);
        return this;
    }

    public void setAnonymousContinuityWireMilimeterDiameter(Double anonymousContinuityWireMilimeterDiameter) {
        this.anonymousContinuityWireMilimeterDiameter = anonymousContinuityWireMilimeterDiameter;
    }

    public Flexibility getAnonymousContinuityWireFlexibility() {
        return this.anonymousContinuityWireFlexibility;
    }

    public ContinuityWireLongitLaying anonymousContinuityWireFlexibility(Flexibility anonymousContinuityWireFlexibility) {
        this.setAnonymousContinuityWireFlexibility(anonymousContinuityWireFlexibility);
        return this;
    }

    public void setAnonymousContinuityWireFlexibility(Flexibility anonymousContinuityWireFlexibility) {
        this.anonymousContinuityWireFlexibility = anonymousContinuityWireFlexibility;
    }

    public ContinuityWire getContinuityWire() {
        return this.continuityWire;
    }

    public void setContinuityWire(ContinuityWire continuityWire) {
        this.continuityWire = continuityWire;
    }

    public ContinuityWireLongitLaying continuityWire(ContinuityWire continuityWire) {
        this.setContinuityWire(continuityWire);
        return this;
    }

    public StrandSupply getOwnerStrandSupply() {
        return this.ownerStrandSupply;
    }

    public void setOwnerStrandSupply(StrandSupply strandSupply) {
        this.ownerStrandSupply = strandSupply;
    }

    public ContinuityWireLongitLaying ownerStrandSupply(StrandSupply strandSupply) {
        this.setOwnerStrandSupply(strandSupply);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContinuityWireLongitLaying)) {
            return false;
        }
        return id != null && id.equals(((ContinuityWireLongitLaying) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContinuityWireLongitLaying{" +
            "id=" + getId() +
            ", operationLayer=" + getOperationLayer() +
            ", anonymousContinuityWireDesignation='" + getAnonymousContinuityWireDesignation() + "'" +
            ", anonymousContinuityWireGramPerMeterLinearMass=" + getAnonymousContinuityWireGramPerMeterLinearMass() +
            ", anonymousContinuityWireMetalFiberKind='" + getAnonymousContinuityWireMetalFiberKind() + "'" +
            ", anonymousContinuityWireMilimeterDiameter=" + getAnonymousContinuityWireMilimeterDiameter() +
            ", anonymousContinuityWireFlexibility='" + getAnonymousContinuityWireFlexibility() + "'" +
            "}";
    }
}
