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
 * Criteria class for the {@link com.muller.lappli.domain.Position} entity. This class is used
 * in {@link com.muller.lappli.web.rest.PositionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /positions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PositionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter value;

    private LongFilter elementSupplyId;

    private LongFilter bangleSupplyId;

    private LongFilter customComponentSupplyId;

    private LongFilter oneStudySupplyId;

    private LongFilter ownerCentralAssemblyId;

    private LongFilter ownerCoreAssemblyId;

    private LongFilter ownerIntersticeAssemblyId;

    private Boolean distinct;

    public PositionCriteria() {}

    public PositionCriteria(PositionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.elementSupplyId = other.elementSupplyId == null ? null : other.elementSupplyId.copy();
        this.bangleSupplyId = other.bangleSupplyId == null ? null : other.bangleSupplyId.copy();
        this.customComponentSupplyId = other.customComponentSupplyId == null ? null : other.customComponentSupplyId.copy();
        this.oneStudySupplyId = other.oneStudySupplyId == null ? null : other.oneStudySupplyId.copy();
        this.ownerCentralAssemblyId = other.ownerCentralAssemblyId == null ? null : other.ownerCentralAssemblyId.copy();
        this.ownerCoreAssemblyId = other.ownerCoreAssemblyId == null ? null : other.ownerCoreAssemblyId.copy();
        this.ownerIntersticeAssemblyId = other.ownerIntersticeAssemblyId == null ? null : other.ownerIntersticeAssemblyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PositionCriteria copy() {
        return new PositionCriteria(this);
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

    public LongFilter getValue() {
        return value;
    }

    public LongFilter value() {
        if (value == null) {
            value = new LongFilter();
        }
        return value;
    }

    public void setValue(LongFilter value) {
        this.value = value;
    }

    public LongFilter getElementSupplyId() {
        return elementSupplyId;
    }

    public LongFilter elementSupplyId() {
        if (elementSupplyId == null) {
            elementSupplyId = new LongFilter();
        }
        return elementSupplyId;
    }

    public void setElementSupplyId(LongFilter elementSupplyId) {
        this.elementSupplyId = elementSupplyId;
    }

    public LongFilter getBangleSupplyId() {
        return bangleSupplyId;
    }

    public LongFilter bangleSupplyId() {
        if (bangleSupplyId == null) {
            bangleSupplyId = new LongFilter();
        }
        return bangleSupplyId;
    }

    public void setBangleSupplyId(LongFilter bangleSupplyId) {
        this.bangleSupplyId = bangleSupplyId;
    }

    public LongFilter getCustomComponentSupplyId() {
        return customComponentSupplyId;
    }

    public LongFilter customComponentSupplyId() {
        if (customComponentSupplyId == null) {
            customComponentSupplyId = new LongFilter();
        }
        return customComponentSupplyId;
    }

    public void setCustomComponentSupplyId(LongFilter customComponentSupplyId) {
        this.customComponentSupplyId = customComponentSupplyId;
    }

    public LongFilter getOneStudySupplyId() {
        return oneStudySupplyId;
    }

    public LongFilter oneStudySupplyId() {
        if (oneStudySupplyId == null) {
            oneStudySupplyId = new LongFilter();
        }
        return oneStudySupplyId;
    }

    public void setOneStudySupplyId(LongFilter oneStudySupplyId) {
        this.oneStudySupplyId = oneStudySupplyId;
    }

    public LongFilter getOwnerCentralAssemblyId() {
        return ownerCentralAssemblyId;
    }

    public LongFilter ownerCentralAssemblyId() {
        if (ownerCentralAssemblyId == null) {
            ownerCentralAssemblyId = new LongFilter();
        }
        return ownerCentralAssemblyId;
    }

    public void setOwnerCentralAssemblyId(LongFilter ownerCentralAssemblyId) {
        this.ownerCentralAssemblyId = ownerCentralAssemblyId;
    }

    public LongFilter getOwnerCoreAssemblyId() {
        return ownerCoreAssemblyId;
    }

    public LongFilter ownerCoreAssemblyId() {
        if (ownerCoreAssemblyId == null) {
            ownerCoreAssemblyId = new LongFilter();
        }
        return ownerCoreAssemblyId;
    }

    public void setOwnerCoreAssemblyId(LongFilter ownerCoreAssemblyId) {
        this.ownerCoreAssemblyId = ownerCoreAssemblyId;
    }

    public LongFilter getOwnerIntersticeAssemblyId() {
        return ownerIntersticeAssemblyId;
    }

    public LongFilter ownerIntersticeAssemblyId() {
        if (ownerIntersticeAssemblyId == null) {
            ownerIntersticeAssemblyId = new LongFilter();
        }
        return ownerIntersticeAssemblyId;
    }

    public void setOwnerIntersticeAssemblyId(LongFilter ownerIntersticeAssemblyId) {
        this.ownerIntersticeAssemblyId = ownerIntersticeAssemblyId;
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
        final PositionCriteria that = (PositionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(value, that.value) &&
            Objects.equals(elementSupplyId, that.elementSupplyId) &&
            Objects.equals(bangleSupplyId, that.bangleSupplyId) &&
            Objects.equals(customComponentSupplyId, that.customComponentSupplyId) &&
            Objects.equals(oneStudySupplyId, that.oneStudySupplyId) &&
            Objects.equals(ownerCentralAssemblyId, that.ownerCentralAssemblyId) &&
            Objects.equals(ownerCoreAssemblyId, that.ownerCoreAssemblyId) &&
            Objects.equals(ownerIntersticeAssemblyId, that.ownerIntersticeAssemblyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            value,
            elementSupplyId,
            bangleSupplyId,
            customComponentSupplyId,
            oneStudySupplyId,
            ownerCentralAssemblyId,
            ownerCoreAssemblyId,
            ownerIntersticeAssemblyId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PositionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (value != null ? "value=" + value + ", " : "") +
            (elementSupplyId != null ? "elementSupplyId=" + elementSupplyId + ", " : "") +
            (bangleSupplyId != null ? "bangleSupplyId=" + bangleSupplyId + ", " : "") +
            (customComponentSupplyId != null ? "customComponentSupplyId=" + customComponentSupplyId + ", " : "") +
            (oneStudySupplyId != null ? "oneStudySupplyId=" + oneStudySupplyId + ", " : "") +
            (ownerCentralAssemblyId != null ? "ownerCentralAssemblyId=" + ownerCentralAssemblyId + ", " : "") +
            (ownerCoreAssemblyId != null ? "ownerCoreAssemblyId=" + ownerCoreAssemblyId + ", " : "") +
            (ownerIntersticeAssemblyId != null ? "ownerIntersticeAssemblyId=" + ownerIntersticeAssemblyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
