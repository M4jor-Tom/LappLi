package com.muller.lappli.domain.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.muller.lappli.domain.BangleSupply;
import com.muller.lappli.domain.CustomComponentSupply;
import com.muller.lappli.domain.DomainManager;
import com.muller.lappli.domain.ElementSupply;
import com.muller.lappli.domain.Material;
import com.muller.lappli.domain.OneStudySupply;
import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.domain.SupplyPosition;
import com.muller.lappli.domain.exception.AppartionDivisionNonNullRemainderException;
import com.muller.lappli.domain.exception.IllegalStrandSupplyException;
import com.muller.lappli.domain.interfaces.CylindricComponent;
import com.muller.lappli.domain.interfaces.PlasticAspectCylindricComponent;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * An abstract mother class for each Supply class
 *
 * A supply object refers to the instanciation of a CylindricComponent
 * inside a Strand or Cable
 */
//[COMPONENT_KIND]
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "__typeName")
@JsonSubTypes(
    {
        @JsonSubTypes.Type(value = BangleSupply.class, name = "BangleSupply"),
        @JsonSubTypes.Type(value = CustomComponentSupply.class, name = "CustomComponentSupply"),
        @JsonSubTypes.Type(value = ElementSupply.class, name = "ElementSupply"),
        @JsonSubTypes.Type(value = OneStudySupply.class, name = "OneStudySupply"),
    }
)
@MappedSuperclass
public abstract class AbstractSupply<T extends AbstractSupply<T>> extends AbstractDomainObject<T> {

    @Transient
    private StrandSupply observerStrandSupply;

    @NotNull
    @Column(name = "apparitions", nullable = false)
    private Long apparitions;

    @Column(name = "description")
    private String description;

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

    public AbstractSupply() {
        super();
        this.observerStrandSupply = null;
    }

    /**
     * @return the SupplyPositions which owns this
     */
    @JsonIgnoreProperties("supply")
    public abstract Set<SupplyPosition> getOwnerSupplyPositions();

    /**
     * @return the diameter in milimeters of the CylindricComponent
     */
    public Double getMilimeterDiameter() {
        if (getCylindricComponent() == null) {
            return Double.NaN;
        }

        return getCylindricComponent().getMilimeterDiameter();
    }

    /**
     * @return the linear mass in grams per meter of the CylindricComponent
     */
    public Double getGramPerMeterLinearMass() {
        if (getCylindricComponent() == null) {
            return Double.NaN;
        }

        return getCylindricComponent().getGramPerMeterLinearMass();
    }

    /**
     * @return the representated component
     */
    public abstract CylindricComponent getCylindricComponent();

    /**
     * @return the representated component if plastic aspect
     */
    public abstract Optional<PlasticAspectCylindricComponent> getCylindricComponentIfPlasticAspect();

    /**
     * @return the Material at the surface of this
     */
    public Material getSurfaceMaterial() {
        if (getCylindricComponentIfPlasticAspect() == null) {
            return null;
        } else if (getCylindricComponentIfPlasticAspect().isEmpty()) {
            throw new UnsupportedOperationException(
                getCylindricComponent().getClass().getCanonicalName() + " does not have a surfaceMaterial"
            );
        }

        return getCylindricComponentIfPlasticAspect().get().getSurfaceMaterial();
    }

    /**
     * Computes the divided apparitions which are unused
     * by any SupplyPosition, so still usable
     *
     * @return the remaining divided apparitions
     */
    public Long getUnusedDividedApparitions() {
        Long dividedApparitionsUsage = Long.valueOf(0);

        for (SupplyPosition supplyPosition : getOwnerSupplyPositions()) {
            dividedApparitionsUsage += supplyPosition.getSupplyApparitionsUsage();
        }

        return getDividedApparitions() - dividedApparitionsUsage;
    }

    /**
     * To be used on {@link #getObserverStrandSupply()}'s computation
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
        if (!getObserverStrandSupply().equals(strandSupply)) {
            throw new IllegalStrandSupplyException();
        }

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
        if (getApparitions() == null || getObserverStrandSupply() == null) {
            return DomainManager.ERROR_LONG_POSITIVE_VALUE;
        } else if (getObserverStrandSupply().getApparitions() == null) {
            return DomainManager.ERROR_LONG_POSITIVE_VALUE;
        }

        return getApparitions() % getObserverStrandSupply().getApparitions();
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
        if (getApparitions() == null || getObserverStrandSupply() == null) {
            return DomainManager.ERROR_LONG_POSITIVE_VALUE;
        } else if (getObserverStrandSupply().getApparitions() == null) {
            return DomainManager.ERROR_LONG_POSITIVE_VALUE;
        }

        return getApparitions() / getObserverStrandSupply().getApparitions();
    }

    /**
     * @return the designation of the representated component
     */
    public String getDesignation() {
        if (getApparitions() == null) {
            return "";
        } else if (getCylindricComponent() == null) {
            return "";
        } else if (getApparitions().equals(Long.valueOf(1))) {
            return getCylindricComponent().getDesignation();
        }
        return getApparitions().toString() + " x " + getCylindricComponent().getDesignation();
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
        if (getApparitions() == null) {
            return Double.NaN;
        }

        return (5.0 * getApparitions() + 5) / 60.0;
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
        if (getMeterPerHourSpeed() == null || getMeterQuantity() == null) {
            return Double.NaN;
        }

        return getMeterQuantity().doubleValue() / getMeterPerHourSpeed();
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
        if (getApparitions() == null) {
            return DomainManager.ERROR_LONG_POSITIVE_VALUE;
        }

        return AbstractSupply.UNITY_METRIC_QUANTITY * getApparitions();
    }

    public Long getApparitions() {
        return this.apparitions;
    }

    public T apparitions(Long apparitions) {
        this.setApparitions(apparitions);
        return getThis();
    }

    public void setApparitions(Long apparitions) {
        this.apparitions = apparitions;
    }

    public String getDescription() {
        return this.description;
    }

    public T description(String description) {
        this.setDescription(description);
        return getThis();
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
