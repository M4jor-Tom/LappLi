package com.muller.lappli.domain.abstracts;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractSupply {

    public static final Long UNITY_METRIC_QUANTITY = Long.valueOf(1000);

    public abstract Long getApparitions();

    public abstract Double getMilimeterDiameter();

    public abstract Double getGramPerMeterLinearMass();

    public Double getHourPreparationTime() {
        return Double.NaN;
    }

    public Double getHourExecutionTime() {
        return Double.NaN;
    }

    public Double getMeterPerSecondSpeed() {
        return Double.NaN;
    }

    public Long getMeterQuantity() {
        return AbstractSupply.UNITY_METRIC_QUANTITY * getApparitions();
    }
}
