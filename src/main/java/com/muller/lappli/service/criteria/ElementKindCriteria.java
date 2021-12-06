package com.muller.lappli.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.muller.lappli.domain.ElementKind} entity. This class is used
 * in {@link com.muller.lappli.web.rest.ElementKindResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /element-kinds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ElementKindCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter designation;

    private DoubleFilter gramPerMeterLinearMass;

    private DoubleFilter milimeterDiameter;

    private DoubleFilter insulationThickness;

    private LongFilter copperId;

    private LongFilter insulationMaterialId;

    private Boolean distinct;

    public ElementKindCriteria() {}

    public ElementKindCriteria(ElementKindCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.gramPerMeterLinearMass = other.gramPerMeterLinearMass == null ? null : other.gramPerMeterLinearMass.copy();
        this.milimeterDiameter = other.milimeterDiameter == null ? null : other.milimeterDiameter.copy();
        this.insulationThickness = other.insulationThickness == null ? null : other.insulationThickness.copy();
        this.copperId = other.copperId == null ? null : other.copperId.copy();
        this.insulationMaterialId = other.insulationMaterialId == null ? null : other.insulationMaterialId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ElementKindCriteria copy() {
        return new ElementKindCriteria(this);
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

    public StringFilter getDesignation() {
        return designation;
    }

    public StringFilter designation() {
        if (designation == null) {
            designation = new StringFilter();
        }
        return designation;
    }

    public void setDesignation(StringFilter designation) {
        this.designation = designation;
    }

    public DoubleFilter getGramPerMeterLinearMass() {
        return gramPerMeterLinearMass;
    }

    public DoubleFilter gramPerMeterLinearMass() {
        if (gramPerMeterLinearMass == null) {
            gramPerMeterLinearMass = new DoubleFilter();
        }
        return gramPerMeterLinearMass;
    }

    public void setGramPerMeterLinearMass(DoubleFilter gramPerMeterLinearMass) {
        this.gramPerMeterLinearMass = gramPerMeterLinearMass;
    }

    public DoubleFilter getMilimeterDiameter() {
        return milimeterDiameter;
    }

    public DoubleFilter milimeterDiameter() {
        if (milimeterDiameter == null) {
            milimeterDiameter = new DoubleFilter();
        }
        return milimeterDiameter;
    }

    public void setMilimeterDiameter(DoubleFilter milimeterDiameter) {
        this.milimeterDiameter = milimeterDiameter;
    }

    public DoubleFilter getInsulationThickness() {
        return insulationThickness;
    }

    public DoubleFilter insulationThickness() {
        if (insulationThickness == null) {
            insulationThickness = new DoubleFilter();
        }
        return insulationThickness;
    }

    public void setInsulationThickness(DoubleFilter insulationThickness) {
        this.insulationThickness = insulationThickness;
    }

    public LongFilter getCopperId() {
        return copperId;
    }

    public LongFilter copperId() {
        if (copperId == null) {
            copperId = new LongFilter();
        }
        return copperId;
    }

    public void setCopperId(LongFilter copperId) {
        this.copperId = copperId;
    }

    public LongFilter getInsulationMaterialId() {
        return insulationMaterialId;
    }

    public LongFilter insulationMaterialId() {
        if (insulationMaterialId == null) {
            insulationMaterialId = new LongFilter();
        }
        return insulationMaterialId;
    }

    public void setInsulationMaterialId(LongFilter insulationMaterialId) {
        this.insulationMaterialId = insulationMaterialId;
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
        final ElementKindCriteria that = (ElementKindCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(gramPerMeterLinearMass, that.gramPerMeterLinearMass) &&
            Objects.equals(milimeterDiameter, that.milimeterDiameter) &&
            Objects.equals(insulationThickness, that.insulationThickness) &&
            Objects.equals(copperId, that.copperId) &&
            Objects.equals(insulationMaterialId, that.insulationMaterialId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            designation,
            gramPerMeterLinearMass,
            milimeterDiameter,
            insulationThickness,
            copperId,
            insulationMaterialId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ElementKindCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (designation != null ? "designation=" + designation + ", " : "") +
            (gramPerMeterLinearMass != null ? "gramPerMeterLinearMass=" + gramPerMeterLinearMass + ", " : "") +
            (milimeterDiameter != null ? "milimeterDiameter=" + milimeterDiameter + ", " : "") +
            (insulationThickness != null ? "insulationThickness=" + insulationThickness + ", " : "") +
            (copperId != null ? "copperId=" + copperId + ", " : "") +
            (insulationMaterialId != null ? "insulationMaterialId=" + insulationMaterialId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
