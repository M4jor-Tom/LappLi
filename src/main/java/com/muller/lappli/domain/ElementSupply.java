package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muller.lappli.domain.abstracts.AbstractMarkedLiftedSupply;
import com.muller.lappli.domain.enumeration.Color;
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
public class ElementSupply extends AbstractMarkedLiftedSupply implements ISupply, Serializable {

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
    //@JsonIgnoreProperties(value = { "elementKind" }, allowSetters = true)
    private Element element;

    public ElementSupply() {
        super();
    }

    /*public ElementSupply(List<Lifter> bestLifterList, Long apparitions, MarkingType markingType, String description, Element element) {
        super(bestLifterList);
        setApparitions(apparitions);
        setMarkingType(markingType);
        setDescription(description);
        setElement(element);
    }*/

    @Override
    @JsonIgnore
    public Material getSurfaceMaterial() {
        try {
            return getElement().getElementKind().getInsulationMaterial();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Color getSurfaceColor() {
        try {
            return getElement().getColor();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @JsonIgnore
    public String getInsulationMaterialDesignation() {
        return getElement().getElementKind().getInsulationMaterial().getDesignation();
    }

    @Override
    @JsonIgnore
    public Double getGramPerMeterLinearMass() {
        return getElement().getElementKind().getGramPerMeterLinearMass();
    }

    @Override
    @JsonIgnore
    public Double getMilimeterDiameter() {
        return getElement().getElementKind().getMilimeterDiameter();
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public Long getId() {
        return this.id;
    }

    public ElementSupply id(Long id) {
        this.setId(id);
        return this;
    }

    @Override
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

    @Override
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

    @Override
    public Strand getStrand() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setStrand(Strand strand) {
        // TODO Auto-generated method stub

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
