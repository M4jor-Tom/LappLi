package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractLiftedSupply;
import com.muller.lappli.domain.enumeration.Color;
import com.muller.lappli.domain.enumeration.MarkingTechnique;
import com.muller.lappli.domain.enumeration.MarkingType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ElementSupply.
 */
@Entity
@Table(name = "element_supply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ElementSupply extends AbstractLiftedSupply implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "apparitions", nullable = false)
    private Long apparitions;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marking_type", nullable = false)
    private MarkingType markingType;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "elementKind" }, allowSetters = true)
    private Element element;

    public ElementSupply() {
        this(new ArrayList<>(), null, MarkingType.LIFTING, "", new Element());
    }

    public ElementSupply(List<Lifter> bestLifterList, Long apparitions, MarkingType markingType, String description, Element element) {
        super(bestLifterList);
        setApparitions(apparitions);
        setMarkingType(markingType);
        setDescription(description);
        setElement(element);
    }

    @Override
    public Double getHourPreparationTime() {
        return Double.NaN;
    }

    @Override
    public Double getHourExecutionTime() {
        return Double.NaN;
    }

    @Override
    public Double getMeterPerHourSpeed() {
        if (getMarkingType().equals(MarkingType.LIFTING)) {
            return Double.valueOf(Math.max(getBestMarkingMaterialStatistic().getMeterPerHourSpeed(), LIFTING_METER_PER_HOUR_SPEED));
        }

        return Double.valueOf(getBestMarkingMaterialStatistic().getMeterPerHourSpeed());
    }

    private MaterialMarkingStatistic getBestMarkingMaterialStatistic() {
        //Takes the element supply's element, then
        return getElement()
            //Takes its element kind, then
            .getElementKind()
            //Takes its insulation material, then
            .getInsulationMaterial()
            //Takes its marking statistics, but
            .getMaterialMarkingStatistics()
            .stream()
            //Only those which has our element supply's marking type, then
            .filter(statistic -> statistic.getMarkingType().equals(getMarkingType()))
            //INK_JET can't print on black
            .filter(statistic ->
                (statistic.getMarkingTechnique().equals(MarkingTechnique.INK_JET) != getElement().getColor().equals(Color.BLACK)) ||
                statistic.getMarkingTechnique().equals(MarkingTechnique.NONE)
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
            .orElse(new MaterialMarkingStatistic(getMarkingType(), MarkingTechnique.NONE_SUITABLE, Long.valueOf(0), new Material()));
    }

    public MarkingTechnique getMarkingTechnique() {
        if (!getMarkingType().equals(MarkingType.NUMBERED)) {
            //A marking technique is necessary when something is written only
            return MarkingTechnique.NONE;
        }

        return getBestMarkingMaterialStatistic().getMarkingTechnique();
    }

    public String getInsulationMaterialDesignation() {
        return getElement().getElementKind().getInsulationMaterial().getDesignation();
    }

    @Override
    public Double getGramPerMeterLinearMass() {
        return getElement().getElementKind().getGramPerMeterLinearMass();
    }

    @Override
    public Double getMilimeterDiameter() {
        return getElement().getElementKind().getMilimeterDiameter();
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ElementSupply id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getApparitions() {
        return this.apparitions;
    }

    public ElementSupply apparitions(Long apparitions) {
        this.setApparitions(apparitions);
        return this;
    }

    public void setApparitions(Long apparitions) {
        this.apparitions = apparitions;
    }

    public MarkingType getMarkingType() {
        return this.markingType;
    }

    public ElementSupply markingType(MarkingType markingType) {
        this.setMarkingType(markingType);
        return this;
    }

    public void setMarkingType(MarkingType markingType) {
        this.markingType = markingType;
    }

    public String getDescription() {
        return this.description;
    }

    public ElementSupply description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Element getElement() {
        return this.element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public ElementSupply element(Element element) {
        this.setElement(element);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ElementSupply)) {
            return false;
        }
        return id != null && id.equals(((ElementSupply) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ElementSupply{" +
            "id=" + getId() +
            ", apparitions=" + getApparitions() +
            ", markingType='" + getMarkingType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
