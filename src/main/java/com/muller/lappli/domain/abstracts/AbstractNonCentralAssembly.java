package com.muller.lappli.domain.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.Position;
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

    @JsonIgnoreProperties(value = { "position", "ownerStrand" })
    public Set<AbstractSupply<?>> getSupplies() {
        Set<AbstractSupply<?>> supplies = new HashSet<>();

        for (Position position : getPositions()) {
            supplies.add(position.getSupply());
        }

        return supplies;
    }

    @Override
    public String getProductDesignation() {
        String designation = "";

        for (AbstractSupply<?> supply : getSupplies()) {
            designation += supply.getDesignation();
        }

        return designation;
    }
}
