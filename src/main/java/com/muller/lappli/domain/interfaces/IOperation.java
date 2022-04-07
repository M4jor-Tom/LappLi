package com.muller.lappli.domain.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.muller.lappli.domain.CentralAssembly;
import com.muller.lappli.domain.CoreAssembly;
import com.muller.lappli.domain.IntersticeAssembly;
import com.muller.lappli.domain.Screen;
import com.muller.lappli.domain.Sheathing;
import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.domain.StripLaying;
import com.muller.lappli.domain.TapeLaying;
import com.muller.lappli.domain.enumeration.OperationKind;

//[OPERATION]
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "__typeName")
@JsonSubTypes(
    {
        @JsonSubTypes.Type(value = CentralAssembly.class, name = "CentralAssembly"),
        @JsonSubTypes.Type(value = CoreAssembly.class, name = "CoreAssembly"),
        @JsonSubTypes.Type(value = IntersticeAssembly.class, name = "IntersticeAssembly"),
        @JsonSubTypes.Type(value = TapeLaying.class, name = "TapeLaying"),
        @JsonSubTypes.Type(value = Screen.class, name = "Screen"),
        @JsonSubTypes.Type(value = StripLaying.class, name = "StripLaying"),
        @JsonSubTypes.Type(value = Sheathing.class, name = "Sheathing"),
    }
)
public interface IOperation<T extends IOperation<T>> {
    public static final Long UNDEFINED_OPERATION_LAYER = -2L;

    public T getThis();

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
