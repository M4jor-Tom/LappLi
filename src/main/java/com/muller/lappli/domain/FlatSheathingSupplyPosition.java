package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractDomainObject;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FlatSheathingSupplyPosition.
 */
@Entity
@Table(name = "flat_sheathing_supply_position")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FlatSheathingSupplyPosition extends AbstractDomainObject<FlatSheathingSupplyPosition> implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Min(value = 0L)
    @Column(name = "location_in_owner_flat_sheathing", nullable = false)
    private Long locationInOwnerFlatSheathing;

    @JsonIgnoreProperties(
        value = {
            "ownerFlatSheathingSupplyPosition",
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
    @OneToOne(mappedBy = "ownerFlatSheathingSupplyPosition")
    private SupplyPosition supplyPosition;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "flatSheathingSupplyPositions", "material", "ownerStrandSupply" }, allowSetters = true)
    private FlatSheathing ownerFlatSheathing;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public FlatSheathingSupplyPosition() {
        super();
    }

    @Override
    public FlatSheathingSupplyPosition getThis() {
        return this;
    }

    public Long getLocationInOwnerFlatSheathing() {
        return this.locationInOwnerFlatSheathing;
    }

    public FlatSheathingSupplyPosition locationInOwnerFlatSheathing(Long locationInOwnerFlatSheathing) {
        this.setLocationInOwnerFlatSheathing(locationInOwnerFlatSheathing);
        return this;
    }

    public void setLocationInOwnerFlatSheathing(Long locationInOwnerFlatSheathing) {
        this.locationInOwnerFlatSheathing = locationInOwnerFlatSheathing;
    }

    public SupplyPosition getSupplyPosition() {
        return this.supplyPosition;
    }

    public void setSupplyPosition(SupplyPosition supplyPosition) {
        if (this.supplyPosition != null) {
            this.supplyPosition.setOwnerFlatSheathingSupplyPosition(null);
        }
        if (supplyPosition != null) {
            supplyPosition.setOwnerFlatSheathingSupplyPosition(this);
        }
        this.supplyPosition = supplyPosition;
    }

    public FlatSheathingSupplyPosition supplyPosition(SupplyPosition supplyPosition) {
        this.setSupplyPosition(supplyPosition);
        return this;
    }

    public FlatSheathing getOwnerFlatSheathing() {
        return this.ownerFlatSheathing;
    }

    public void setOwnerFlatSheathing(FlatSheathing flatSheathing) {
        this.ownerFlatSheathing = flatSheathing;
    }

    public FlatSheathingSupplyPosition ownerFlatSheathing(FlatSheathing flatSheathing) {
        this.setOwnerFlatSheathing(flatSheathing);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FlatSheathingSupplyPosition)) {
            return false;
        }
        return getId() != null && getId().equals(((FlatSheathingSupplyPosition) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FlatSheathingSupplyPosition{" +
            "id=" + getId() +
            ", locationInOwnerFlatSheathing=" + getLocationInOwnerFlatSheathing() +
            "}";
    }
}
