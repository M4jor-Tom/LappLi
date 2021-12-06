package com.muller.lappli.service.criteria;

import com.muller.lappli.domain.enumeration.Color;
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
 * Criteria class for the {@link com.muller.lappli.domain.Element} entity. This class is used
 * in {@link com.muller.lappli.web.rest.ElementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /elements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ElementCriteria implements Serializable, Criteria {

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

    private LongFilter number;

    private ColorFilter color;

    private LongFilter elementKindId;

    private Boolean distinct;

    public ElementCriteria() {}

    public ElementCriteria(ElementCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.number = other.number == null ? null : other.number.copy();
        this.color = other.color == null ? null : other.color.copy();
        this.elementKindId = other.elementKindId == null ? null : other.elementKindId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ElementCriteria copy() {
        return new ElementCriteria(this);
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

    public ColorFilter getColor() {
        return color;
    }

    public ColorFilter color() {
        if (color == null) {
            color = new ColorFilter();
        }
        return color;
    }

    public void setColor(ColorFilter color) {
        this.color = color;
    }

    public LongFilter getElementKindId() {
        return elementKindId;
    }

    public LongFilter elementKindId() {
        if (elementKindId == null) {
            elementKindId = new LongFilter();
        }
        return elementKindId;
    }

    public void setElementKindId(LongFilter elementKindId) {
        this.elementKindId = elementKindId;
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
        final ElementCriteria that = (ElementCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(number, that.number) &&
            Objects.equals(color, that.color) &&
            Objects.equals(elementKindId, that.elementKindId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, color, elementKindId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ElementCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (number != null ? "number=" + number + ", " : "") +
            (color != null ? "color=" + color + ", " : "") +
            (elementKindId != null ? "elementKindId=" + elementKindId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
