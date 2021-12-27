package com.muller.lappli.service.criteria;

import com.muller.lappli.domain.enumeration.Color;
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
 * Criteria class for the {@link com.muller.lappli.domain.OneStudySupply} entity. This class is used
 * in {@link com.muller.lappli.web.rest.OneStudySupplyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /one-study-supplies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OneStudySupplyCriteria implements Serializable, Criteria {

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
     * Class for filtering Color
     */
    public static class ColorFilter extends Filter<Color> {

        public ColorFilter() {}

        public ColorFilter(ColorFilter filter) {
            super(filter);
        }

        @Override
        public ColorFilter copy() {
            return new ColorFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter apparitions;

    private LongFilter number;

    private StringFilter designation;

    private StringFilter description;

    private MarkingTypeFilter markingType;

    private DoubleFilter gramPerMeterLinearMass;

    private DoubleFilter milimeterDiameter;

    private ColorFilter surfaceColor;

    private LongFilter surfaceMaterialId;

    private LongFilter positionId;

    private LongFilter strandId;

    private Boolean distinct;

    public OneStudySupplyCriteria() {}

    public OneStudySupplyCriteria(OneStudySupplyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.apparitions = other.apparitions == null ? null : other.apparitions.copy();
        this.number = other.number == null ? null : other.number.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.markingType = other.markingType == null ? null : other.markingType.copy();
        this.gramPerMeterLinearMass = other.gramPerMeterLinearMass == null ? null : other.gramPerMeterLinearMass.copy();
        this.milimeterDiameter = other.milimeterDiameter == null ? null : other.milimeterDiameter.copy();
        this.surfaceColor = other.surfaceColor == null ? null : other.surfaceColor.copy();
        this.surfaceMaterialId = other.surfaceMaterialId == null ? null : other.surfaceMaterialId.copy();
        this.positionId = other.positionId == null ? null : other.positionId.copy();
        this.strandId = other.strandId == null ? null : other.strandId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OneStudySupplyCriteria copy() {
        return new OneStudySupplyCriteria(this);
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

    public LongFilter getApparitions() {
        return apparitions;
    }

    public LongFilter apparitions() {
        if (apparitions == null) {
            apparitions = new LongFilter();
        }
        return apparitions;
    }

    public void setApparitions(LongFilter apparitions) {
        this.apparitions = apparitions;
    }

    public LongFilter getNumber() {
        return number;
    }

    public LongFilter number() {
        if (number == null) {
            number = new LongFilter();
        }
        return number;
    }

    public void setNumber(LongFilter number) {
        this.number = number;
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

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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

    public ColorFilter getSurfaceColor() {
        return surfaceColor;
    }

    public ColorFilter surfaceColor() {
        if (surfaceColor == null) {
            surfaceColor = new ColorFilter();
        }
        return surfaceColor;
    }

    public void setSurfaceColor(ColorFilter surfaceColor) {
        this.surfaceColor = surfaceColor;
    }

    public LongFilter getSurfaceMaterialId() {
        return surfaceMaterialId;
    }

    public LongFilter surfaceMaterialId() {
        if (surfaceMaterialId == null) {
            surfaceMaterialId = new LongFilter();
        }
        return surfaceMaterialId;
    }

    public void setSurfaceMaterialId(LongFilter surfaceMaterialId) {
        this.surfaceMaterialId = surfaceMaterialId;
    }

    public LongFilter getPositionId() {
        return positionId;
    }

    public LongFilter positionId() {
        if (positionId == null) {
            positionId = new LongFilter();
        }
        return positionId;
    }

    public void setPositionId(LongFilter positionId) {
        this.positionId = positionId;
    }

    public LongFilter getStrandId() {
        return strandId;
    }

    public LongFilter strandId() {
        if (strandId == null) {
            strandId = new LongFilter();
        }
        return strandId;
    }

    public void setStrandId(LongFilter strandId) {
        this.strandId = strandId;
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
        final OneStudySupplyCriteria that = (OneStudySupplyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(apparitions, that.apparitions) &&
            Objects.equals(number, that.number) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(description, that.description) &&
            Objects.equals(markingType, that.markingType) &&
            Objects.equals(gramPerMeterLinearMass, that.gramPerMeterLinearMass) &&
            Objects.equals(milimeterDiameter, that.milimeterDiameter) &&
            Objects.equals(surfaceColor, that.surfaceColor) &&
            Objects.equals(surfaceMaterialId, that.surfaceMaterialId) &&
            Objects.equals(positionId, that.positionId) &&
            Objects.equals(strandId, that.strandId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            apparitions,
            number,
            designation,
            description,
            markingType,
            gramPerMeterLinearMass,
            milimeterDiameter,
            surfaceColor,
            surfaceMaterialId,
            positionId,
            strandId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OneStudySupplyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (apparitions != null ? "apparitions=" + apparitions + ", " : "") +
            (number != null ? "number=" + number + ", " : "") +
            (designation != null ? "designation=" + designation + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (markingType != null ? "markingType=" + markingType + ", " : "") +
            (gramPerMeterLinearMass != null ? "gramPerMeterLinearMass=" + gramPerMeterLinearMass + ", " : "") +
            (milimeterDiameter != null ? "milimeterDiameter=" + milimeterDiameter + ", " : "") +
            (surfaceColor != null ? "surfaceColor=" + surfaceColor + ", " : "") +
            (surfaceMaterialId != null ? "surfaceMaterialId=" + surfaceMaterialId + ", " : "") +
            (positionId != null ? "positionId=" + positionId + ", " : "") +
            (strandId != null ? "strandId=" + strandId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
