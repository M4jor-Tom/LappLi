package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractLiftedSupply;
import com.muller.lappli.domain.interfaces.CylindricComponent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class BangleSupply extends AbstractLiftedSupply implements Serializable {

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

    @ManyToOne(optional = false)
    @NotNull
    //@JsonIgnoreProperties(value = { "material" }, allowSetters = true)
    private Bangle bangle;

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
    @OneToOne(mappedBy = "bangleSupply")
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

    @Override
    public CylindricComponent getCylindricComponent() {
        return getBangle();
    }

    @Override
    public Double getMeterPerHourSpeed() {
        return Double.valueOf(LIFTING_METER_PER_HOUR_SPEED);
    }

    @Override
    @JsonIgnore
    public Double getMilimeterDiameter() {
        try {
            return getBangle().getMilimeterDiameter();
        } catch (NullPointerException e) {
            return Double.NaN;
        }
    }

    @Override
    @JsonIgnore
    public Double getGramPerMeterLinearMass() {
        try {
            return getBangle().getGramPerMeterLinearMass();
        } catch (NullPointerException e) {
            return Double.NaN;
        }
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

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        if (this.position != null) {
            this.position.setBangleSupply(null);
        }
        if (position != null) {
            position.setBangleSupply(this);
        }
        this.position = position;
    }

    public BangleSupply position(Position position) {
        this.setPosition(position);
        return this;
    }

    public Strand getStrand() {
        return this.strand;
    }

    public void setStrand(Strand strand) {
        this.strand = strand;
    }

    public BangleSupply strand(Strand strand) {
        this.setStrand(strand);
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
