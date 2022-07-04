package com.muller.lappli.domain;

import com.muller.lappli.domain.abstracts.AbstractMetalFiber;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CopperFiber.
 * An {@link com.muller.lappli.domain.abstracts.AbstractMetalFiber}
 * which is used in {@link com.muller.lappli.domain.Plait} and
 * {@link com.muller.lappli.domain.Screen} Operations.
 */
@Entity
@Table(name = "copper_fiber")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CopperFiber extends AbstractMetalFiber<CopperFiber> implements Serializable {

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public CopperFiber() {
        super();
    }

    @Override
    public CopperFiber getThis() {
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CopperFiber)) {
            return false;
        }
        return getId() != null && getId().equals(((CopperFiber) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CopperFiber{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", designation='" + getDesignation() + "'" +
            ", metalFiberKind='" + getMetalFiberKind() + "'" +
            ", milimeterDiameter=" + getMilimeterDiameter() +
            "}";
    }
}
