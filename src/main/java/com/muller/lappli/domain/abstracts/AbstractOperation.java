package com.muller.lappli.domain.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.muller.lappli.domain.CentralAssembly;
import com.muller.lappli.domain.CoreAssembly;
import com.muller.lappli.domain.IntersticeAssembly;
import com.muller.lappli.domain.Sheathing;
import com.muller.lappli.domain.Strand;
import com.muller.lappli.domain.enumeration.OperationKind;
import com.muller.lappli.domain.exception.ImpossibleAssemblyPresetDistributionException;
import javax.persistence.MappedSuperclass;

/**
 * This class represents an Operation which is done in a cable
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "__typeName")
@JsonSubTypes(
    {
        @JsonSubTypes.Type(value = CentralAssembly.class, name = "CentralAssembly"),
        @JsonSubTypes.Type(value = CoreAssembly.class, name = "CoreAssembly"),
        @JsonSubTypes.Type(value = IntersticeAssembly.class, name = "IntersticeAssembly"),
        @JsonSubTypes.Type(value = Sheathing.class, name = "Sheathing"),
    }
)
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
    @JsonIgnoreProperties(
        value = { "operations", "nonAssemblyOperations", "assemblies", "centralAssembly", "coreAssemblies", "IntersticeAssemblies" }
    )
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
     * @return the diameter just under this operation
     * @throws ImpossibleAssemblyPresetDistributionException
     */
    public Double getBeforeThisMilimeterDiameter() throws ImpossibleAssemblyPresetDistributionException {
        try {
            return getOwnerStrand().getMilimeterDiameterBefore(this);
        } catch (NullPointerException e) {
            return Double.NaN;
        }
    }

    /**
     * @return the diameter of {@link #getOwnerStrand()}
     * in milimeters after {@link #getMilimeterDiameterIncidency()}
     * is added
     * @throws ImpossibleAssemblyPresetDistributionException
     */
    public Double getAfterThisMilimeterDiameter() throws ImpossibleAssemblyPresetDistributionException {
        try {
            return getBeforeThisMilimeterDiameter() + getMilimeterDiameterIncidency();
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
