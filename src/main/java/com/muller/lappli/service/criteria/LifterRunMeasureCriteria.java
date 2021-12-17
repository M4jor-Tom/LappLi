package com.muller.lappli.service.criteria;

import com.muller.lappli.domain.enumeration.MarkingTechnique;
import com.muller.lappli.domain.enumeration.MarkingType;
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
 * Criteria class for the {@link com.muller.lappli.domain.LifterRunMeasure} entity. This class is used
 * in {@link com.muller.lappli.web.rest.LifterRunMeasureResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /lifter-run-measures?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LifterRunMeasureCriteria implements Serializable, Criteria {

    /**
     * Class for filtering MarkingType
     */
    public static class MarkingTypeFilter extends Filter<MarkingType> {

        public MarkingTypeFilter() {}

        public MarkingTypeFilter(MarkingTypeFilter filter) {
            super(filter);
        }

        @Override
        public MarkingTypeFilter copy() {
            return new MarkingTypeFilter(this);
        }
    }

    /**
     * Class for filtering MarkingTechnique
     */
    public static class MarkingTechniqueFilter extends Filter<MarkingTechnique> {

        public MarkingTechniqueFilter() {}

        public MarkingTechniqueFilter(MarkingTechniqueFilter filter) {
            super(filter);
        }

        @Override
        public MarkingTechniqueFilter copy() {
            return new MarkingTechniqueFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter milimeterDiameter;

    private DoubleFilter meterPerSecondSpeed;

    private MarkingTypeFilter markingType;

    private MarkingTechniqueFilter markingTechnique;

    private DoubleFilter hourPreparationTime;

    private LongFilter lifterId;

    private Boolean distinct;

    public LifterRunMeasureCriteria() {}

    public LifterRunMeasureCriteria(LifterRunMeasureCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.milimeterDiameter = other.milimeterDiameter == null ? null : other.milimeterDiameter.copy();
        this.meterPerSecondSpeed = other.meterPerSecondSpeed == null ? null : other.meterPerSecondSpeed.copy();
        this.markingType = other.markingType == null ? null : other.markingType.copy();
        this.markingTechnique = other.markingTechnique == null ? null : other.markingTechnique.copy();
        this.hourPreparationTime = other.hourPreparationTime == null ? null : other.hourPreparationTime.copy();
        this.lifterId = other.lifterId == null ? null : other.lifterId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LifterRunMeasureCriteria copy() {
        return new LifterRunMeasureCriteria(this);
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

    public DoubleFilter getMeterPerSecondSpeed() {
        return meterPerSecondSpeed;
    }

    public DoubleFilter meterPerSecondSpeed() {
        if (meterPerSecondSpeed == null) {
            meterPerSecondSpeed = new DoubleFilter();
        }
        return meterPerSecondSpeed;
    }

    public void setMeterPerSecondSpeed(DoubleFilter meterPerSecondSpeed) {
        this.meterPerSecondSpeed = meterPerSecondSpeed;
    }

    public MarkingTypeFilter getMarkingType() {
        return markingType;
    }

    public MarkingTypeFilter markingType() {
        if (markingType == null) {
            markingType = new MarkingTypeFilter();
        }
        return markingType;
    }

    public void setMarkingType(MarkingTypeFilter markingType) {
        this.markingType = markingType;
    }

    public MarkingTechniqueFilter getMarkingTechnique() {
        return markingTechnique;
    }

    public MarkingTechniqueFilter markingTechnique() {
        if (markingTechnique == null) {
            markingTechnique = new MarkingTechniqueFilter();
        }
        return markingTechnique;
    }

    public void setMarkingTechnique(MarkingTechniqueFilter markingTechnique) {
        this.markingTechnique = markingTechnique;
    }

    public DoubleFilter getHourPreparationTime() {
        return hourPreparationTime;
    }

    public DoubleFilter hourPreparationTime() {
        if (hourPreparationTime == null) {
            hourPreparationTime = new DoubleFilter();
        }
        return hourPreparationTime;
    }

    public void setHourPreparationTime(DoubleFilter hourPreparationTime) {
        this.hourPreparationTime = hourPreparationTime;
    }

    public LongFilter getLifterId() {
        return lifterId;
    }

    public LongFilter lifterId() {
        if (lifterId == null) {
            lifterId = new LongFilter();
        }
        return lifterId;
    }

    public void setLifterId(LongFilter lifterId) {
        this.lifterId = lifterId;
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
        final LifterRunMeasureCriteria that = (LifterRunMeasureCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(milimeterDiameter, that.milimeterDiameter) &&
            Objects.equals(meterPerSecondSpeed, that.meterPerSecondSpeed) &&
            Objects.equals(markingType, that.markingType) &&
            Objects.equals(markingTechnique, that.markingTechnique) &&
            Objects.equals(hourPreparationTime, that.hourPreparationTime) &&
            Objects.equals(lifterId, that.lifterId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            milimeterDiameter,
            meterPerSecondSpeed,
            markingType,
            markingTechnique,
            hourPreparationTime,
            lifterId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LifterRunMeasureCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (milimeterDiameter != null ? "milimeterDiameter=" + milimeterDiameter + ", " : "") +
            (meterPerSecondSpeed != null ? "meterPerSecondSpeed=" + meterPerSecondSpeed + ", " : "") +
            (markingType != null ? "markingType=" + markingType + ", " : "") +
            (markingTechnique != null ? "markingTechnique=" + markingTechnique + ", " : "") +
            (hourPreparationTime != null ? "hourPreparationTime=" + hourPreparationTime + ", " : "") +
            (lifterId != null ? "lifterId=" + lifterId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
