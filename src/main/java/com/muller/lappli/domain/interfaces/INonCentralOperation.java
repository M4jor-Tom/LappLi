package com.muller.lappli.domain.interfaces;

/**
 * An operation which is not the center of a Strand
 */
public interface INonCentralOperation<T extends INonCentralOperation<T>> extends IOperation<T> {
    public T operationLayer(Long operationLayer);

    public void setOperationLayer(Long operationLayer);

    public IOperation<T> toOperation();

    public default Boolean isOperationLayerDefined() {
        return !IOperation.UNDEFINED_OPERATION_LAYER.equals(getOperationLayer());
    }
}
