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
 * Criteria class for the {@link com.muller.lappli.domain.Material} entity. This class is used
 * in {@link com.muller.lappli.web.rest.MaterialResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /materials?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MaterialCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter number;

    private StringFilter designation;

    private LongFilter materialMarkingStatisticId;

    private Boolean distinct;

    public MaterialCriteria() {}

    public MaterialCriteria(MaterialCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.number = other.number == null ? null : other.number.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.materialMarkingStatisticId = other.materialMarkingStatisticId == null ? null : other.materialMarkingStatisticId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MaterialCriteria copy() {
        return new MaterialCriteria(this);
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

    public LongFilter getMaterialMarkingStatisticId() {
        return materialMarkingStatisticId;
    }

    public LongFilter materialMarkingStatisticId() {
        if (materialMarkingStatisticId == null) {
            materialMarkingStatisticId = new LongFilter();
        }
        return materialMarkingStatisticId;
    }

    public void setMaterialMarkingStatisticId(LongFilter materialMarkingStatisticId) {
        this.materialMarkingStatisticId = materialMarkingStatisticId;
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
        final MaterialCriteria that = (MaterialCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(number, that.number) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(materialMarkingStatisticId, that.materialMarkingStatisticId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, designation, materialMarkingStatisticId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaterialCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (number != null ? "number=" + number + ", " : "") +
            (designation != null ? "designation=" + designation + ", " : "") +
            (materialMarkingStatisticId != null ? "materialMarkingStatisticId=" + materialMarkingStatisticId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
