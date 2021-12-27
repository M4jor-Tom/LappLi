package com.muller.lappli.domain.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muller.lappli.domain.Strand;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class AbstractAssembly<T extends AbstractAssembly<T>> {

    @NotNull
    @Column(name = "production_step", nullable = false)
    private Long productionStep;

    @JsonIgnore
    public abstract T getThis();

    public abstract Strand getStrand();

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
