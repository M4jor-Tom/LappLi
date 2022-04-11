package com.muller.lappli.domain;

import com.muller.lappli.domain.abstracts.AbstractMetalFiber;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MetalFiber.
 */
@Entity
@Table(name = "metal_fiber")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MetalFiber extends AbstractMetalFiber<MetalFiber> implements Serializable {

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public MetalFiber getThis() {
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetalFiber)) {
            return false;
        }
        return getId() != null && getId().equals(((MetalFiber) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetalFiber{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", designation='" + getDesignation() + "'" +
            ", metalFiberKind='" + getMetalFiberKind() + "'" +
            ", milimeterDiameter=" + getMilimeterDiameter() +
            "}";
    }
}
