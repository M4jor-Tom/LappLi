package com.muller.lappli.domain;

import com.muller.lappli.domain.abstracts.AbstractFixedDiameterUniformAtom;
import com.muller.lappli.domain.enumeration.Flexibility;
import com.muller.lappli.domain.enumeration.MetalFiberKind;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContinuityWire.
 */
@Entity
@Table(name = "continuity_wire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContinuityWire extends AbstractFixedDiameterUniformAtom<ContinuityWire> implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "designation", nullable = false, unique = true)
    private String designation;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "metal_fiber_kind", nullable = false)
    private MetalFiberKind metalFiberKind;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "flexibility", nullable = false)
    private Flexibility flexibility;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public ContinuityWire() {
        super();
    }

    @Override
    public ContinuityWire getThis() {
        return this;
    }

    @Override
    public Boolean isConform() {
        return super.isConform() && getDesignation() != null && getMetalFiberKind() != null && getFlexibility() != null;
    }

    @Override
    public Boolean isUtility() {
        return true;
    }

    @Override
    public String getDesignation() {
        return this.designation;
    }

    public ContinuityWire designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public MetalFiberKind getMetalFiberKind() {
        return this.metalFiberKind;
    }

    public ContinuityWire metalFiberKind(MetalFiberKind metalFiberKind) {
        this.setMetalFiberKind(metalFiberKind);
        return this;
    }

    public void setMetalFiberKind(MetalFiberKind metalFiberKind) {
        this.metalFiberKind = metalFiberKind;
    }

    public Flexibility getFlexibility() {
        return this.flexibility;
    }

    public ContinuityWire flexibility(Flexibility flexibility) {
        this.setFlexibility(flexibility);
        return this;
    }

    public void setFlexibility(Flexibility flexibility) {
        this.flexibility = flexibility;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContinuityWire)) {
            return false;
        }
        return getId() != null && getId().equals(((ContinuityWire) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContinuityWire{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", gramPerMeterLinearMass=" + getGramPerMeterLinearMass() +
            ", metalFiberKind='" + getMetalFiberKind() + "'" +
            ", milimeterDiameter=" + getMilimeterDiameter() +
            ", flexibility='" + getFlexibility() + "'" +
            "}";
    }
}
