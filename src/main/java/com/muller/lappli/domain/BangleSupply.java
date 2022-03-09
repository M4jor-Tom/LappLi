package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractLiftedSupply;
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
 * A BangleSupply.
 */
@Entity
@Table(name = "bangle_supply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BangleSupply extends AbstractLiftedSupply<BangleSupply> implements Serializable {

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
        setApparitions(null);
        setDescription(null);
        setOwnerSupplyPositions(null);
    }

    @Override
    public BangleSupply getThis() {
        return this;
    }

    @Override
    public SupplyKind getSupplyKind() {
        return SupplyKind.BANGLE;
    }

    @Override
    public CylindricComponent getCylindricComponent() {
        return getBangle();
    }

    @Override
    public Material getSurfaceMaterial() {
        try {
            return getBangle().getMaterial();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Double getMeterPerHourSpeed() {
        return Double.valueOf(LIFTING_METER_PER_HOUR_SPEED);
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BangleSupply id(Long id) {
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

    public BangleSupply apparitions(Long apparitions) {
        this.setApparitions(apparitions);
        return this;
    }

    public void setApparitions(Long apparitions) {
        this.apparitions = apparitions;
    }

    public String getDescription() {
        return this.description;
    }

    public BangleSupply description(String description) {
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
        return id != null && id.equals(((BangleSupply) o).id);
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
