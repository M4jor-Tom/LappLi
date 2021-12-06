package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractEdition;
import com.muller.lappli.domain.interfaces.NotNullForceable;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * This domain class serves EditionKind as an implementation of IEdition<ElementKind>
 *
 * That means that its fields will be used to edit any ElementKind instance
 * at a given Instant
 */
@Entity
@Table(name = "element_kind_edition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ElementKindEdition extends AbstractEdition<ElementKind> implements NotNullForceable<ElementKindEdition>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "edition_date_time", nullable = false)
    private Instant editionDateTime;

    @Column(name = "new_gram_per_meter_linear_mass")
    private Double newGramPerMeterLinearMass;

    @Column(name = "new_milimeter_diameter")
    private Double newMilimeterDiameter;

    @Column(name = "new_insulation_thickness")
    private Double newInsulationThickness;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "copper", "insulationMaterial" }, allowSetters = true)
    private ElementKind editedElementKind;

    public ElementKindEdition() {
        this(Double.NaN, Double.NaN, Double.NaN, new ElementKind());
    }

    public ElementKindEdition(
        Double newGramPerMeterLinearMass,
        Double newMilimeterDiameter,
        Double newInsulationThickness,
        ElementKind editedElementKind
    ) {
        setEditionDateTime(Instant.now());
        setNewGramPerMeterLinearMass(newGramPerMeterLinearMass);
        setNewMilimeterDiameter(newMilimeterDiameter);
        setNewInsulationThickness(newInsulationThickness);
        setEditedElementKind(editedElementKind);
    }

    @Override
    public ElementKind update(ElementKind elementKind) {
        if (!getNewGramPerMeterLinearMass().isNaN()) {
            elementKind.setGramPerMeterLinearMass(getNewGramPerMeterLinearMass());
        }
        if (!getNewMilimeterDiameter().isNaN()) {
            elementKind.setMilimeterDiameter(getNewMilimeterDiameter());
        }
        if (!getNewInsulationThickness().isNaN()) {
            elementKind.setInsulationThickness(getNewInsulationThickness());
        }

        return elementKind;
    }

    @Override
    public Instant getEditionInstant() {
        return getEditionDateTime();
    }

    @Override
    protected void setEditionInstant(Instant editionInstant) {
        setEditionDateTime(editionInstant);
    }

    @Override
    public ElementKindEdition forceNotNull() {
        if (getEditionDateTime() == null) {
            epochEditionTime();
        }
        if (getEditedElementKind() == null) {
            setEditedElementKind(new ElementKind());
        }
        if (getNewGramPerMeterLinearMass() == null) {
            setNewGramPerMeterLinearMass(Double.NaN);
        }
        if (getNewMilimeterDiameter() == null) {
            setNewMilimeterDiameter(Double.NaN);
        }
        if (getNewInsulationThickness() == null) {
            setNewInsulationThickness(Double.NaN);
        }
        if (getEditedElementKind() == null) {
            setEditedElementKind(new ElementKind());
        }

        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ElementKindEdition id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getEditionDateTime() {
        return this.editionDateTime;
    }

    private ElementKindEdition editionDateTime(Instant editionDateTime) {
        this.setEditionDateTime(editionDateTime);
        return this;
    }

    private void setEditionDateTime(Instant editionDateTime) {
        this.editionDateTime = editionDateTime;
    }

    public Double getNewGramPerMeterLinearMass() {
        return this.newGramPerMeterLinearMass;
    }

    public ElementKindEdition newGramPerMeterLinearMass(Double newGramPerMeterLinearMass) {
        this.setNewGramPerMeterLinearMass(newGramPerMeterLinearMass);
        return this;
    }

    public void setNewGramPerMeterLinearMass(Double newGramPerMeterLinearMass) {
        this.newGramPerMeterLinearMass = newGramPerMeterLinearMass;
    }

    public Double getNewMilimeterDiameter() {
        return this.newMilimeterDiameter;
    }

    public ElementKindEdition newMilimeterDiameter(Double newMilimeterDiameter) {
        this.setNewMilimeterDiameter(newMilimeterDiameter);
        return this;
    }

    public void setNewMilimeterDiameter(Double newMilimeterDiameter) {
        this.newMilimeterDiameter = newMilimeterDiameter;
    }

    public Double getNewInsulationThickness() {
        return this.newInsulationThickness;
    }

    public ElementKindEdition newInsulationThickness(Double newInsulationThickness) {
        this.setNewInsulationThickness(newInsulationThickness);
        return this;
    }

    public void setNewInsulationThickness(Double newInsulationThickness) {
        this.newInsulationThickness = newInsulationThickness;
    }

    public ElementKind getEditedElementKind() {
        return this.editedElementKind;
    }

    public void setEditedElementKind(ElementKind elementKind) {
        this.editedElementKind = elementKind;
    }

    public ElementKindEdition editedElementKind(ElementKind elementKind) {
        this.setEditedElementKind(elementKind);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ElementKindEdition)) {
            return false;
        }
        return id != null && id.equals(((ElementKindEdition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ElementKindEdition{" +
            "id=" + getId() +
            ", editionDateTime='" + getEditionDateTime() + "'" +
            ", newGramPerMeterLinearMass=" + getNewGramPerMeterLinearMass() +
            ", newMilimeterDiameter=" + getNewMilimeterDiameter() +
            ", newInsulationThickness=" + getNewInsulationThickness() +
            "}";
    }
}
