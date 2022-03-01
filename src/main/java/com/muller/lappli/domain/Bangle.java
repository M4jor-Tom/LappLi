package com.muller.lappli.domain;

import com.muller.lappli.domain.abstracts.AbstractAssemblableAtom;
import com.muller.lappli.domain.interfaces.Article;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Bangle.
 */
@Entity
@Table(name = "bangle")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bangle extends AbstractAssemblableAtom<Bangle> implements Article, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false, unique = true)
    private Long number;

    @NotNull
    @Column(name = "designation", nullable = false, unique = true)
    private String designation;

    @NotNull
    @Column(name = "gram_per_meter_linear_mass", nullable = false)
    private Double gramPerMeterLinearMass;

    @NotNull
    @Column(name = "milimeter_diameter", nullable = false)
    private Double milimeterDiameter;

    @ManyToOne(optional = false)
    @NotNull
    //@JsonIgnoreProperties(value = { "materialMarkingStatistics" }, allowSetters = true)
    private Material material;

    @Override
    public Bangle getThis() {
        return this;
    }

    @Override
    public Long getArticleNumber() {
        return getNumber();
    }

    @Override
    public Boolean isUtility() {
        return false;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bangle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getGramPerMeterLinearMass() {
        return this.gramPerMeterLinearMass;
    }

    public Bangle gramPerMeterLinearMass(Double gramPerMeterLinearMass) {
        this.setGramPerMeterLinearMass(gramPerMeterLinearMass);
        return this;
    }

    public void setGramPerMeterLinearMass(Double gramPerMeterLinearMass) {
        this.gramPerMeterLinearMass = gramPerMeterLinearMass;
    }

    public Double getMilimeterDiameter() {
        return this.milimeterDiameter;
    }

    public Bangle milimeterDiameter(Double milimeterDiameter) {
        this.setMilimeterDiameter(milimeterDiameter);
        return this;
    }

    public void setMilimeterDiameter(Double milimeterDiameter) {
        this.milimeterDiameter = milimeterDiameter;
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
        return id != null && id.equals(((Bangle) o).id);
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
