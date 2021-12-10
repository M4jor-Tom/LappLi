package com.muller.lappli.domain.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.MaterialMarkingStatistic;
import com.muller.lappli.domain.enumeration.MarkingTechnique;
import com.muller.lappli.domain.enumeration.MarkingType;

public abstract class AbstractMarkedLiftedSupply extends AbstractLiftedSupply {

    protected abstract MaterialMarkingStatistic getBestMarkingMaterialStatistic();

    public abstract MarkingType getMarkingType();

    @Override
    @JsonIgnoreProperties(allowGetters = true)
    public Double getMeterPerHourSpeed() {
        try {
            if (MarkingType.LIFTING.equals(getMarkingType())) {
                return Double.valueOf(Math.max(getBestMarkingMaterialStatistic().getMeterPerHourSpeed(), LIFTING_METER_PER_HOUR_SPEED));
            }

            return Double.valueOf(getBestMarkingMaterialStatistic().getMeterPerHourSpeed());
        } catch (NullPointerException e) {
            return Double.NaN;
        }
    }

    @JsonIgnoreProperties(allowGetters = true)
    public MarkingTechnique getMarkingTechnique() {
        if (!MarkingType.NUMBERED.equals(getMarkingType())) {
            //A marking technique is necessary when something is written only
            return MarkingTechnique.NONE;
        }

        return getBestMarkingMaterialStatistic().getMarkingTechnique();
    }
}
