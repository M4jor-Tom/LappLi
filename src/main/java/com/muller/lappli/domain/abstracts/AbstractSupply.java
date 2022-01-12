package com.muller.lappli.domain.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.DomainManager;
import com.muller.lappli.domain.Material;
import com.muller.lappli.domain.Position;
import com.muller.lappli.domain.Strand;
import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.domain.enumeration.SupplyKind;
import com.muller.lappli.domain.exception.AppartionDivisionNonNullRemainderException;
import com.muller.lappli.domain.exception.IllegalStrandSupplyException;
import com.muller.lappli.domain.interfaces.CylindricComponent;
import java.text.DecimalFormat;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * An abstract mother class for each Supply class
 *
 * A supply object refers to the instanciation of a CylindricComponent
 * inside a Strand or Cable
 */
@MappedSuperclass
public abstract class AbstractSupply<T> extends AbstractDomainObject<T> {

    @Transient
    private StrandSupply observerStrandSupply;

    /**
     * The unity length which Muller uses to measure cables statistics
     *
     * It may be increased by the supplied CylindricComponent's overlength factor
     */
    public static final Long UNITY_METRIC_QUANTITY = Long.valueOf(1000);

    /**
     * The speed at which a lifter is supposed to run at maximum
     */
    protected static final Long LIFTING_METER_PER_HOUR_SPEED = Long.valueOf(5000);

    /**
     * @return the Strand which owns this
     */
    @JsonIgnoreProperties("supplies")
    public abstract Strand getOwnerStrand();

    /**
     * @return the Position at which this is in the {@link #getOwnerStrand()}'s assemblies
     */
    public abstract Position getPosition();

    /**
     * @return the apparitions of the CylindricComponent inside the final Cable
     */
    public abstract Long getApparitions();

    /**
     * @return the diameter in milimeters of the CylindricComponent
     */
    public abstract Double getMilimeterDiameter();

    /**
     * @return the linear mass in grams per meter of the CylindricComponent
     */
    public abstract Double getGramPerMeterLinearMass();

    /**
     * @return the representated component
     */
    public abstract CylindricComponent getCylindricComponent();

    /**
     * @return the Material at the surface of this
     */
    public abstract Material getSurfaceMaterial();

    /**
     * @return the kind of supply this is
     */
    public abstract SupplyKind getSupplyKind();

    /**
     * To be used on {@link #getOwnerStrand()}'s computation
     *
     * @return this
     * @throws AppartionDivisionNonNullRemainderException if {@link #getApparitionDivisionRemain()} is not null
     */
    public T checkApparitionRemainderIsNull() throws AppartionDivisionNonNullRemainderException {
        if (!isApparitionDivisionRemainNull()) {
            throw new AppartionDivisionNonNullRemainderException();
        }

        return getThis();
    }

    /**
     * Check a StrandSupply is this' observer
     *
     * @param strandSupply to check
     * @return this
     * @throws IllegalStrandSupplyException if the given strandSupply is not this' observer
     */
    public T checkStrandSupplyObserverIs(StrandSupply strandSupply) throws IllegalStrandSupplyException {
        try {
            if (getObserverStrandSupply().equals(strandSupply)) {
                throw new IllegalStrandSupplyException();
            }
        } catch (NullPointerException e) {}

        return getThis();
    }

    /**
     * @return true if {@link #getApparitionDivisionRemain()} is equal to 0, false otherwise
     */
    public Boolean isApparitionDivisionRemainNull() {
        return getApparitionDivisionRemain() == Long.valueOf(0);
    }

    /**
     * @return the remain of {@link #getApparitions()} divided by {@link #getObserverStrandSupply()}'s
     * {@link com.muller.lappli.domain.StrandSupply#getApparitions()}
     */
    public Long getApparitionDivisionRemain() {
        try {
            return getApparitions() % getObserverStrandSupply().getApparitions();
        } catch (NullPointerException e) {
            return DomainManager.ERROR_LONG_POSITIVE_VALUE;
        }
    }

    public StrandSupply getObserverStrandSupply() {
        return observerStrandSupply;
    }

    public void setObserverStrandSupply(StrandSupply observerStrandSupply) {
        this.observerStrandSupply = observerStrandSupply;
    }

    public T observerStrandSupply(StrandSupply strandSupply) {
        setObserverStrandSupply(observerStrandSupply);

        return getThis();
    }

    /**
     * @return the apparitions of this into its observerStrandSupply
     * leaves a remain after the observerStrandSupply's apparitions count divides it
     */
    public Long getDividedApparitions() {
        try {
            return getApparitions() / getObserverStrandSupply().getApparitions();
        } catch (NullPointerException e) {
            return DomainManager.ERROR_LONG_POSITIVE_VALUE;
        }
    }

    /**
     * Tells if the Supply is placed into a Strand's Assembly
     * @return a boolean
     */
    public Boolean isPlaced() {
        return getPosition() != null;
    }

    /**
     * @return the designation of the representated component
     */
    public String getDesignation() {
        try {
            return getApparitions().toString() + " x " + getCylindricComponent().getDesignation();
        } catch (NullPointerException e) {
            return "";
        }
    }

    /**
     * Will determine how hours are display along Supply entities
     *
     * @param value the input Double value
     * @return a value that is formatted for human reading
     */
    private static Double hourFormat(Double value) {
        if (value.isInfinite()) {
            value = Double.NaN;
        }

        String hourStringDecimals = "";

        for (int i = 0; i < DomainManager.HOUR_DECIMAL_COUNT; i++) {
            hourStringDecimals += '#';
        }

        return Double.valueOf(new DecimalFormat("." + hourStringDecimals).format(value));
    }

    /**
     * @return the formated preparation time in hours of the supply operation
     */
    public Double getHourPreparationTime() {
        try {
            return (5.0 * getApparitions() + 5) / 60.0;
        } catch (NullPointerException e) {
            return Double.NaN;
        }
    }

    /**
     * @return the preparation time in hours of the supply operation
     */
    public Double getFormatedHourPreparationTime() {
        return hourFormat(getHourPreparationTime());
    }

    /**
     * @return the execution time in hours of the supply operation
     */
    public Double getHourExecutionTime() {
        return getMeterQuantity() / getMeterPerHourSpeed();
    }

    /**
     * @return the formated execution time in hours of the supply operation
     */
    public Double getFormatedHourExecutionTime() {
        return hourFormat(getHourExecutionTime());
    }

    /**
     * @return the speed in meters per hour of the supply operation
     */
    public abstract Double getMeterPerHourSpeed();

    /**
     * @return the necessary quantity of that CylindricComponent to make the final Cable
     */
    public Long getMeterQuantity() {
        try {
            return AbstractSupply.UNITY_METRIC_QUANTITY * getApparitions();
        } catch (NullPointerException e) {
            return DomainManager.ERROR_LONG_POSITIVE_VALUE;
        }
    }
}
