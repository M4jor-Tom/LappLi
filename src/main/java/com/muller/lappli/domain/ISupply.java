package com.muller.lappli.domain;

/**
 * A ISupply.
 */
public interface ISupply {
    public Long getId();

    public void setId(Long id);

    public Long getApparitions();

    public void setApparitions(Long apparitions);

    public Double getMilimeterDiameter();

    public Double getGramPerMeterLinearMass();

    public Strand getStrand();

    public void setStrand(Strand strand);
}
