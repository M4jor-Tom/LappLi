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
 * Criteria class for the {@link com.muller.lappli.domain.MaterialMarkingStatistic} entity. This class is used
 * in {@link com.muller.lappli.web.rest.MaterialMarkingStatisticResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /material-marking-statistics?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MaterialMarkingStatisticCriteria implements Serializable, Criteria {

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

    private MarkingTypeFilter markingType;

    private MarkingTechniqueFilter markingTechnique;

    private LongFilter meterPerHourSpeed;

    private LongFilter materialId;

    private Boolean distinct;

    public MaterialMarkingStatisticCriteria() {}

    public MaterialMarkingStatisticCriteria(MaterialMarkingStatisticCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.markingType = other.markingType == null ? null : other.markingType.copy();
        this.markingTechnique = other.markingTechnique == null ? null : other.markingTechnique.copy();
        this.meterPerHourSpeed = other.meterPerHourSpeed == null ? null : other.meterPerHourSpeed.copy();
        this.materialId = other.materialId == null ? null : other.materialId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MaterialMarkingStatisticCriteria copy() {
        return new MaterialMarkingStatisticCriteria(this);
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

    public LongFilter getMeterPerHourSpeed() {
        return meterPerHourSpeed;
    }

    public LongFilter meterPerHourSpeed() {
        if (meterPerHourSpeed == null) {
            meterPerHourSpeed = new LongFilter();
        }
        return meterPerHourSpeed;
    }

    public void setMeterPerHourSpeed(LongFilter meterPerHourSpeed) {
        this.meterPerHourSpeed = meterPerHourSpeed;
    }

    public LongFilter getMaterialId() {
        return materialId;
    }

    public LongFilter materialId() {
        if (materialId == null) {
            materialId = new LongFilter();
        }
        return materialId;
    }

    public void setMaterialId(LongFilter materialId) {
        this.materialId = materialId;
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
        final MaterialMarkingStatisticCriteria that = (MaterialMarkingStatisticCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(markingType, that.markingType) &&
            Objects.equals(markingTechnique, that.markingTechnique) &&
            Objects.equals(meterPerHourSpeed, that.meterPerHourSpeed) &&
            Objects.equals(materialId, that.materialId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, markingType, markingTechnique, meterPerHourSpeed, materialId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaterialMarkingStatisticCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (markingType != null ? "markingType=" + markingType + ", " : "") +
            (markingTechnique != null ? "markingTechnique=" + markingTechnique + ", " : "") +
            (meterPerHourSpeed != null ? "meterPerHourSpeed=" + meterPerHourSpeed + ", " : "") +
            (materialId != null ? "materialId=" + materialId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
