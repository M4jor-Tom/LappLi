package com.muller.lappli.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ElementKind.
 */
@Entity
@Table(name = "element_kind")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ElementKind implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "designation", nullable = false)
    private String designation;

    @NotNull
    @Column(name = "gram_per_meter_mass", nullable = false)
    private Double gramPerMeterMass;

    @NotNull
    @Column(name = "milimeter_diameter", nullable = false)
    private Double milimeterDiameter;

    @NotNull
    @Column(name = "insulation_thickness", nullable = false)
    private Double insulationThickness;

    @ManyToOne
    private Copper copper;

    @ManyToOne
    private Material insulationMaterial;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ElementKind id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return this.designation;
    }

    public ElementKind designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getGramPerMeterMass() {
        return this.gramPerMeterMass;
    }

    public ElementKind gramPerMeterMass(Double gramPerMeterMass) {
        this.setGramPerMeterMass(gramPerMeterMass);
        return this;
    }

    public void setGramPerMeterMass(Double gramPerMeterMass) {
        this.gramPerMeterMass = gramPerMeterMass;
    }

    public Double getMilimeterDiameter() {
        return this.milimeterDiameter;
    }

    public ElementKind milimeterDiameter(Double milimeterDiameter) {
        this.setMilimeterDiameter(milimeterDiameter);
        return this;
    }

    public void setMilimeterDiameter(Double milimeterDiameter) {
        this.milimeterDiameter = milimeterDiameter;
    }

    public Double getInsulationThickness() {
        return this.insulationThickness;
    }

    public ElementKind insulationThickness(Double insulationThickness) {
        this.setInsulationThickness(insulationThickness);
        return this;
    }

    public void setInsulationThickness(Double insulationThickness) {
        this.insulationThickness = insulationThickness;
    }

    public Copper getCopper() {
        return this.copper;
    }

    public void setCopper(Copper copper) {
        this.copper = copper;
    }

    public ElementKind copper(Copper copper) {
        this.setCopper(copper);
        return this;
    }

    public Material getInsulationMaterial() {
        return this.insulationMaterial;
    }

    public void setInsulationMaterial(Material material) {
        this.insulationMaterial = material;
    }

    public ElementKind insulationMaterial(Material material) {
        this.setInsulationMaterial(material);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ElementKind)) {
            return false;
        }
        return id != null && id.equals(((ElementKind) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ElementKind{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", gramPerMeterMass=" + getGramPerMeterMass() +
            ", milimeterDiameter=" + getMilimeterDiameter() +
            ", insulationThickness=" + getInsulationThickness() +
            "}";
    }
}
