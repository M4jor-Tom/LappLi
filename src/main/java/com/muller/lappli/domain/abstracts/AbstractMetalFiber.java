package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.enumeration.MetalFiberKind;
import javax.persistence.*;
import javax.validation.constraints.*;

@MappedSuperclass
public abstract class AbstractMetalFiber<T extends AbstractMetalFiber<T>> extends AbstractDomainObject<T> {

    @Column(name = "number", unique = true)
    private Long number;

    @Column(name = "designation", unique = true)
    private String designation;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "metal_fiber_kind", nullable = false)
    private MetalFiberKind metalFiberKind;

    @NotNull
    @Column(name = "milimeter_diameter", nullable = false)
    private Double milimeterDiameter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public AbstractMetalFiber() {
        super();
    }

    public Boolean isMetalFiberKindConform() {
        if (getMetalFiberKind() == null) {
            return false;
        }

        return getMetalFiberKind().isForMetalFiber(getThis().getClass());
    }

    public Boolean isConform() {
        return getMetalFiberKind() != null && getMilimeterDiameter() != null && isMetalFiberKindConform();
    }

    public Long getNumber() {
        return this.number;
    }

    public T number(Long number) {
        this.setNumber(number);
        return getThis();
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getDesignation() {
        return this.designation;
    }

    public T designation(String designation) {
        this.setDesignation(designation);
        return getThis();
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public MetalFiberKind getMetalFiberKind() {
        return this.metalFiberKind;
    }

    public T metalFiberKind(MetalFiberKind metalFiberKind) {
        this.setMetalFiberKind(metalFiberKind);
        return getThis();
    }

    public void setMetalFiberKind(MetalFiberKind metalFiberKind) {
        this.metalFiberKind = metalFiberKind;
    }

    public Double getMilimeterDiameter() {
        return this.milimeterDiameter;
    }

    public T milimeterDiameter(Double milimeterDiameter) {
        this.setMilimeterDiameter(milimeterDiameter);
        return getThis();
    }

    public void setMilimeterDiameter(Double milimeterDiameter) {
        this.milimeterDiameter = milimeterDiameter;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractMetalFiber<?>)) {
            return false;
        }
        return getId() != null && getId().equals(((AbstractMetalFiber<?>) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AbstractMetalFiber{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", designation='" + getDesignation() + "'" +
            ", metalFiberKind='" + getMetalFiberKind() + "'" +
            ", milimeterDiameter=" + getMilimeterDiameter() +
            "}";
    }
}
