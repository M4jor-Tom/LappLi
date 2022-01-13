package com.muller.lappli.domain.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.DomainManager;
import com.muller.lappli.domain.Strand;
import com.muller.lappli.domain.enumeration.OperationKind;
import javax.persistence.MappedSuperclass;

/**
 * This class represents an Operation which is done in a cable
 */
@MappedSuperclass
public abstract class AbstractOperation<T> extends AbstractDomainObject<T> {

    /**
     * @return the operation kind of this
     */
    public abstract OperationKind getOperationKind();

    /**
     * @return twice the "thikness" of the operation in the general case,
     * sometimes a litle bit more, sometimes a little but less
     */
    public abstract Double getMilimeterDiameterIncidency();

    /**
     * @return the strand which owns this assembly
     */
    @JsonIgnoreProperties(value = { "operations", "nonAssemblyOperations", "assemblies" })
    public abstract Strand getOwnerStrand();

    /**
     * @return the step at which this is producted
     */
    public abstract Long getProductionStep();

    /**
     * @return the designation of what is represented in the operation
     */
    public abstract String getProductDesignation();

    /**
     * @return the diameter of {@link #getOwnerStrand()}
     * in milimeters after {@link #getMilimeterDiameterIncidency()}
     * is added
     */
    public Double getAfterThisMilimeterDiameter() {
        try {
            Double milimeterDiameterBeforeThis = getOwnerStrand().getMilimeterDiameterBefore(this);
            if (milimeterDiameterBeforeThis.isNaN()) {
                return Double.NaN;
            }
            return milimeterDiameterBeforeThis + getMilimeterDiameterIncidency();
        } catch (NullPointerException e) {
            return Double.NaN;
        }
    }

    /**
     * @return the layer at which this operation is
     */
    public abstract Long getOperationLayer();

    /**
     * @return the designation of the owner strand
     */
    public String getDesignation() {
        try {
            return getOwnerStrand().getDesignation();
        } catch (NullPointerException e) {
            return "";
        }
    }
}
