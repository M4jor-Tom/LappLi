package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.interfaces.INonCentralOperation;
import com.muller.lappli.domain.interfaces.IOperation;
import com.muller.lappli.domain.interfaces.MeanedAssemblableOperation;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * This class represents an assembly, which is a layer of the {@link com.muller.lappli.domain.Strand} in {@link AbstractAssembly#getOwnerStrandSupply()}
 */
@MappedSuperclass
public abstract class AbstractNonCentralAssembly<T extends AbstractNonCentralAssembly<T>>
    extends AbstractAssembly<T>
    implements INonCentralOperation<T>, MeanedAssemblableOperation<T> {

    @NotNull
    @Column(name = "operation_layer", nullable = false)
    private Long operationLayer;

    @Column(name = "forced_mean_milimeter_component_diameter")
    private Double forcedMeanMilimeterComponentDiameter;

    public AbstractNonCentralAssembly() {
        super();
    }

    @Override
    public String getProductDesignation() {
        return "";
    }

    public IOperation<T> toOperation() {
        return this;
    }

    @Override
    public Long getOperationLayer() {
        return this.operationLayer;
    }

    public T operationLayer(Long operationLayer) {
        this.setOperationLayer(operationLayer);
        return getThis();
    }

    public void setOperationLayer(Long operationLayer) {
        this.operationLayer = operationLayer;
    }

    public Double getForcedMeanMilimeterComponentDiameter() {
        return this.forcedMeanMilimeterComponentDiameter;
    }

    public T forcedMeanMilimeterComponentDiameter(Double forcedMeanMilimeterComponentDiameter) {
        this.setForcedMeanMilimeterComponentDiameter(forcedMeanMilimeterComponentDiameter);
        return getThis();
    }

    public void setForcedMeanMilimeterComponentDiameter(Double forcedMeanMilimeterComponentDiameter) {
        this.forcedMeanMilimeterComponentDiameter = forcedMeanMilimeterComponentDiameter;
    }
}
