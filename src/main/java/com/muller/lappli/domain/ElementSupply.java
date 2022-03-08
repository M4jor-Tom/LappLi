package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractMarkedLiftedSupply;
import com.muller.lappli.domain.enumeration.Color;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.domain.enumeration.SupplyKind;
import com.muller.lappli.domain.interfaces.CylindricComponent;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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
public class ElementSupply extends AbstractMarkedLiftedSupply<ElementSupply> implements Serializable {

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

    @OneToMany(mappedBy = "elementSupply", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "ownerCentralAssembly",
            "elementSupply",
            "bangleSupply",
            "customComponentSupply",
            "oneStudySupply",
            "ownerStrand",
            "ownerIntersticeAssembly",
        },
        allowSetters = true
    )
    private Set<SupplyPosition> ownerSupplyPositions = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Element element;

    @Override
    public ElementSupply getThis() {
        return this;
    }

    @Override
    public SupplyKind getSupplyKind() {
        return SupplyKind.ELEMENT;
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
        return getSurfaceMaterial().getDesignation();
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

    @Override
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

    @Override
    public Set<SupplyPosition> getOwnerSupplyPositions() {
        return this.ownerSupplyPositions;
    }

    public void setOwnerSupplyPositions(Set<SupplyPosition> supplyPositions) {
        if (this.ownerSupplyPositions != null) {
            this.ownerSupplyPositions.forEach(i -> i.setElementSupply(null));
        }
        if (supplyPositions != null) {
            supplyPositions.forEach(i -> i.setElementSupply(this));
        }
        this.ownerSupplyPositions = supplyPositions;
    }

    public ElementSupply ownerSupplyPositions(Set<SupplyPosition> supplyPositions) {
        this.setOwnerSupplyPositions(supplyPositions);
        return this;
    }

    public ElementSupply addOwnerSupplyPosition(SupplyPosition supplyPosition) {
        this.ownerSupplyPositions.add(supplyPosition);
        supplyPosition.setElementSupply(this);
        return this;
    }

    public ElementSupply removeOwnerSupplyPosition(SupplyPosition supplyPosition) {
        this.ownerSupplyPositions.remove(supplyPosition);
        supplyPosition.setElementSupply(null);
        return this;
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
