package com.muller.lappli.domain.interfaces;

public interface IOperation<T extends IOperation<T>> {
    public Long getOperationLayer();
}
