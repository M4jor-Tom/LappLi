package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.Strand;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractOperation<T> extends AbstractDomainObject<T> {

    public abstract Double getAfterThisMilimeterDiameter();

    /**
     * @return the strand which owns this assembly
     */
    public abstract Strand getOwnerStrand();

    /**
     * @return the layer at which this operation is
     */
    public abstract Long getOperationLayer();

    /**
     * @return the step at which this is producted
     */
    public abstract Long getProductionStep();
}
