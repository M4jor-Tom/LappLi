package com.muller.lappli.domain.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface IDomainObject<T extends IDomainObject<T>> {
    /**
     * @return the object at its concretest state
     */
    @JsonIgnore
    public abstract T getThis();
}
