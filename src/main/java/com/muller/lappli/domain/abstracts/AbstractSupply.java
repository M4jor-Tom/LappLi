package com.muller.lappli.domain.abstracts;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractSupply {

    public static final Long UNITY_METRIC_QUANTITY = Long.valueOf(1000);

    public abstract Long getApparitions();

    public abstract Double getMilimeterDiameter();

    public abstract Double getGramPerMeterLinearMass();

    public abstract Double getHourPreparationTime();

    public abstract Double getHourExecutionTime();

    public abstract Double getMeterPerSecondSpeed();

    public Long getMeterQuantity() {
        return AbstractSupply.UNITY_METRIC_QUANTITY * getApparitions();
    }
}
