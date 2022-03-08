package com.muller.lappli.domain.interfaces;

public interface INonCentralOperation<T extends INonCentralOperation<T>> extends IOperation<T> {
    public T operationLayer(Long operationLayer);

    public void setOperationLayer(Long operationLayer);

    public IOperation<T> toOperation();
}
