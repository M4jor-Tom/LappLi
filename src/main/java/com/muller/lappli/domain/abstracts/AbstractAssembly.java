package com.muller.lappli.domain.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    /**
     * @return the designation of the owner strand
     */
    public abstract String getDesignation();

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
