package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractMarkedLiftedSupply;
import com.muller.lappli.domain.enumeration.Color;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.domain.enumeration.SupplyKind;
import com.muller.lappli.domain.interfaces.CylindricComponent;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CustomComponentSupply.
 */
@Entity
@Table(name = "custom_component_supply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomComponentSupply extends AbstractMarkedLiftedSupply<CustomComponentSupply> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "apparitions", nullable = false)
    private Long apparitions;

    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marking_type", nullable = false)
    private MarkingType markingType;

    @ManyToOne(optional = false)
    @NotNull
    //@JsonIgnoreProperties(value = { "surfaceMaterial" }, allowSetters = true)
    private CustomComponent customComponent;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "coreAssemblies",
            "intersticeAssemblies",
            "sheathings",
            "elementSupplies",
            "bangleSupplies",
            "customComponentSupplies",
            "oneStudySupplies",
            "centralAssembly",
            "futureStudy",
        },
        allowSetters = true
    )
    private Strand ownerStrand;

    @Override
    public CustomComponentSupply getThis() {
        return this;
    }

    @Override
    public SupplyKind getSupplyKind() {
        return SupplyKind.CUSTOM_COMPONENT;
    }

    @Override
    public CylindricComponent getCylindricComponent() {
        return getCustomComponent();
    }

    @Override
    public Material getSurfaceMaterial() {
        try {
            return getCustomComponent().getSurfaceMaterial();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Color getSurfaceColor() {
        try {
            return getCustomComponent().getSurfaceColor();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    @JsonIgnore
    public Double getMilimeterDiameter() {
        return getCustomComponent().getMilimeterDiameter();
    }

    @Override
    @JsonIgnore
    public Double getGramPerMeterLinearMass() {
        return getCustomComponent().getGramPerMeterLinearMass();
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CustomComponentSupply id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApparitions() {
        return this.apparitions;
    }

    public CustomComponentSupply apparitions(Long apparitions) {
        this.setApparitions(apparitions);
        return this;
    }

    public void setApparitions(Long apparitions) {
        this.apparitions = apparitions;
    }

    public String getDescription() {
        return this.description;
    }

    public CustomComponentSupply description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MarkingType getMarkingType() {
        return this.markingType;
    }

    public CustomComponentSupply markingType(MarkingType markingType) {
        this.setMarkingType(markingType);
        return this;
    }

    public void setMarkingType(MarkingType markingType) {
        this.markingType = markingType;
    }

    public CustomComponent getCustomComponent() {
        return this.customComponent;
    }

    public void setCustomComponent(CustomComponent customComponent) {
        this.customComponent = customComponent;
    }

    public CustomComponentSupply customComponent(CustomComponent customComponent) {
        this.setCustomComponent(customComponent);
        return this;
    }

    public Strand getOwnerStrand() {
        return this.ownerStrand;
    }

    public void setOwnerStrand(Strand strand) {
        this.ownerStrand = strand;
    }

    public CustomComponentSupply ownerStrand(Strand strand) {
        this.setOwnerStrand(strand);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomComponentSupply)) {
            return false;
        }
        return id != null && id.equals(((CustomComponentSupply) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomComponentSupply{" +
            "id=" + getId() +
            ", apparitions=" + getApparitions() +
            ", description='" + getDescription() + "'" +
            ", markingType='" + getMarkingType() + "'" +
            "}";
    }
}
