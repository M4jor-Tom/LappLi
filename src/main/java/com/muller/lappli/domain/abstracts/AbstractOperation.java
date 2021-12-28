package com.muller.lappli.domain.abstracts;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractOperation<T> extends AbstractDomainObject<T> {

    public abstract Double getAfterThisMilimeterDiameter();
}
