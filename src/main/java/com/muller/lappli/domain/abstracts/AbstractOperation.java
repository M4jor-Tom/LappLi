package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.DomainManager;
import com.muller.lappli.domain.interfaces.IOperation;
import javax.persistence.MappedSuperclass;

/**
 * This class represents an Operation which is done in a cable
 */
@MappedSuperclass
public abstract class AbstractOperation<T extends AbstractOperation<T>> extends AbstractDomainObject<T> implements IOperation<T> {

    public AbstractOperation() {
        super();
    }

    /**
     * @return the standardized {@link String} value of
     * {@link AbstractOperation#getMilimeterDiameterIncidency}
     */
    public String getMullerStandardizedFormatMilimeterDiameterIncidency() {
        return DomainManager.mullerStandardizedFormat(getMilimeterDiameterIncidency());
    }

    /**
     * @return the standardized {@link String} value of
     * {@link AbstractOperation#getHourPreparationTime}
     */
    public String getMullerStandardizedFormatHourPreparationTime() {
        return DomainManager.mullerStandardizedFormat(getHourPreparationTime());
    }

    /**
     * @return the standardized {@link String} value of
     * {@link AbstractOperation#getHourExecutionTime}
     */
    public String getMullerStandardizedFormatHourExecutionTime() {
        return DomainManager.mullerStandardizedFormat(getHourExecutionTime());
    }

    /**
     * @return the diameter just under this operation
     */
    public Double getBeforeThisMilimeterDiameter() {
        if (getOwnerStrandSupply() == null) {
            return Double.NaN;
        }

        return getOwnerStrandSupply().getMilimeterDiameterBefore(this);
    }

    /**
     * @return the standardized {@link String} value of
     * {@link AbstractOperation#getBeforeThisMilimeterDiameter}
     */
    public String getMullerStandardizedFormatBeforeThisMilimeterDiameter() {
        return DomainManager.mullerStandardizedFormat(getBeforeThisMilimeterDiameter());
    }

    @Override
    public Double getAfterThisMilimeterDiameter() {
        return getBeforeThisMilimeterDiameter() + getMilimeterDiameterIncidency();
    }

    /**
     * @return the standardized {@link String} value of
     * {@link AbstractOperation#getAfterThisMilimeterDiameter}
     */
    public String getMullerStandardizedFormatAfterThisMilimeterDiameter() {
        return DomainManager.mullerStandardizedFormat(getAfterThisMilimeterDiameter());
    }

    /**
     * @return the designation of the owner strand
     */
    public String getDesignation() {
        if (getOwnerStrandSupply() == null) {
            return "";
        }

        return getOwnerStrandSupply().getDesignation();
    }
}
