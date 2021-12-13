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
 * Criteria class for the {@link com.muller.lappli.domain.ISupply} entity. This class is used
 * in {@link com.muller.lappli.web.rest.ISupplyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /i-supplies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ISupplyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter apparitions;

    private DoubleFilter milimeterDiameter;

    private DoubleFilter gramPerMeterLinearMass;

    private LongFilter strandId;

    private Boolean distinct;

    public ISupplyCriteria() {}

    public ISupplyCriteria(ISupplyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.apparitions = other.apparitions == null ? null : other.apparitions.copy();
        this.milimeterDiameter = other.milimeterDiameter == null ? null : other.milimeterDiameter.copy();
        this.gramPerMeterLinearMass = other.gramPerMeterLinearMass == null ? null : other.gramPerMeterLinearMass.copy();
        this.strandId = other.strandId == null ? null : other.strandId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ISupplyCriteria copy() {
        return new ISupplyCriteria(this);
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
        final ISupplyCriteria that = (ISupplyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(apparitions, that.apparitions) &&
            Objects.equals(milimeterDiameter, that.milimeterDiameter) &&
            Objects.equals(gramPerMeterLinearMass, that.gramPerMeterLinearMass) &&
            Objects.equals(strandId, that.strandId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apparitions, milimeterDiameter, gramPerMeterLinearMass, strandId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ISupplyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (apparitions != null ? "apparitions=" + apparitions + ", " : "") +
            (milimeterDiameter != null ? "milimeterDiameter=" + milimeterDiameter + ", " : "") +
            (gramPerMeterLinearMass != null ? "gramPerMeterLinearMass=" + gramPerMeterLinearMass + ", " : "") +
            (strandId != null ? "strandId=" + strandId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
