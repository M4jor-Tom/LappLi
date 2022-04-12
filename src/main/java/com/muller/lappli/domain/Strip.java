package com.muller.lappli.domain;

import com.muller.lappli.domain.abstracts.AbstractDomainObject;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Strip.
 */
@Entity
@Table(name = "strip")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Strip extends AbstractDomainObject<Strip> implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "number", nullable = false, unique = true)
    private Long number;

    @NotNull
    @Column(name = "designation", nullable = false, unique = true)
    private String designation;

    @NotNull
    @Column(name = "milimeter_thickness", nullable = false)
    private Double milimeterThickness;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public Strip getThis() {
        return this;
    }

    public Long getNumber() {
        return this.number;
    }

    public Strip number(Long number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getDesignation() {
        return this.designation;
    }

    public Strip designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getMilimeterThickness() {
        return this.milimeterThickness;
    }

    public Strip milimeterThickness(Double milimeterThickness) {
        this.setMilimeterThickness(milimeterThickness);
        return this;
    }

    public void setMilimeterThickness(Double milimeterThickness) {
        this.milimeterThickness = milimeterThickness;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Strip)) {
            return false;
        }
        return getId() != null && getId().equals(((Strip) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Strip{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", designation='" + getDesignation() + "'" +
            ", milimeterThickness=" + getMilimeterThickness() +
            "}";
    }
}
