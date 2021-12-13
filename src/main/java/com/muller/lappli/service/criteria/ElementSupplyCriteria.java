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

    private MarkingTypeFilter markingType;

    private StringFilter description;

    private LongFilter elementId;

    private LongFilter strandId;

    private Boolean distinct;

    public ElementSupplyCriteria() {}

    public ElementSupplyCriteria(ElementSupplyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.apparitions = other.apparitions == null ? null : other.apparitions.copy();
        this.markingType = other.markingType == null ? null : other.markingType.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.elementId = other.elementId == null ? null : other.elementId.copy();
        this.strandId = other.strandId == null ? null : other.strandId.copy();
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
        final ElementSupplyCriteria that = (ElementSupplyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(apparitions, that.apparitions) &&
            Objects.equals(markingType, that.markingType) &&
            Objects.equals(description, that.description) &&
            Objects.equals(elementId, that.elementId) &&
            Objects.equals(strandId, that.strandId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apparitions, markingType, description, elementId, strandId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ElementSupplyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (apparitions != null ? "apparitions=" + apparitions + ", " : "") +
            (markingType != null ? "markingType=" + markingType + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (elementId != null ? "elementId=" + elementId + ", " : "") +
            (strandId != null ? "strandId=" + strandId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
