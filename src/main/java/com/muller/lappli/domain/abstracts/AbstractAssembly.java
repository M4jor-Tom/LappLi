package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.Position;
import com.muller.lappli.domain.Strand;
import com.muller.lappli.domain.exception.PositionHasSeveralSupplyException;
import com.muller.lappli.domain.exception.PositionInSeveralAssemblyException;
import java.util.Set;
import javax.persistence.MappedSuperclass;

/**
 * This class represents AbstractOperations which are Assemblies
 */
@MappedSuperclass
public abstract class AbstractAssembly<T extends AbstractAssembly<T>> extends AbstractOperation<T> {

    /**
     * @return the strand which owns this assembly
     */
    public abstract Strand getOwnerStrand();

    /**
     * @return a Set containing all positions in this
     */
    public abstract Set<Position> getPositions();

    /**
     * @return the layer at which this operation is
     */
    public abstract Long getOperationLayer();

    /**
     * @return the step at which this is producted
     */
    public abstract Long getProductionStep();

    /**
     * Checks positions are all right with {@link Position#checkRight()}
     *
     * @throws PositionInSeveralAssemblyException if {@link Position#checkRight()} does
     * @throws PositionHasSeveralSupplyException if {@link Position#checkRight()} does
     */
    public void checkPositions() throws PositionInSeveralAssemblyException, PositionHasSeveralSupplyException {
        if (getPositions() != null) {
            for (Position position : getPositions()) {
                position.checkRight();
            }
        }
    }

    /**
     * @return false if {@link #checkPositions()} throws PositionInSeveralAssemblyException or
     * PositionHasSeveralSupplyException, true otherwise
     */
    public Boolean positionsAreRight() {
        try {
            checkPositions();
        } catch (PositionInSeveralAssemblyException | PositionHasSeveralSupplyException e) {
            return false;
        }

        return true;
    }

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
