package com.muller.lappli.service.criteria;

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
 * Criteria class for the {@link com.muller.lappli.domain.ElementSupply} entity. This class is used
 * in {@link com.muller.lappli.web.rest.ElementSupplyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /element-supplies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ElementSupplyCriteria implements Serializable, Criteria {

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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter apparitions;

    private StringFilter forcedMarking;

    private MarkingTypeFilter markingType;

    private LongFilter elementId;

    private Boolean distinct;

    public ElementSupplyCriteria() {}

    public ElementSupplyCriteria(ElementSupplyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.apparitions = other.apparitions == null ? null : other.apparitions.copy();
        this.forcedMarking = other.forcedMarking == null ? null : other.forcedMarking.copy();
        this.markingType = other.markingType == null ? null : other.markingType.copy();
        this.elementId = other.elementId == null ? null : other.elementId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ElementSupplyCriteria copy() {
        return new ElementSupplyCriteria(this);
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

    public StringFilter getForcedMarking() {
        return forcedMarking;
    }

    public StringFilter forcedMarking() {
        if (forcedMarking == null) {
            forcedMarking = new StringFilter();
        }
        return forcedMarking;
    }

    public void setForcedMarking(StringFilter forcedMarking) {
        this.forcedMarking = forcedMarking;
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

    public LongFilter getElementId() {
        return elementId;
    }

    public LongFilter elementId() {
        if (elementId == null) {
            elementId = new LongFilter();
        }
        return elementId;
    }

    public void setElementId(LongFilter elementId) {
        this.elementId = elementId;
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
        final ElementSupplyCriteria that = (ElementSupplyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(apparitions, that.apparitions) &&
            Objects.equals(forcedMarking, that.forcedMarking) &&
            Objects.equals(markingType, that.markingType) &&
            Objects.equals(elementId, that.elementId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apparitions, forcedMarking, markingType, elementId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ElementSupplyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (apparitions != null ? "apparitions=" + apparitions + ", " : "") +
            (forcedMarking != null ? "forcedMarking=" + forcedMarking + ", " : "") +
            (markingType != null ? "markingType=" + markingType + ", " : "") +
            (elementId != null ? "elementId=" + elementId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
