package com.muller.lappli.domain.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.Position;
import com.muller.lappli.domain.exception.PositionHasSeveralSupplyException;
import com.muller.lappli.domain.exception.PositionInSeveralAssemblyException;
import java.util.Set;
import javax.persistence.MappedSuperclass;

/**
 * This class represents AbstractOperations which are Assemblies
 */
@MappedSuperclass
public abstract class AbstractAssembly<T extends AbstractAssembly<T>> extends AbstractOperation<T> {

    public abstract Long getAssemblyLayer();

    @Override
    public Long getOperationLayer() {
        return Long.valueOf(0);
    }

    /**
     * @return a Set containing all positions in this
     */
    @JsonIgnoreProperties("ownerAssembly")
    public abstract Set<Position> getPositions();

    public Long getPositionsCountBySupply(AbstractSupply<?> ownedSupply) {
        return Long.valueOf(getPositions().stream().filter(position -> position.getSupply().equals(ownedSupply)).toList().size());
    }

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
}
