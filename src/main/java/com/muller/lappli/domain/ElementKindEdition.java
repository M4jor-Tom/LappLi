package com.muller.lappli.domain;

import com.muller.lappli.domain.abstracts.AbstractEdition;
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
public class ElementKindEdition extends AbstractEdition<ElementKind> implements Serializable {

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

    @Column(name = "new_milimeter_insulation_thickness")
    private Double newMilimeterInsulationThickness;

    @ManyToOne(optional = false)
    @NotNull
    //@JsonIgnoreProperties(value = { "copper", "insulationMaterial" }, allowSetters = true)
    private ElementKind editedElementKind;

    public ElementKindEdition() {
        super();
    }

    @Override
    public ElementKindEdition getThis() {
        return this;
    }

    @Override
    public ElementKind getEditedCommitable() {
        return getEditedElementKind();
    }

    @Override
    public ElementKind update(ElementKind elementKind) {
        if (getNewGramPerMeterLinearMass() != null) {
            elementKind.setGramPerMeterLinearMass(getNewGramPerMeterLinearMass());
        }
        if (getNewMilimeterDiameter() != null) {
            elementKind.setMilimeterDiameter(getNewMilimeterDiameter());
        }
        if (getNewMilimeterInsulationThickness() != null) {
            elementKind.setMilimeterInsulationThickness(getNewMilimeterInsulationThickness());
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

    public Double getNewMilimeterInsulationThickness() {
        return this.newMilimeterInsulationThickness;
    }

    public ElementKindEdition newMilimeterInsulationThickness(Double newMilimeterInsulationThickness) {
        this.setNewMilimeterInsulationThickness(newMilimeterInsulationThickness);
        return this;
    }

    public void setNewMilimeterInsulationThickness(Double newMilimeterInsulationThickness) {
        this.newMilimeterInsulationThickness = newMilimeterInsulationThickness;
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
            ", newMilimeterInsulationThickness=" + getNewMilimeterInsulationThickness() +
            "}";
    }
}
