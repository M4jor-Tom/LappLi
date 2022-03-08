package com.muller.lappli.domain.interfaces;

import com.muller.lappli.domain.abstracts.AbstractOperation;

public interface INonCentralOperation<T extends INonCentralOperation<?>> extends IOperation<INonCentralOperation<T>> {
    public T operationLayer(Long operationLayer);

    public void setOperationLayer(Long operationLayer);

    public AbstractOperation<T> toOperation();
}
