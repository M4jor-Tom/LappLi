package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.enumeration.MetalFiberKind;
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
public class Plait implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    private MetalFiber metalFiber;

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

    public Long getId() {
        return this.id;
    }

    public Plait id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
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

    public MetalFiber getMetalFiber() {
        return this.metalFiber;
    }

    public void setMetalFiber(MetalFiber metalFiber) {
        this.metalFiber = metalFiber;
    }

    public Plait metalFiber(MetalFiber metalFiber) {
        this.setMetalFiber(metalFiber);
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
        return id != null && id.equals(((Plait) o).id);
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
