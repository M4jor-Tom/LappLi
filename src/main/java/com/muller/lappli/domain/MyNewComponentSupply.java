package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractMarkedLiftedSupply;
import com.muller.lappli.domain.enumeration.Color;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.domain.interfaces.CylindricComponent;
import com.muller.lappli.domain.interfaces.PlasticAspectCylindricComponent;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MyNewComponentSupply.
 */
@Entity
@Table(name = "my_new_component_supply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MyNewComponentSupply extends AbstractMarkedLiftedSupply<MyNewComponentSupply> implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marking_type", nullable = false)
    private MarkingType markingType;

    @OneToMany(mappedBy = "myNewComponentSupply", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "ownerCentralAssembly",
            "elementSupply",
            "bangleSupply",
            "customComponentSupply",
            "oneStudySupply",
            "myNewComponentSupply",
            "ownerStrand",
            "ownerIntersticeAssembly",
        },
        allowSetters = true
    )
    private Set<SupplyPosition> ownerSupplyPositions = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private MyNewComponent myNewComponent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public MarkingType getMarkingType() {
        return this.markingType;
    }

    public MyNewComponentSupply markingType(MarkingType markingType) {
        this.setMarkingType(markingType);
        return this;
    }

    public void setMarkingType(MarkingType markingType) {
        this.markingType = markingType;
    }

    @Override
    public Set<SupplyPosition> getOwnerSupplyPositions() {
        return this.ownerSupplyPositions;
    }

    public void setOwnerSupplyPositions(Set<SupplyPosition> supplyPositions) {
        if (this.ownerSupplyPositions != null) {
            this.ownerSupplyPositions.forEach(i -> i.setMyNewComponentSupply(null));
        }
        if (supplyPositions != null) {
            supplyPositions.forEach(i -> i.setMyNewComponentSupply(this));
        }
        this.ownerSupplyPositions = supplyPositions;
    }

    public MyNewComponentSupply ownerSupplyPositions(Set<SupplyPosition> supplyPositions) {
        this.setOwnerSupplyPositions(supplyPositions);
        return this;
    }

    public MyNewComponentSupply addOwnerSupplyPosition(SupplyPosition supplyPosition) {
        this.ownerSupplyPositions.add(supplyPosition);
        supplyPosition.setMyNewComponentSupply(this);
        return this;
    }

    public MyNewComponentSupply removeOwnerSupplyPosition(SupplyPosition supplyPosition) {
        this.ownerSupplyPositions.remove(supplyPosition);
        supplyPosition.setMyNewComponentSupply(null);
        return this;
    }

    public MyNewComponent getMyNewComponent() {
        return this.myNewComponent;
    }

    public void setMyNewComponent(MyNewComponent myNewComponent) {
        this.myNewComponent = myNewComponent;
    }

    public MyNewComponentSupply myNewComponent(MyNewComponent myNewComponent) {
        this.setMyNewComponent(myNewComponent);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyNewComponentSupply)) {
            return false;
        }
        return getId() != null && getId().equals(((MyNewComponentSupply) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MyNewComponentSupply{" +
            "id=" + getId() +
            ", apparitions=" + getApparitions() +
            ", description='" + getDescription() + "'" +
            ", markingType='" + getMarkingType() + "'" +
            "}";
    }

    @Override
    public MyNewComponentSupply getThis() {
        return this;
    }

    @Override
    public Color getSurfaceColor() {
        //Let's suppose there's no color. This can stay null.
        return null;
    }

    @Override
    public CylindricComponent getCylindricComponent() {
        return getMyNewComponent();
    }

    @Override
    public Optional<PlasticAspectCylindricComponent> getCylindricComponentIfPlasticAspect() {
        //WATCH OUT HERE
        //This example works for version 0.2.9, but upon merging
        //feature/StrandSupplyAsSupply, the contract of this methods changes.
        //See commit 7bf8fe454e639e2a6c31b2b21e71a4c0ca131729

        //return Optional.of(getMyNewComponent()); //if MyNewComponent does implement or can be
        //interpreated as a PlasticAspectCylindricComponent
        return null; //otherwise
    }
}
