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
 * Criteria class for the {@link com.muller.lappli.domain.StrandSupply} entity. This class is used
 * in {@link com.muller.lappli.web.rest.StrandSupplyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /strand-supplies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StrandSupplyCriteria implements Serializable, Criteria {

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

    private LongFilter strandId;

    private LongFilter studyId;

    private Boolean distinct;

    public StrandSupplyCriteria() {}

    public StrandSupplyCriteria(StrandSupplyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.apparitions = other.apparitions == null ? null : other.apparitions.copy();
        this.markingType = other.markingType == null ? null : other.markingType.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.strandId = other.strandId == null ? null : other.strandId.copy();
        this.studyId = other.studyId == null ? null : other.studyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public StrandSupplyCriteria copy() {
        return new StrandSupplyCriteria(this);
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

    public LongFilter getStudyId() {
        return studyId;
    }

    public LongFilter studyId() {
        if (studyId == null) {
            studyId = new LongFilter();
        }
        return studyId;
    }

    public void setStudyId(LongFilter studyId) {
        this.studyId = studyId;
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
        final StrandSupplyCriteria that = (StrandSupplyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(apparitions, that.apparitions) &&
            Objects.equals(markingType, that.markingType) &&
            Objects.equals(description, that.description) &&
            Objects.equals(strandId, that.strandId) &&
            Objects.equals(studyId, that.studyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apparitions, markingType, description, strandId, studyId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StrandSupplyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (apparitions != null ? "apparitions=" + apparitions + ", " : "") +
            (markingType != null ? "markingType=" + markingType + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (strandId != null ? "strandId=" + strandId + ", " : "") +
            (studyId != null ? "studyId=" + studyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
