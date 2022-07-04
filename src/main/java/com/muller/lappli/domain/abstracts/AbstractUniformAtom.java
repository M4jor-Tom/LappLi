package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.interfaces.CylindricComponent;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * This class represents an atomic component (not a Strand),
 * which is made of the same material in its totality
 */
@MappedSuperclass
public abstract class AbstractUniformAtom<T extends AbstractUniformAtom<T>> extends AbstractDomainObject<T> implements CylindricComponent {

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "gram_per_meter_linear_mass", nullable = false)
    private Double gramPerMeterLinearMass;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "milimeter_diameter", nullable = false)
    private Double milimeterDiameter;

    public AbstractUniformAtom() {
        super();
    }

    public Boolean isConform() {
        return getGramPerMeterLinearMass() != null && getMilimeterDiameter() != null;
    }

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
