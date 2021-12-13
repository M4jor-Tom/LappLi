package com.muller.lappli.service.criteria;

import com.muller.lappli.domain.enumeration.OperationType;
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

    /**
     * Class for filtering OperationType
     */
    public static class OperationTypeFilter extends Filter<OperationType> {

        public OperationTypeFilter() {}

        public OperationTypeFilter(OperationTypeFilter filter) {
            super(filter);
        }

        @Override
        public OperationTypeFilter copy() {
            return new OperationTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter designation;

    private OperationTypeFilter housingOperationType;

    private LongFilter suppliesId;

    private Boolean distinct;

    public StrandCriteria() {}

    public StrandCriteria(StrandCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.housingOperationType = other.housingOperationType == null ? null : other.housingOperationType.copy();
        this.suppliesId = other.suppliesId == null ? null : other.suppliesId.copy();
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

    public OperationTypeFilter getHousingOperationType() {
        return housingOperationType;
    }

    public OperationTypeFilter housingOperationType() {
        if (housingOperationType == null) {
            housingOperationType = new OperationTypeFilter();
        }
        return housingOperationType;
    }

    public void setHousingOperationType(OperationTypeFilter housingOperationType) {
        this.housingOperationType = housingOperationType;
    }

    public LongFilter getSuppliesId() {
        return suppliesId;
    }

    public LongFilter suppliesId() {
        if (suppliesId == null) {
            suppliesId = new LongFilter();
        }
        return suppliesId;
    }

    public void setSuppliesId(LongFilter suppliesId) {
        this.suppliesId = suppliesId;
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
            Objects.equals(housingOperationType, that.housingOperationType) &&
            Objects.equals(suppliesId, that.suppliesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, designation, housingOperationType, suppliesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StrandCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (designation != null ? "designation=" + designation + ", " : "") +
            (housingOperationType != null ? "housingOperationType=" + housingOperationType + ", " : "") +
            (suppliesId != null ? "suppliesId=" + suppliesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
