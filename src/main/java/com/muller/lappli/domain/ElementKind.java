package com.muller.lappli.domain;

import com.muller.lappli.domain.abstracts.AbstractDomainObject;
import com.muller.lappli.domain.interfaces.Commitable;
import com.muller.lappli.domain.interfaces.Designable;
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
public class ElementKind extends AbstractDomainObject<ElementKind> implements Commitable<ElementKind>, Designable, Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "designation", nullable = false, unique = true)
    private String designation;

    @NotNull
    @Column(name = "gram_per_meter_linear_mass", nullable = false)
    private Double gramPerMeterLinearMass;

    @NotNull
    @Column(name = "milimeter_diameter", nullable = false)
    private Double milimeterDiameter;

    @NotNull
    @Column(name = "milimeter_insulation_thickness", nullable = false)
    private Double milimeterInsulationThickness;

    @ManyToOne(optional = false)
    @NotNull
    private Copper copper;

    @ManyToOne(optional = false)
    @NotNull
    //@JsonIgnoreProperties(value = { "materialMarkingStatistics" }, allowSetters = true)
    private Material insulationMaterial;

    @Transient
    private EditionListManager<ElementKind> editionListManager;

    public ElementKind() {
        super();
    }

    public ElementKind(
        String designation,
        Double gramPerMeterLinearMass,
        Double milimeterDiameter,
        Double insulationThickness,
        Copper copper,
        Material insulationMaterial
    ) {
        super();
        setDesignation(designation);
        setGramPerMeterLinearMass(gramPerMeterLinearMass);
        setMilimeterDiameter(milimeterDiameter);
        setMilimeterInsulationThickness(insulationThickness);
        setCopper(copper);
        setInsulationMaterial(insulationMaterial);
    }

    protected ElementKind(ElementKind elementKind) {
        this(
            String.valueOf(elementKind.getDesignation()),
            Double.valueOf(elementKind.getGramPerMeterLinearMass()),
            Double.valueOf(elementKind.getMilimeterDiameter()),
            Double.valueOf(elementKind.getMilimeterInsulationThickness()),
            (Copper) elementKind.getCopper().copy(),
            (Material) elementKind.getInsulationMaterial().copy()
        );
    }

    @Override
    public ElementKind copy() {
        return new ElementKind(this).id(getId());
    }

    @Override
    public ElementKind getThis() {
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
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

    public Double getGramPerMeterLinearMass() {
        return this.gramPerMeterLinearMass;
    }

    public ElementKind gramPerMeterLinearMass(Double gramPerMeterLinearMass) {
        this.setGramPerMeterLinearMass(gramPerMeterLinearMass);
        return this;
    }

    public void setGramPerMeterLinearMass(Double gramPerMeterLinearMass) {
        this.gramPerMeterLinearMass = gramPerMeterLinearMass;
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

    public Double getMilimeterInsulationThickness() {
        return this.milimeterInsulationThickness;
    }

    public ElementKind milimeterInsulationThickness(Double milimeterInsulationThickness) {
        this.setMilimeterInsulationThickness(milimeterInsulationThickness);
        return this;
    }

    public void setMilimeterInsulationThickness(Double milimeterInsulationThickness) {
        this.milimeterInsulationThickness = milimeterInsulationThickness;
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

    @Override
    public EditionListManager<ElementKind> getEditionListManager() {
        return editionListManager;
    }

    @Override
    public void setEditionListManager(EditionListManager<ElementKind> editionListManager) {
        this.editionListManager = editionListManager;
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
        return getId() != null && getId().equals(((ElementKind) o).getId());
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
            ", gramPerMeterLinearMass=" + getGramPerMeterLinearMass() +
            ", milimeterDiameter=" + getMilimeterDiameter() +
            ", milimeterInsulationThickness=" + getMilimeterInsulationThickness() +
            "}";
    }
}
