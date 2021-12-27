package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractMarkedLiftedSupply;
import com.muller.lappli.domain.enumeration.Color;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.domain.interfaces.CylindricComponent;
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
public class ElementSupply extends AbstractMarkedLiftedSupply implements Serializable {

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

    @JsonIgnoreProperties(
        value = {
            "elementSupply",
            "bangleSupply",
            "customComponentSupply",
            "oneStudySupply",
            "ownerCentralAssembly",
            "ownerCoreAssembly",
            "ownerIntersticeAssembly",
        },
        allowSetters = true
    )
    @OneToOne(mappedBy = "elementSupply")
    private Position position;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "coreAssemblies",
            "intersticialAssemblies",
            "elementSupplies",
            "bangleSupplies",
            "customComponentSupplies",
            "oneStudySupplies",
            "centralAssembly",
        },
        allowSetters = true
    )
    private Strand strand;

    public ElementSupply() {
        super();
    }

    @Override
    public CylindricComponent getCylindricComponent() {
        return getElement();
    }

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

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        if (this.position != null) {
            this.position.setElementSupply(null);
        }
        if (position != null) {
            position.setElementSupply(this);
        }
        this.position = position;
    }

    public ElementSupply position(Position position) {
        this.setPosition(position);
        return this;
    }

    public Strand getStrand() {
        return this.strand;
    }

    public void setStrand(Strand strand) {
        this.strand = strand;
    }

    public ElementSupply strand(Strand strand) {
        this.setStrand(strand);
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
