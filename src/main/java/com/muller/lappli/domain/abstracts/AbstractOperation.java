package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.Strand;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
/**
 * This class represents an Operation which is done in a cable
 */
public abstract class AbstractOperation<T> extends AbstractDomainObject<T> {

    /**
     * @return the diameter of {@link #getOwnerStrand()} in milimeters after
     */
    public Double getAfterThisMilimeterDiameter() {
        try {
            return getOwnerStrand().getMilimeterDiameterBefore(this) + getMilimeterDiameterIncidency();
        } catch (NullPointerException e) {
            return 0.0;
        }
    }

    public abstract Double getMilimeterDiameterIncidency();

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
