package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractUniformAtom;
import com.muller.lappli.domain.enumeration.Color;
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
public class CustomComponent extends AbstractUniformAtom<CustomComponent> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "number")
    private Long number;

    @Column(name = "designation")
    private String designation;

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
        return getId() != null && getId().equals(((CustomComponent) o).getId());
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
