package com.muller.lappli.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.muller.lappli.domain.ElementKindEdition} entity. This class is used
 * in {@link com.muller.lappli.web.rest.ElementKindEditionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /element-kind-editions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ElementKindEditionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter editionDateTime;

    private DoubleFilter newGramPerMeterLinearMass;

    private DoubleFilter newMilimeterDiameter;

    private DoubleFilter newInsulationThickness;

    private LongFilter editedElementKindId;

    private Boolean distinct;

    public ElementKindEditionCriteria() {}

    public ElementKindEditionCriteria(ElementKindEditionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.editionDateTime = other.editionDateTime == null ? null : other.editionDateTime.copy();
        this.newGramPerMeterLinearMass = other.newGramPerMeterLinearMass == null ? null : other.newGramPerMeterLinearMass.copy();
        this.newMilimeterDiameter = other.newMilimeterDiameter == null ? null : other.newMilimeterDiameter.copy();
        this.newInsulationThickness = other.newInsulationThickness == null ? null : other.newInsulationThickness.copy();
        this.editedElementKindId = other.editedElementKindId == null ? null : other.editedElementKindId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ElementKindEditionCriteria copy() {
        return new ElementKindEditionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getEditionDateTime() {
        return editionDateTime;
    }

    public InstantFilter editionDateTime() {
        if (editionDateTime == null) {
            editionDateTime = new InstantFilter();
        }
        return editionDateTime;
    }

    public void setEditionDateTime(InstantFilter editionDateTime) {
        this.editionDateTime = editionDateTime;
    }

    public DoubleFilter getNewGramPerMeterLinearMass() {
        return newGramPerMeterLinearMass;
    }

    public DoubleFilter newGramPerMeterLinearMass() {
        if (newGramPerMeterLinearMass == null) {
            newGramPerMeterLinearMass = new DoubleFilter();
        }
        return newGramPerMeterLinearMass;
    }

    public void setNewGramPerMeterLinearMass(DoubleFilter newGramPerMeterLinearMass) {
        this.newGramPerMeterLinearMass = newGramPerMeterLinearMass;
    }

    public DoubleFilter getNewMilimeterDiameter() {
        return newMilimeterDiameter;
    }

    public DoubleFilter newMilimeterDiameter() {
        if (newMilimeterDiameter == null) {
            newMilimeterDiameter = new DoubleFilter();
        }
        return newMilimeterDiameter;
    }

    public void setNewMilimeterDiameter(DoubleFilter newMilimeterDiameter) {
        this.newMilimeterDiameter = newMilimeterDiameter;
    }

    public DoubleFilter getNewInsulationThickness() {
        return newInsulationThickness;
    }

    public DoubleFilter newInsulationThickness() {
        if (newInsulationThickness == null) {
            newInsulationThickness = new DoubleFilter();
        }
        return newInsulationThickness;
    }

    public void setNewInsulationThickness(DoubleFilter newInsulationThickness) {
        this.newInsulationThickness = newInsulationThickness;
    }

    public LongFilter getEditedElementKindId() {
        return editedElementKindId;
    }

    public LongFilter editedElementKindId() {
        if (editedElementKindId == null) {
            editedElementKindId = new LongFilter();
        }
        return editedElementKindId;
    }

    public void setEditedElementKindId(LongFilter editedElementKindId) {
        this.editedElementKindId = editedElementKindId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ElementKindEditionCriteria that = (ElementKindEditionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(editionDateTime, that.editionDateTime) &&
            Objects.equals(newGramPerMeterLinearMass, that.newGramPerMeterLinearMass) &&
            Objects.equals(newMilimeterDiameter, that.newMilimeterDiameter) &&
            Objects.equals(newInsulationThickness, that.newInsulationThickness) &&
            Objects.equals(editedElementKindId, that.editedElementKindId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            editionDateTime,
            newGramPerMeterLinearMass,
            newMilimeterDiameter,
            newInsulationThickness,
            editedElementKindId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ElementKindEditionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (editionDateTime != null ? "editionDateTime=" + editionDateTime + ", " : "") +
            (newGramPerMeterLinearMass != null ? "newGramPerMeterLinearMass=" + newGramPerMeterLinearMass + ", " : "") +
            (newMilimeterDiameter != null ? "newMilimeterDiameter=" + newMilimeterDiameter + ", " : "") +
            (newInsulationThickness != null ? "newInsulationThickness=" + newInsulationThickness + ", " : "") +
            (editedElementKindId != null ? "editedElementKindId=" + editedElementKindId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
