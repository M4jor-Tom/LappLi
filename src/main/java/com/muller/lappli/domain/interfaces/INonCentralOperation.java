package com.muller.lappli.domain.interfaces;

public interface INonCentralOperation<T extends INonCentralOperation<?>> {
    public Long getOperationLayer();

    public T operationLayer(Long operationLayer);

    public void setOperationLayer(Long operationLayer);
}
