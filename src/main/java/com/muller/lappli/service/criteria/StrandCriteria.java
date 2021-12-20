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
 * Criteria class for the {@link com.muller.lappli.domain.Strand} entity. This class is used
 * in {@link com.muller.lappli.web.rest.StrandResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /strands?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StrandCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter designation;

    private LongFilter elementSuppliesId;

    private LongFilter bangleSuppliesId;

    private LongFilter customComponentSuppliesId;

    private LongFilter oneStudySuppliesId;

    private Boolean distinct;

    public StrandCriteria() {}

    public StrandCriteria(StrandCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.elementSuppliesId = other.elementSuppliesId == null ? null : other.elementSuppliesId.copy();
        this.bangleSuppliesId = other.bangleSuppliesId == null ? null : other.bangleSuppliesId.copy();
        this.customComponentSuppliesId = other.customComponentSuppliesId == null ? null : other.customComponentSuppliesId.copy();
        this.oneStudySuppliesId = other.oneStudySuppliesId == null ? null : other.oneStudySuppliesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public StrandCriteria copy() {
        return new StrandCriteria(this);
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

    public LongFilter getElementSuppliesId() {
        return elementSuppliesId;
    }

    public LongFilter elementSuppliesId() {
        if (elementSuppliesId == null) {
            elementSuppliesId = new LongFilter();
        }
        return elementSuppliesId;
    }

    public void setElementSuppliesId(LongFilter elementSuppliesId) {
        this.elementSuppliesId = elementSuppliesId;
    }

    public LongFilter getBangleSuppliesId() {
        return bangleSuppliesId;
    }

    public LongFilter bangleSuppliesId() {
        if (bangleSuppliesId == null) {
            bangleSuppliesId = new LongFilter();
        }
        return bangleSuppliesId;
    }

    public void setBangleSuppliesId(LongFilter bangleSuppliesId) {
        this.bangleSuppliesId = bangleSuppliesId;
    }

    public LongFilter getCustomComponentSuppliesId() {
        return customComponentSuppliesId;
    }

    public LongFilter customComponentSuppliesId() {
        if (customComponentSuppliesId == null) {
            customComponentSuppliesId = new LongFilter();
        }
        return customComponentSuppliesId;
    }

    public void setCustomComponentSuppliesId(LongFilter customComponentSuppliesId) {
        this.customComponentSuppliesId = customComponentSuppliesId;
    }

    public LongFilter getOneStudySuppliesId() {
        return oneStudySuppliesId;
    }

    public LongFilter oneStudySuppliesId() {
        if (oneStudySuppliesId == null) {
            oneStudySuppliesId = new LongFilter();
        }
        return oneStudySuppliesId;
    }

    public void setOneStudySuppliesId(LongFilter oneStudySuppliesId) {
        this.oneStudySuppliesId = oneStudySuppliesId;
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
        final StrandCriteria that = (StrandCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(elementSuppliesId, that.elementSuppliesId) &&
            Objects.equals(bangleSuppliesId, that.bangleSuppliesId) &&
            Objects.equals(customComponentSuppliesId, that.customComponentSuppliesId) &&
            Objects.equals(oneStudySuppliesId, that.oneStudySuppliesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, designation, elementSuppliesId, bangleSuppliesId, customComponentSuppliesId, oneStudySuppliesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StrandCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (designation != null ? "designation=" + designation + ", " : "") +
            (elementSuppliesId != null ? "elementSuppliesId=" + elementSuppliesId + ", " : "") +
            (bangleSuppliesId != null ? "bangleSuppliesId=" + bangleSuppliesId + ", " : "") +
            (customComponentSuppliesId != null ? "customComponentSuppliesId=" + customComponentSuppliesId + ", " : "") +
            (oneStudySuppliesId != null ? "oneStudySuppliesId=" + oneStudySuppliesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
