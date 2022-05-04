package com.muller.lappli.domain.abstracts;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class AbstractFixedDiameterUniformAtom<T extends AbstractFixedDiameterUniformAtom<T>> extends AbstractUniformAtom<T> {

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "milimeter_diameter", nullable = false)
    private Double milimeterDiameter;

    public AbstractFixedDiameterUniformAtom() {
        super();
    }

    @Override
    public Double getMilimeterDiameter() {
        return this.milimeterDiameter;
    }

    public T milimeterDiameter(Double milimeterDiameter) {
        this.setMilimeterDiameter(milimeterDiameter);
        return getThis();
    }

    public void setMilimeterDiameter(Double milimeterDiameter) {
        this.milimeterDiameter = milimeterDiameter;
    }
}
