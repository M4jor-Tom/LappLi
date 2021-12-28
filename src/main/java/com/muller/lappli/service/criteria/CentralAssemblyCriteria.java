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
 * Criteria class for the {@link com.muller.lappli.domain.CentralAssembly} entity. This class is used
 * in {@link com.muller.lappli.web.rest.CentralAssemblyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /central-assemblies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CentralAssemblyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter productionStep;

    private LongFilter strandId;

    private LongFilter positionId;

    private Boolean distinct;

    public CentralAssemblyCriteria() {}

    public CentralAssemblyCriteria(CentralAssemblyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.productionStep = other.productionStep == null ? null : other.productionStep.copy();
        this.strandId = other.strandId == null ? null : other.strandId.copy();
        this.positionId = other.positionId == null ? null : other.positionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CentralAssemblyCriteria copy() {
        return new CentralAssemblyCriteria(this);
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

    public LongFilter getProductionStep() {
        return productionStep;
    }

    public LongFilter productionStep() {
        if (productionStep == null) {
            productionStep = new LongFilter();
        }
        return productionStep;
    }

    public void setProductionStep(LongFilter productionStep) {
        this.productionStep = productionStep;
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
        final CentralAssemblyCriteria that = (CentralAssemblyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(productionStep, that.productionStep) &&
            Objects.equals(strandId, that.strandId) &&
            Objects.equals(positionId, that.positionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productionStep, strandId, positionId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CentralAssemblyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (productionStep != null ? "productionStep=" + productionStep + ", " : "") +
            (strandId != null ? "strandId=" + strandId + ", " : "") +
            (positionId != null ? "positionId=" + positionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
