package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.interfaces.CylindricComponent;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class AbstractUniformAtom<T extends AbstractUniformAtom<T>> extends AbstractDomainObject<T> implements CylindricComponent {

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "gram_per_meter_linear_mass", nullable = false)
    private Double gramPerMeterLinearMass;

    public AbstractUniformAtom() {
        super();
    }

    public Boolean isConform() {
        return getGramPerMeterLinearMass() != null && getMilimeterDiameter() != null;
    }

    public abstract Double getMilimeterDiameter();

    public Double getGramPerMeterLinearMass() {
        return this.gramPerMeterLinearMass;
    }

    public T gramPerMeterLinearMass(Double gramPerMeterLinearMass) {
        this.setGramPerMeterLinearMass(gramPerMeterLinearMass);
        return getThis();
    }

    public void setGramPerMeterLinearMass(Double gramPerMeterLinearMass) {
        this.gramPerMeterLinearMass = gramPerMeterLinearMass;
    }
}
