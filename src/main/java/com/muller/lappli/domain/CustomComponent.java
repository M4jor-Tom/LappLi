package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractDomainObject;
import com.muller.lappli.domain.enumeration.Color;
import com.muller.lappli.domain.interfaces.CylindricComponent;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CustomComponent.
 */
@Entity
@Table(name = "custom_component")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomComponent extends AbstractDomainObject<CustomComponent> implements CylindricComponent, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private Long number;

    @Column(name = "designation")
    private String designation;

    @NotNull
    @Column(name = "gram_per_meter_linear_mass", nullable = false)
    private Double gramPerMeterLinearMass;

    @NotNull
    @Column(name = "milimeter_diameter", nullable = false)
    private Double milimeterDiameter;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "surface_color", nullable = false)
    private Color surfaceColor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "materialMarkingStatistics" }, allowSetters = true)
    private Material surfaceMaterial;

    public CustomComponent() {
        super();
        setNumber(null);
        setDesignation(null);
        setGramPerMeterLinearMass(Double.NaN);
        setMilimeterDiameter(Double.NaN);
        setSurfaceColor(null);
        setSurfaceMaterial(null);
    }

    @Override
    public CustomComponent getThis() {
        return this;
    }

    @Override
    public Boolean isUtility() {
        return true;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public Long getId() {
        return this.id;
    }

    public CustomComponent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return this.number;
    }

    public CustomComponent number(Long number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Override
    public String getDesignation() {
        return this.designation;
    }

    public CustomComponent designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public Double getGramPerMeterLinearMass() {
        return this.gramPerMeterLinearMass;
    }

    public CustomComponent gramPerMeterLinearMass(Double gramPerMeterLinearMass) {
        this.setGramPerMeterLinearMass(gramPerMeterLinearMass);
        return this;
    }

    public void setGramPerMeterLinearMass(Double gramPerMeterLinearMass) {
        this.gramPerMeterLinearMass = gramPerMeterLinearMass;
    }

    @Override
    public Double getMilimeterDiameter() {
        return this.milimeterDiameter;
    }

    public CustomComponent milimeterDiameter(Double milimeterDiameter) {
        this.setMilimeterDiameter(milimeterDiameter);
        return this;
    }

    public void setMilimeterDiameter(Double milimeterDiameter) {
        this.milimeterDiameter = milimeterDiameter;
    }

    public Color getSurfaceColor() {
        return this.surfaceColor;
    }

    public CustomComponent surfaceColor(Color surfaceColor) {
        this.setSurfaceColor(surfaceColor);
        return this;
    }

    public void setSurfaceColor(Color surfaceColor) {
        this.surfaceColor = surfaceColor;
    }

    public Material getSurfaceMaterial() {
        return this.surfaceMaterial;
    }

    public void setSurfaceMaterial(Material material) {
        this.surfaceMaterial = material;
    }

    public CustomComponent surfaceMaterial(Material material) {
        this.setSurfaceMaterial(material);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomComponent)) {
            return false;
        }
        return id != null && id.equals(((CustomComponent) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomComponent{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", designation='" + getDesignation() + "'" +
            ", gramPerMeterLinearMass=" + getGramPerMeterLinearMass() +
            ", milimeterDiameter=" + getMilimeterDiameter() +
            ", surfaceColor='" + getSurfaceColor() + "'" +
            "}";
    }
}
