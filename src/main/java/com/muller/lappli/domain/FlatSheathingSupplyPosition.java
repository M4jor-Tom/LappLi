package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class FlatSheathingSupplyPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @NotNull
    @Min(value = 0L)
    @Column(name = "location_in_owner_flat_sheathing", nullable = false)
    private Long locationInOwnerFlatSheathing;

    @JsonIgnoreProperties(
        value = {
            "ownerCentralAssembly",
            "ownerFlatSheathingSupplyPosition",
            "elementSupply",
            "bangleSupply",
            "customComponentSupply",
            "oneStudySupply",
            "ownerStrand",
            "ownerIntersticeAssembly",
        },
        allowSetters = true
    )
    @OneToOne(optional = false)
    @NotNull
    @MapsId
    @JoinColumn(name = "id")
    private SupplyPosition supplyPosition;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "flatSheathingSupplyPositions", "material", "ownerStrandSupply" }, allowSetters = true)
    private FlatSheathing ownerFlatSheathing;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FlatSheathingSupplyPosition id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
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
        return id != null && id.equals(((FlatSheathingSupplyPosition) o).id);
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
