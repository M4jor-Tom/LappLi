package com.muller.lappli.domain.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.Material;
import com.muller.lappli.domain.MaterialMarkingStatistic;
import com.muller.lappli.domain.enumeration.Color;
import com.muller.lappli.domain.enumeration.MarkingTechnique;
import com.muller.lappli.domain.enumeration.MarkingType;
import java.util.Comparator;

public abstract class AbstractMarkedLiftedSupply extends AbstractLiftedSupply {

    public abstract Material getSurfaceMaterial();

    public abstract Color getSurfaceColor();

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

    protected MaterialMarkingStatistic getBestMarkingMaterialStatistic() {
        //Takes the surface material, then
        return getSurfaceMaterial()
            //Takes its marking statistics, but
            .getMaterialMarkingStatistics()
            .stream()
            //Only those which has our element supply's marking type, then
            .filter(statistic -> statistic.getMarkingType().equals(getMarkingType()))
            //INK_JET can't print on black
            .filter(statistic ->
                (MarkingTechnique.INK_JET.equals(statistic.getMarkingTechnique()) != Color.BLACK.equals(getSurfaceColor())) ||
                MarkingTechnique.NONE.equals(statistic.getMarkingTechnique())
            )
            //Takes the fastest to act in a lifter machine, but
            .max(
                new Comparator<MaterialMarkingStatistic>() {
                    @Override
                    public int compare(MaterialMarkingStatistic o1, MaterialMarkingStatistic o2) {
                        return o1.getMeterPerHourSpeed().compareTo(o2.getMeterPerHourSpeed());
                    }
                }
            )
            //If no statistic is found, meaning the lifting operation is unavailable for
            //those parameters, it means that no marking technique is suitable
            .orElse(
                new MaterialMarkingStatistic()
                    .markingType(getMarkingType())
                    .markingTechnique(MarkingTechnique.NONE_SUITABLE)
                    .meterPerHourSpeed(Long.valueOf(0))
            );
    }
}
