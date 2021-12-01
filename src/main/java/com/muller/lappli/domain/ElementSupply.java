package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractLiftedSupply;
import com.muller.lappli.domain.enumeration.MarkingTechnique;
import com.muller.lappli.domain.enumeration.MarkingType;
import java.io.Serializable;
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

    @Column(name = "forced_marking")
    private String forcedMarking;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marking_type", nullable = false)
    private MarkingType markingType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "elementKind" }, allowSetters = true)
    private Element element;

    public ElementSupply() {
        this(null, "", MarkingType.LIFTING, new Element());
    }

    public ElementSupply(Long apparitions, String forcedMarking, MarkingType markingType, Element element) {
        setApparitions(apparitions);
        setForcedMarking(forcedMarking);
        setMarkingType(markingType);
        setElement(element);
    }

    public MarkingTechnique getMarkingTechnique() {
        if (getForcedMarking().isBlank() && !getMarkingType().equals(MarkingType.NUMBERED)) {
            //A marking technique is necessary when something is written only
            return MarkingTechnique.NONE;
        }

        return MarkingTechnique.INK_JET;
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

    public String getForcedMarking() {
        return this.forcedMarking;
    }

    public ElementSupply forcedMarking(String forcedMarking) {
        this.setForcedMarking(forcedMarking);
        return this;
    }

    public void setForcedMarking(String forcedMarking) {
        this.forcedMarking = forcedMarking;
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
            ", forcedMarking='" + getForcedMarking() + "'" +
            ", markingType='" + getMarkingType() + "'" +
            "}";
    }
}
