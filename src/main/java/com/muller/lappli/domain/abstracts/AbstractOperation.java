package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.Strand;
import javax.persistence.MappedSuperclass;

/**
 * This class represents an Operation which is done in a cable
 */
@MappedSuperclass
public abstract class AbstractOperation<T> extends AbstractDomainObject<T> {

    /**
     * @return the diameter of {@link #getOwnerStrand()}
     * in milimeters after {@link #getMilimeterDiameterIncidency()}
     * is added
     */
    public Double getAfterThisMilimeterDiameter() {
        try {
            return getOwnerStrand().getMilimeterDiameterBefore(this) + getMilimeterDiameterIncidency();
        } catch (NullPointerException e) {
            return 0.0;
        }
    }

    /**
     * @return twice the "thikness" of the operation in the general case,
     * sometimes a litle bit more, sometimes a little but less
     */
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
