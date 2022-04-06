package com.muller.lappli.domain;

import com.muller.lappli.domain.abstracts.AbstractDomainObject;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CopperFiber.
 */
@Entity
@Table(name = "copper_fiber")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CopperFiber extends AbstractDomainObject<CopperFiber> implements Serializable {

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
    @Column(name = "copper_is_red_not_tinned", nullable = false)
    private Boolean copperIsRedNotTinned;

    @NotNull
    @Column(name = "milimeter_diameter", nullable = false)
    private Double milimeterDiameter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public CopperFiber getThis() {
        return this;
    }

    public Long getId() {
        return this.id;
    }

    public CopperFiber id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return this.number;
    }

    public CopperFiber number(Long number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getDesignation() {
        return this.designation;
    }

    public CopperFiber designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Boolean getCopperIsRedNotTinned() {
        return this.copperIsRedNotTinned;
    }

    public CopperFiber copperIsRedNotTinned(Boolean copperIsRedNotTinned) {
        this.setCopperIsRedNotTinned(copperIsRedNotTinned);
        return this;
    }

    public void setCopperIsRedNotTinned(Boolean copperIsRedNotTinned) {
        this.copperIsRedNotTinned = copperIsRedNotTinned;
    }

    public Double getMilimeterDiameter() {
        return this.milimeterDiameter;
    }

    public CopperFiber milimeterDiameter(Double milimeterDiameter) {
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
        if (!(o instanceof CopperFiber)) {
            return false;
        }
        return id != null && id.equals(((CopperFiber) o).id);
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
            ", copperIsRedNotTinned='" + getCopperIsRedNotTinned() + "'" +
            ", milimeterDiameter=" + getMilimeterDiameter() +
            "}";
    }
}
