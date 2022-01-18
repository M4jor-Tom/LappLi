package com.muller.lappli.domain.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.enumeration.AssemblyMean;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents an assembly, which is a layer of the {@link com.muller.lappli.domain.Strand} in {@link AbstractAssembly#getOwnerStrand()}
 */
public abstract class AbstractNonCentralAssembly<T extends AbstractNonCentralAssembly<T>> extends AbstractAssembly<T> {

    /**
     * @return the assembly step measured in {@link AbstractOperation#getAfterThisMilimeterDiameter}s
     */
    public abstract Double getDiameterAssemblyStep();

    /**
     * @return the assembly's mean
     */
    public abstract AssemblyMean getAssemblyMean();

    @JsonIgnoreProperties(value = { "ownerStrand" })
    public Set<AbstractSupply<?>> getSupplies() {
        Set<AbstractSupply<?>> supplies = new HashSet<>();

        //supplies.addAll(getBangleSupplies());
        //supplies.addAll(getCustomComponentSupplies());
        //supplies.addAll(getElementSupplies());
        //supplies.addAll(getOneStudySupplies());

        return supplies;
    }

    @Override
    public String getProductDesignation() {
        String designation = "";

        for (AbstractSupply<?> supply : getOwnerStrand().getSupplies()) {
            designation += supply.getDesignation();
        }

        return designation;
    }
}
