package com.muller.lappli.domain.abstracts;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractDomainObject<T> {

    public abstract Long getId();
}
