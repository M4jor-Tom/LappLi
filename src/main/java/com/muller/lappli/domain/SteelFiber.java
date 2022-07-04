package com.muller.lappli.domain;

import com.muller.lappli.domain.abstracts.AbstractMetalFiber;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SteelFiber.
 * A component which can be laid through the
 * {@link com.muller.lappli.domain.Plait} operation
 */
@Entity
@Table(name = "steel_fiber")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SteelFiber extends AbstractMetalFiber<SteelFiber> implements Serializable {

    private static final long serialVersionUID = 1L;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public SteelFiber getThis() {
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SteelFiber)) {
            return false;
        }
        return getId() != null && getId().equals(((SteelFiber) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SteelFiber{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", designation='" + getDesignation() + "'" +
            ", metalFiberKind='" + getMetalFiberKind() + "'" +
            ", milimeterDiameter=" + getMilimeterDiameter() +
            "}";
    }
}
