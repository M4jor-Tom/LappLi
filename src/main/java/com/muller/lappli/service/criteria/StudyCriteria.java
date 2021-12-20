package com.muller.lappli.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.muller.lappli.domain.Study} entity. This class is used
 * in {@link com.muller.lappli.web.rest.StudyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /studies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StudyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter number;

    private InstantFilter creationInstant;

    private LongFilter strandSuppliesId;

    private LongFilter authorId;

    private Boolean distinct;

    public StudyCriteria() {}

    public StudyCriteria(StudyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.number = other.number == null ? null : other.number.copy();
        this.creationInstant = other.creationInstant == null ? null : other.creationInstant.copy();
        this.strandSuppliesId = other.strandSuppliesId == null ? null : other.strandSuppliesId.copy();
        this.authorId = other.authorId == null ? null : other.authorId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public StudyCriteria copy() {
        return new StudyCriteria(this);
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

    public InstantFilter getCreationInstant() {
        return creationInstant;
    }

    public InstantFilter creationInstant() {
        if (creationInstant == null) {
            creationInstant = new InstantFilter();
        }
        return creationInstant;
    }

    public void setCreationInstant(InstantFilter creationInstant) {
        this.creationInstant = creationInstant;
    }

    public LongFilter getStrandSuppliesId() {
        return strandSuppliesId;
    }

    public LongFilter strandSuppliesId() {
        if (strandSuppliesId == null) {
            strandSuppliesId = new LongFilter();
        }
        return strandSuppliesId;
    }

    public void setStrandSuppliesId(LongFilter strandSuppliesId) {
        this.strandSuppliesId = strandSuppliesId;
    }

    public LongFilter getAuthorId() {
        return authorId;
    }

    public LongFilter authorId() {
        if (authorId == null) {
            authorId = new LongFilter();
        }
        return authorId;
    }

    public void setAuthorId(LongFilter authorId) {
        this.authorId = authorId;
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
        final StudyCriteria that = (StudyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(number, that.number) &&
            Objects.equals(creationInstant, that.creationInstant) &&
            Objects.equals(strandSuppliesId, that.strandSuppliesId) &&
            Objects.equals(authorId, that.authorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, creationInstant, strandSuppliesId, authorId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (number != null ? "number=" + number + ", " : "") +
            (creationInstant != null ? "creationInstant=" + creationInstant + ", " : "") +
            (strandSuppliesId != null ? "strandSuppliesId=" + strandSuppliesId + ", " : "") +
            (authorId != null ? "authorId=" + authorId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
