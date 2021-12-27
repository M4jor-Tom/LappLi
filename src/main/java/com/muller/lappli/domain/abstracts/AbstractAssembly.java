package com.muller.lappli.domain.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muller.lappli.domain.Position;
import com.muller.lappli.domain.Strand;
import com.muller.lappli.domain.exception.PositionHasSeveralSupplyException;
import com.muller.lappli.domain.exception.PositionInSeveralAssemblyException;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class AbstractAssembly<T extends AbstractAssembly<T>> extends AbstractDomainObject<T> {

    @NotNull
    @Column(name = "production_step", nullable = false)
    private Long productionStep;

    @JsonIgnore
    public abstract T getThis();

    public abstract Strand getStrand();

    public abstract Long getId();

    public abstract Set<Position> getPositions();

    public void checkPositions() throws PositionInSeveralAssemblyException, PositionHasSeveralSupplyException {
        for (Position position : getPositions()) {
            position.checkRight();
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
            return getStrand().getDesignation();
        } catch (NullPointerException e) {
            return "";
        }
    }

    public Long getProductionStep() {
        return this.productionStep;
    }

    public T productionStep(Long productionStep) {
        this.setProductionStep(productionStep);
        return getThis();
    }

    public void setProductionStep(Long productionStep) {
        this.productionStep = productionStep;
    }
}
