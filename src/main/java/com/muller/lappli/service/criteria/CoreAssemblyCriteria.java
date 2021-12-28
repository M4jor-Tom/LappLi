package com.muller.lappli.service.criteria;

import com.muller.lappli.domain.enumeration.AssemblyMean;
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
 * Criteria class for the {@link com.muller.lappli.domain.CoreAssembly} entity. This class is used
 * in {@link com.muller.lappli.web.rest.CoreAssemblyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /core-assemblies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CoreAssemblyCriteria implements Serializable, Criteria {

    /**
     * Class for filtering AssemblyMean
     */
    public static class AssemblyMeanFilter extends Filter<AssemblyMean> {

        public AssemblyMeanFilter() {}

        public AssemblyMeanFilter(AssemblyMeanFilter filter) {
            super(filter);
        }

        @Override
        public AssemblyMeanFilter copy() {
            return new AssemblyMeanFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter operationLayer;

    private LongFilter productionStep;

    private DoubleFilter assemblyStep;

    private AssemblyMeanFilter assemblyMean;

    private LongFilter positionsId;

    private LongFilter strandId;

    private Boolean distinct;

    public CoreAssemblyCriteria() {}

    public CoreAssemblyCriteria(CoreAssemblyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.operationLayer = other.operationLayer == null ? null : other.operationLayer.copy();
        this.productionStep = other.productionStep == null ? null : other.productionStep.copy();
        this.assemblyStep = other.assemblyStep == null ? null : other.assemblyStep.copy();
        this.assemblyMean = other.assemblyMean == null ? null : other.assemblyMean.copy();
        this.positionsId = other.positionsId == null ? null : other.positionsId.copy();
        this.strandId = other.strandId == null ? null : other.strandId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CoreAssemblyCriteria copy() {
        return new CoreAssemblyCriteria(this);
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

    public LongFilter getOperationLayer() {
        return operationLayer;
    }

    public LongFilter operationLayer() {
        if (operationLayer == null) {
            operationLayer = new LongFilter();
        }
        return operationLayer;
    }

    public void setOperationLayer(LongFilter operationLayer) {
        this.operationLayer = operationLayer;
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

    public DoubleFilter getAssemblyStep() {
        return assemblyStep;
    }

    public DoubleFilter assemblyStep() {
        if (assemblyStep == null) {
            assemblyStep = new DoubleFilter();
        }
        return assemblyStep;
    }

    public void setAssemblyStep(DoubleFilter assemblyStep) {
        this.assemblyStep = assemblyStep;
    }

    public AssemblyMeanFilter getAssemblyMean() {
        return assemblyMean;
    }

    public AssemblyMeanFilter assemblyMean() {
        if (assemblyMean == null) {
            assemblyMean = new AssemblyMeanFilter();
        }
        return assemblyMean;
    }

    public void setAssemblyMean(AssemblyMeanFilter assemblyMean) {
        this.assemblyMean = assemblyMean;
    }

    public LongFilter getPositionsId() {
        return positionsId;
    }

    public LongFilter positionsId() {
        if (positionsId == null) {
            positionsId = new LongFilter();
        }
        return positionsId;
    }

    public void setPositionsId(LongFilter positionsId) {
        this.positionsId = positionsId;
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
        final CoreAssemblyCriteria that = (CoreAssemblyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(operationLayer, that.operationLayer) &&
            Objects.equals(productionStep, that.productionStep) &&
            Objects.equals(assemblyStep, that.assemblyStep) &&
            Objects.equals(assemblyMean, that.assemblyMean) &&
            Objects.equals(positionsId, that.positionsId) &&
            Objects.equals(strandId, that.strandId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, operationLayer, productionStep, assemblyStep, assemblyMean, positionsId, strandId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CoreAssemblyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (operationLayer != null ? "operationLayer=" + operationLayer + ", " : "") +
            (productionStep != null ? "productionStep=" + productionStep + ", " : "") +
            (assemblyStep != null ? "assemblyStep=" + assemblyStep + ", " : "") +
            (assemblyMean != null ? "assemblyMean=" + assemblyMean + ", " : "") +
            (positionsId != null ? "positionsId=" + positionsId + ", " : "") +
            (strandId != null ? "strandId=" + strandId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
