package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.Position;
import com.muller.lappli.domain.Strand;
import com.muller.lappli.domain.exception.PositionHasSeveralSupplyException;
import com.muller.lappli.domain.exception.PositionInSeveralAssemblyException;
import java.util.Set;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractAssembly<T extends AbstractAssembly<T>> extends AbstractOperation<T> {

    public abstract Strand getOwnerStrand();

    public abstract Set<Position> getPositions();

    public abstract Long getOperationLayer();

    public abstract Long getProductionStep();

    public Long getPositionsCountBySupply(AbstractSupply<?> ownedSupply) {
        return Long.valueOf(getPositions().stream().filter(position -> position.getSupply().equals(ownedSupply)).toList().size());
    }

    public void checkPositions() throws PositionInSeveralAssemblyException, PositionHasSeveralSupplyException {
        if (getPositions() != null) {
            for (Position position : getPositions()) {
                position.checkRight();
            }
        }
    }

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
