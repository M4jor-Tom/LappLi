package com.muller.lappli.domain;

import com.muller.lappli.domain.enumeration.MetalFiberKind;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SteelFiber.
 */
@Entity
@Table(name = "steel_fiber")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SteelFiber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

    public Long getId() {
        return this.id;
    }

    public SteelFiber id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return this.number;
    }

    public SteelFiber number(Long number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getDesignation() {
        return this.designation;
    }

    public SteelFiber designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public MetalFiberKind getMetalFiberKind() {
        return this.metalFiberKind;
    }

    public SteelFiber metalFiberKind(MetalFiberKind metalFiberKind) {
        this.setMetalFiberKind(metalFiberKind);
        return this;
    }

    public void setMetalFiberKind(MetalFiberKind metalFiberKind) {
        this.metalFiberKind = metalFiberKind;
    }

    public Double getMilimeterDiameter() {
        return this.milimeterDiameter;
    }

    public SteelFiber milimeterDiameter(Double milimeterDiameter) {
        this.setMilimeterDiameter(milimeterDiameter);
        return this;
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
        if (!(o instanceof SteelFiber)) {
            return false;
        }
        return id != null && id.equals(((SteelFiber) o).id);
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
