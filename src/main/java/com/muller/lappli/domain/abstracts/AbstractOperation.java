package com.muller.lappli.domain.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.muller.lappli.domain.CentralAssembly;
import com.muller.lappli.domain.CoreAssembly;
import com.muller.lappli.domain.DomainManager;
import com.muller.lappli.domain.IntersticeAssembly;
import com.muller.lappli.domain.Sheathing;
import com.muller.lappli.domain.TapeLaying;
import com.muller.lappli.domain.interfaces.IOperation;
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
        @JsonSubTypes.Type(value = TapeLaying.class, name = "TapeLaying"),
        @JsonSubTypes.Type(value = Sheathing.class, name = "Sheathing"),
    }
)
@MappedSuperclass
public abstract class AbstractOperation<T> extends AbstractDomainObject<T> implements IOperation<AbstractOperation<T>> {

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

    /**
     * @return the diameter of {@link #getOwnerStrand()}
     * in milimeters after {@link #getMilimeterDiameterIncidency()}
     * is added
     * @throws ImpossibleAssemblyPresetDistributionException
     */
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
