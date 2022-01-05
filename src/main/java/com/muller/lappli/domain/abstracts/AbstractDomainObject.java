package com.muller.lappli.domain.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractDomainObject<T> {

    @JsonIgnore
    public abstract T getThis();

    public abstract Long getId();
}
