package com.muller.lappli.domain;

import com.muller.lappli.domain.abstracts.AbstractUniformAtom;
import com.muller.lappli.domain.enumeration.CylindricComponentKind;
import com.muller.lappli.domain.interfaces.Article;
import com.muller.lappli.domain.interfaces.PlasticAspectCylindricComponent;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Bangle. It can be used to fill in Strands when they need
 * completion.
 */
@Entity
@Table(name = "bangle")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bangle extends AbstractUniformAtom<Bangle> implements Article, Serializable, PlasticAspectCylindricComponent {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "number", nullable = false, unique = true)
    private Long number;

    @NotNull
    @Column(name = "designation", nullable = false, unique = true)
    private String designation;

    @ManyToOne(optional = false)
    @NotNull
    //@JsonIgnoreProperties(value = { "materialMarkingStatistics" }, allowSetters = true)
    private Material material;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Bangle() {
        super();
    }

    @Override
    public Bangle getThis() {
        return this;
    }

    @Override
    public CylindricComponentKind getCylindricComponentKind() {
        return CylindricComponentKind.BANGLE;
    }

    @Override
    public Long getArticleNumber() {
        return getNumber();
    }

    @Override
    public Boolean isUtility() {
        return false;
    }

    @Override
    public Material getSurfaceMaterial() {
        return getMaterial();
    }

    public Long getNumber() {
        return this.number;
    }

    public Bangle number(Long number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getDesignation() {
        return this.designation;
    }

    public Bangle designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Bangle material(Material material) {
        this.setMaterial(material);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bangle)) {
            return false;
        }
        return getId() != null && getId().equals(((Bangle) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bangle{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", designation='" + getDesignation() + "'" +
            ", gramPerMeterLinearMass=" + getGramPerMeterLinearMass() +
            ", milimeterDiameter=" + getMilimeterDiameter() +
            "}";
    }
}
