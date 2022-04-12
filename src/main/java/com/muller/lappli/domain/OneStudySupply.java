package com.muller.lappli.domain;

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
 * A OneStudySupply.
 */
@Entity
@Table(name = "one_study_supply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OneStudySupply extends AbstractMarkedLiftedSupply<OneStudySupply> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    private OneStudyComponent oneStudyComponent;

    @Column(name = "apparitions")
    private Long apparitions;

    @Column(name = "number")
    private Long number;

    @Column(name = "component_designation")
    private String componentDesignation;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marking_type", nullable = false)
    private MarkingType markingType;

    @NotNull
    @Column(name = "gram_per_meter_linear_mass", nullable = false)
    private Double gramPerMeterLinearMass;

    @NotNull
    @Column(name = "milimeter_diameter", nullable = false)
    private Double milimeterDiameter;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "surface_color", nullable = false)
    private Color surfaceColor;

    @OneToMany(mappedBy = "oneStudySupply", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
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
    @JsonIgnoreProperties(value = { "materialMarkingStatistics" }, allowSetters = true)
    private Material surfaceMaterial;

    public OneStudySupply() {
        super();
        this.oneStudyComponent = null;
        setOwnerSupplyPositions(new HashSet<>());
    }

    @Override
    public OneStudySupply getThis() {
        return this;
    }

    @Override
    public SupplyKind getSupplyKind() {
        return SupplyKind.ONE_STUDY;
    }

    @Override
    public CylindricComponent getCylindricComponent() {
        if (oneStudyComponent == null) {
            oneStudyComponent =
                new OneStudyComponent()
                    .designation(getComponentDesignation())
                    .milimeterDiameter(getMilimeterDiameter())
                    .gramPerMeterLinearMass(getGramPerMeterLinearMass());
        }

        return oneStudyComponent;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public Long getApparitions() {
        return this.apparitions;
    }

    @Override
    public void setApparitions(Long apparitions) {
        this.apparitions = apparitions;
    }

    public Long getNumber() {
        return this.number;
    }

    public OneStudySupply number(Long number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getComponentDesignation() {
        return this.componentDesignation;
    }

    public OneStudySupply componentDesignation(String componentDesignation) {
        this.setComponentDesignation(componentDesignation);
        return this;
    }

    public void setComponentDesignation(String componentDesignation) {
        this.componentDesignation = componentDesignation;
    }

    @Override
    public MarkingType getMarkingType() {
        return this.markingType;
    }

    public OneStudySupply markingType(MarkingType markingType) {
        this.setMarkingType(markingType);
        return this;
    }

    public void setMarkingType(MarkingType markingType) {
        this.markingType = markingType;
    }

    @Override
    public Double getGramPerMeterLinearMass() {
        return this.gramPerMeterLinearMass;
    }

    public OneStudySupply gramPerMeterLinearMass(Double gramPerMeterLinearMass) {
        this.setGramPerMeterLinearMass(gramPerMeterLinearMass);
        return this;
    }

    public void setGramPerMeterLinearMass(Double gramPerMeterLinearMass) {
        this.gramPerMeterLinearMass = gramPerMeterLinearMass;
    }

    @Override
    public Double getMilimeterDiameter() {
        return this.milimeterDiameter;
    }

    public OneStudySupply milimeterDiameter(Double milimeterDiameter) {
        this.setMilimeterDiameter(milimeterDiameter);
        return this;
    }

    public void setMilimeterDiameter(Double milimeterDiameter) {
        this.milimeterDiameter = milimeterDiameter;
    }

    @Override
    public Color getSurfaceColor() {
        return this.surfaceColor;
    }

    public OneStudySupply surfaceColor(Color surfaceColor) {
        this.setSurfaceColor(surfaceColor);
        return this;
    }

    public void setSurfaceColor(Color surfaceColor) {
        this.surfaceColor = surfaceColor;
    }

    @Override
    public Set<SupplyPosition> getOwnerSupplyPositions() {
        return this.ownerSupplyPositions;
    }

    public void setOwnerSupplyPositions(Set<SupplyPosition> supplyPositions) {
        if (this.ownerSupplyPositions != null) {
            this.ownerSupplyPositions.forEach(i -> i.setOneStudySupply(null));
        }
        if (supplyPositions != null) {
            supplyPositions.forEach(i -> i.setOneStudySupply(this));
        }
        this.ownerSupplyPositions = supplyPositions;
    }

    public OneStudySupply ownerSupplyPositions(Set<SupplyPosition> supplyPositions) {
        this.setOwnerSupplyPositions(supplyPositions);
        return this;
    }

    public OneStudySupply addOwnerSupplyPosition(SupplyPosition supplyPosition) {
        this.ownerSupplyPositions.add(supplyPosition);
        supplyPosition.setOneStudySupply(this);
        return this;
    }

    public OneStudySupply removeOwnerSupplyPosition(SupplyPosition supplyPosition) {
        this.ownerSupplyPositions.remove(supplyPosition);
        supplyPosition.setOneStudySupply(null);
        return this;
    }

    public Material getSurfaceMaterial() {
        return this.surfaceMaterial;
    }

    public void setSurfaceMaterial(Material material) {
        this.surfaceMaterial = material;
    }

    public OneStudySupply surfaceMaterial(Material material) {
        this.setSurfaceMaterial(material);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OneStudySupply)) {
            return false;
        }
        return getId() != null && getId().equals(((OneStudySupply) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OneStudySupply{" +
            "id=" + getId() +
            ", apparitions=" + getApparitions() +
            ", number=" + getNumber() +
            ", componentDesignation='" + getComponentDesignation() + "'" +
            ", description='" + getDescription() + "'" +
            ", markingType='" + getMarkingType() + "'" +
            ", gramPerMeterLinearMass=" + getGramPerMeterLinearMass() +
            ", milimeterDiameter=" + getMilimeterDiameter() +
            ", surfaceColor='" + getSurfaceColor() + "'" +
            "}";
    }

    private class OneStudyComponent implements CylindricComponent {

        private String designation;

        private Double milimeterDiameter;

        private Double gramPerMeterLinearMass;

        @Override
        public Boolean isUtility() {
            return true;
        }

        @Override
        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public OneStudyComponent designation(String designation) {
            setDesignation(designation);
            return this;
        }

        @Override
        public Double getMilimeterDiameter() {
            return milimeterDiameter;
        }

        public void setMilimeterDiameter(Double milimeterDiameter) {
            this.milimeterDiameter = milimeterDiameter;
        }

        public OneStudyComponent milimeterDiameter(Double milimeterDiameter) {
            setMilimeterDiameter(milimeterDiameter);
            return this;
        }

        @Override
        public Double getGramPerMeterLinearMass() {
            return gramPerMeterLinearMass;
        }

        public void setGramPerMeterLinearMass(Double gramPerMeterLinearMass) {
            this.gramPerMeterLinearMass = gramPerMeterLinearMass;
        }

        public OneStudyComponent gramPerMeterLinearMass(Double gramPerMeterLinearMass) {
            setGramPerMeterLinearMass(gramPerMeterLinearMass);
            return this;
        }
    }
}
