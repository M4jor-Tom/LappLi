package com.muller.lappli.domain.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.domain.enumeration.OperationKind;

public interface IOperation<T extends IOperation<T>> {
    /**
     * @return the layer at which the operation is
     */
    public Long getOperationLayer();

    /**
     * @return the operation kind of this
     */
    public OperationKind getOperationKind();

    /**
     * @return twice the "thikness" of the operation in the general case,
     * sometimes a litle bit more, sometimes a little but less
     */
    public Double getMilimeterDiameterIncidency();

    /**
     * @return the StrandSupply which owns this assembly
     */
    @JsonIgnoreProperties(value = { "operations", "nonAssemblyOperations" })
    public StrandSupply getOwnerStrandSupply();

    /**
     * @return the step at which this is producted
     */
    public Long getProductionStep();

    /**
     * @return the designation of what is represented in the operation
     */
    public String getProductDesignation();

    /**
     * @return the diameter of {@link #getOwnerStrand()}
     * in milimeters after {@link #getMilimeterDiameterIncidency()}
     * is added
     */
    public Double getAfterThisMilimeterDiameter();
}
