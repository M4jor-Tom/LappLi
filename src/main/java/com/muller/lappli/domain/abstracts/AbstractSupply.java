package com.muller.lappli.domain.abstracts;

import javax.persistence.MappedSuperclass;

/**
 * An abstract mother class for each Supply class
 *
 * A supply object refers to the instanciation of a CylindricComponent
 * inside a Strand or Cable
 */
@MappedSuperclass
public abstract class AbstractSupply {

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
     * @return the preparation time in hours of the supply operation
     */
    public Double getHourPreparationTime() {
        return (5.0 * getApparitions() + 5) / 60.0;
    }

    /**
     * @return the execution time in hours of the supply operation
     */
    public Double getHourExecutionTime() {
        return getMeterQuantity() / getMeterPerHourSpeed();
    }

    /**
     * @return the speed in meters per hour of the supply operation
     */
    public abstract Double getMeterPerHourSpeed();

    /**
     * @return the necessary quantity of that CylindricComponent to make the final Cable
     */
    public Long getMeterQuantity() {
        return AbstractSupply.UNITY_METRIC_QUANTITY * getApparitions();
    }
}
