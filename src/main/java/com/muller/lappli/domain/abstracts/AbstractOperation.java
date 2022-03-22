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
     * @return the diameter just under this operation
     * @throws ImpossibleAssemblyPresetDistributionException
     */
    public Double getBeforeThisMilimeterDiameter() {
        try {
            return getOwnerStrandSupply().getMilimeterDiameterBefore(this);
        } catch (NullPointerException e) {
            return Double.NaN;
        }
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
        try {
            return getBeforeThisMilimeterDiameter() + getMilimeterDiameterIncidency();
        } catch (NullPointerException e) {
            return Double.NaN;
        }
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
        try {
            return getOwnerStrandSupply().getDesignation();
        } catch (NullPointerException e) {
            return "";
        }
    }
}
