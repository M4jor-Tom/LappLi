package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractLiftedSupply;
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
 * A Supply for {@link com.muller.lappli.domain.Bangle}
 */
@Entity
@Table(name = "bangle_supply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BangleSupply extends AbstractLiftedSupply<BangleSupply> implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "bangleSupply", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
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
    private Bangle bangle;

    public BangleSupply() {
        super();
    }

    @Override
    public BangleSupply getThis() {
        return this;
    }

    @Override
    public CylindricComponent getCylindricComponent() {
        return getBangle();
    }

    @Override
    public Optional<PlasticAspectCylindricComponent> getCylindricComponentIfPlasticAspect() {
        if (getBangle() == null) {
            return null;
        }

        return Optional.of(getBangle());
    }

    @Override
    public Double getMeterPerHourSpeed() {
        return Double.valueOf(LIFTING_METER_PER_HOUR_SPEED);
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public Set<SupplyPosition> getOwnerSupplyPositions() {
        return this.ownerSupplyPositions;
    }

    public void setOwnerSupplyPositions(Set<SupplyPosition> supplyPositions) {
        if (this.ownerSupplyPositions != null) {
            this.ownerSupplyPositions.forEach(i -> i.setBangleSupply(null));
        }
        if (supplyPositions != null) {
            supplyPositions.forEach(i -> i.setBangleSupply(this));
        }
        this.ownerSupplyPositions = supplyPositions;
    }

    public BangleSupply ownerSupplyPositions(Set<SupplyPosition> supplyPositions) {
        this.setOwnerSupplyPositions(supplyPositions);
        return this;
    }

    public BangleSupply addOwnerSupplyPosition(SupplyPosition supplyPosition) {
        this.ownerSupplyPositions.add(supplyPosition);
        supplyPosition.setBangleSupply(this);
        return this;
    }

    public BangleSupply removeOwnerSupplyPosition(SupplyPosition supplyPosition) {
        this.ownerSupplyPositions.remove(supplyPosition);
        supplyPosition.setBangleSupply(null);
        return this;
    }

    public Bangle getBangle() {
        return this.bangle;
    }

    public void setBangle(Bangle bangle) {
        this.bangle = bangle;
    }

    public BangleSupply bangle(Bangle bangle) {
        this.setBangle(bangle);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BangleSupply)) {
            return false;
        }
        return getId() != null && getId().equals(((BangleSupply) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BangleSupply{" +
            "id=" + getId() +
            ", apparitions=" + getApparitions() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
