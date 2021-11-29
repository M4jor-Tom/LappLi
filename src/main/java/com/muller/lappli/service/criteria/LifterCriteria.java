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
 * Criteria class for the {@link com.muller.lappli.domain.Lifter} entity. This class is used
 * in {@link com.muller.lappli.web.rest.LifterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /lifters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LifterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter index;

    private DoubleFilter minimumMilimeterDiameter;

    private DoubleFilter maximumMilimeterDiameter;

    private BooleanFilter supportsSpirallyColoredMarkingType;

    private BooleanFilter supportsLongitudinallyColoredMarkingType;

    private BooleanFilter supportsNumberedMarkingType;

    private BooleanFilter supportsInkJetMarkingTechnique;

    private BooleanFilter supportsRsdMarkingTechnique;

    private Boolean distinct;

    public LifterCriteria() {}

    public LifterCriteria(LifterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.index = other.index == null ? null : other.index.copy();
        this.minimumMilimeterDiameter = other.minimumMilimeterDiameter == null ? null : other.minimumMilimeterDiameter.copy();
        this.maximumMilimeterDiameter = other.maximumMilimeterDiameter == null ? null : other.maximumMilimeterDiameter.copy();
        this.supportsSpirallyColoredMarkingType =
            other.supportsSpirallyColoredMarkingType == null ? null : other.supportsSpirallyColoredMarkingType.copy();
        this.supportsLongitudinallyColoredMarkingType =
            other.supportsLongitudinallyColoredMarkingType == null ? null : other.supportsLongitudinallyColoredMarkingType.copy();
        this.supportsNumberedMarkingType = other.supportsNumberedMarkingType == null ? null : other.supportsNumberedMarkingType.copy();
        this.supportsInkJetMarkingTechnique =
            other.supportsInkJetMarkingTechnique == null ? null : other.supportsInkJetMarkingTechnique.copy();
        this.supportsRsdMarkingTechnique = other.supportsRsdMarkingTechnique == null ? null : other.supportsRsdMarkingTechnique.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LifterCriteria copy() {
        return new LifterCriteria(this);
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

    public LongFilter getIndex() {
        return index;
    }

    public LongFilter index() {
        if (index == null) {
            index = new LongFilter();
        }
        return index;
    }

    public void setIndex(LongFilter index) {
        this.index = index;
    }

    public DoubleFilter getMinimumMilimeterDiameter() {
        return minimumMilimeterDiameter;
    }

    public DoubleFilter minimumMilimeterDiameter() {
        if (minimumMilimeterDiameter == null) {
            minimumMilimeterDiameter = new DoubleFilter();
        }
        return minimumMilimeterDiameter;
    }

    public void setMinimumMilimeterDiameter(DoubleFilter minimumMilimeterDiameter) {
        this.minimumMilimeterDiameter = minimumMilimeterDiameter;
    }

    public DoubleFilter getMaximumMilimeterDiameter() {
        return maximumMilimeterDiameter;
    }

    public DoubleFilter maximumMilimeterDiameter() {
        if (maximumMilimeterDiameter == null) {
            maximumMilimeterDiameter = new DoubleFilter();
        }
        return maximumMilimeterDiameter;
    }

    public void setMaximumMilimeterDiameter(DoubleFilter maximumMilimeterDiameter) {
        this.maximumMilimeterDiameter = maximumMilimeterDiameter;
    }

    public BooleanFilter getSupportsSpirallyColoredMarkingType() {
        return supportsSpirallyColoredMarkingType;
    }

    public BooleanFilter supportsSpirallyColoredMarkingType() {
        if (supportsSpirallyColoredMarkingType == null) {
            supportsSpirallyColoredMarkingType = new BooleanFilter();
        }
        return supportsSpirallyColoredMarkingType;
    }

    public void setSupportsSpirallyColoredMarkingType(BooleanFilter supportsSpirallyColoredMarkingType) {
        this.supportsSpirallyColoredMarkingType = supportsSpirallyColoredMarkingType;
    }

    public BooleanFilter getSupportsLongitudinallyColoredMarkingType() {
        return supportsLongitudinallyColoredMarkingType;
    }

    public BooleanFilter supportsLongitudinallyColoredMarkingType() {
        if (supportsLongitudinallyColoredMarkingType == null) {
            supportsLongitudinallyColoredMarkingType = new BooleanFilter();
        }
        return supportsLongitudinallyColoredMarkingType;
    }

    public void setSupportsLongitudinallyColoredMarkingType(BooleanFilter supportsLongitudinallyColoredMarkingType) {
        this.supportsLongitudinallyColoredMarkingType = supportsLongitudinallyColoredMarkingType;
    }

    public BooleanFilter getSupportsNumberedMarkingType() {
        return supportsNumberedMarkingType;
    }

    public BooleanFilter supportsNumberedMarkingType() {
        if (supportsNumberedMarkingType == null) {
            supportsNumberedMarkingType = new BooleanFilter();
        }
        return supportsNumberedMarkingType;
    }

    public void setSupportsNumberedMarkingType(BooleanFilter supportsNumberedMarkingType) {
        this.supportsNumberedMarkingType = supportsNumberedMarkingType;
    }

    public BooleanFilter getSupportsInkJetMarkingTechnique() {
        return supportsInkJetMarkingTechnique;
    }

    public BooleanFilter supportsInkJetMarkingTechnique() {
        if (supportsInkJetMarkingTechnique == null) {
            supportsInkJetMarkingTechnique = new BooleanFilter();
        }
        return supportsInkJetMarkingTechnique;
    }

    public void setSupportsInkJetMarkingTechnique(BooleanFilter supportsInkJetMarkingTechnique) {
        this.supportsInkJetMarkingTechnique = supportsInkJetMarkingTechnique;
    }

    public BooleanFilter getSupportsRsdMarkingTechnique() {
        return supportsRsdMarkingTechnique;
    }

    public BooleanFilter supportsRsdMarkingTechnique() {
        if (supportsRsdMarkingTechnique == null) {
            supportsRsdMarkingTechnique = new BooleanFilter();
        }
        return supportsRsdMarkingTechnique;
    }

    public void setSupportsRsdMarkingTechnique(BooleanFilter supportsRsdMarkingTechnique) {
        this.supportsRsdMarkingTechnique = supportsRsdMarkingTechnique;
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
        final LifterCriteria that = (LifterCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(index, that.index) &&
            Objects.equals(minimumMilimeterDiameter, that.minimumMilimeterDiameter) &&
            Objects.equals(maximumMilimeterDiameter, that.maximumMilimeterDiameter) &&
            Objects.equals(supportsSpirallyColoredMarkingType, that.supportsSpirallyColoredMarkingType) &&
            Objects.equals(supportsLongitudinallyColoredMarkingType, that.supportsLongitudinallyColoredMarkingType) &&
            Objects.equals(supportsNumberedMarkingType, that.supportsNumberedMarkingType) &&
            Objects.equals(supportsInkJetMarkingTechnique, that.supportsInkJetMarkingTechnique) &&
            Objects.equals(supportsRsdMarkingTechnique, that.supportsRsdMarkingTechnique) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            index,
            minimumMilimeterDiameter,
            maximumMilimeterDiameter,
            supportsSpirallyColoredMarkingType,
            supportsLongitudinallyColoredMarkingType,
            supportsNumberedMarkingType,
            supportsInkJetMarkingTechnique,
            supportsRsdMarkingTechnique,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LifterCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (index != null ? "index=" + index + ", " : "") +
            (minimumMilimeterDiameter != null ? "minimumMilimeterDiameter=" + minimumMilimeterDiameter + ", " : "") +
            (maximumMilimeterDiameter != null ? "maximumMilimeterDiameter=" + maximumMilimeterDiameter + ", " : "") +
            (supportsSpirallyColoredMarkingType != null ? "supportsSpirallyColoredMarkingType=" + supportsSpirallyColoredMarkingType + ", " : "") +
            (supportsLongitudinallyColoredMarkingType != null ? "supportsLongitudinallyColoredMarkingType=" + supportsLongitudinallyColoredMarkingType + ", " : "") +
            (supportsNumberedMarkingType != null ? "supportsNumberedMarkingType=" + supportsNumberedMarkingType + ", " : "") +
            (supportsInkJetMarkingTechnique != null ? "supportsInkJetMarkingTechnique=" + supportsInkJetMarkingTechnique + ", " : "") +
            (supportsRsdMarkingTechnique != null ? "supportsRsdMarkingTechnique=" + supportsRsdMarkingTechnique + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
