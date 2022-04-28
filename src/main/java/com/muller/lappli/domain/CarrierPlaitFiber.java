package com.muller.lappli.domain;

import com.muller.lappli.domain.abstracts.AbstractDomainObject;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CarrierPlaitFiber.
 */
@Entity
@Table(name = "carrier_plait_fiber")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CarrierPlaitFiber extends /*TODO AbstractUniformAtom*/AbstractDomainObject<CarrierPlaitFiber> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Min(value = 0L)
    @Column(name = "number", unique = true)
    private Long number;

    @Column(name = "designation", unique = true)
    private String designation;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "square_milimeter_section", nullable = false)
    private Double squareMilimeterSection;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "deca_newton_load", nullable = false)
    private Double decaNewtonLoad;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public Boolean isConform() {
        return getSquareMilimeterSection() != null && getDecaNewtonLoad() != null;
    }

    @Override
    public CarrierPlaitFiber getThis() {
        return this;
    }

    public Long getNumber() {
        return this.number;
    }

    public CarrierPlaitFiber number(Long number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getDesignation() {
        return this.designation;
    }

    public CarrierPlaitFiber designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getSquareMilimeterSection() {
        return this.squareMilimeterSection;
    }

    public CarrierPlaitFiber squareMilimeterSection(Double squareMilimeterSection) {
        this.setSquareMilimeterSection(squareMilimeterSection);
        return this;
    }

    public void setSquareMilimeterSection(Double squareMilimeterSection) {
        this.squareMilimeterSection = squareMilimeterSection;
    }

    public Double getDecaNewtonLoad() {
        return this.decaNewtonLoad;
    }

    public CarrierPlaitFiber decaNewtonLoad(Double decaNewtonLoad) {
        this.setDecaNewtonLoad(decaNewtonLoad);
        return this;
    }

    public void setDecaNewtonLoad(Double decaNewtonLoad) {
        this.decaNewtonLoad = decaNewtonLoad;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarrierPlaitFiber)) {
            return false;
        }
        return getId() != null && getId().equals(((CarrierPlaitFiber) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarrierPlaitFiber{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", designation='" + getDesignation() + "'" +
            ", squareMilimeterSection=" + getSquareMilimeterSection() +
            ", decaNewtonLoad=" + getDecaNewtonLoad() +
            "}";
    }
}
