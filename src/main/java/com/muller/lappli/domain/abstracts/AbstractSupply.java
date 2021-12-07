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
    public abstract Double getHourPreparationTime();

    /**
     * @return the execution time in hours of the supply operation
     */
    public abstract Double getHourExecutionTime();

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
