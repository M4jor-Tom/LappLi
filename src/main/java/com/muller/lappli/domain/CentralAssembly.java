package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractAssembly;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CentralAssembly.
 */
@Entity
@Table(name = "central_assembly")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CentralAssembly extends AbstractAssembly<CentralAssembly> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @JsonIgnoreProperties(
        value = {
            "coreAssemblies",
            "intersticeAssemblies",
            "elementSupplies",
            "bangleSupplies",
            "customComponentSupplies",
            "oneStudySupplies",
            "centralAssembly",
        },
        allowSetters = true
    )
    @OneToOne(optional = false)
    @NotNull
    @MapsId
    @JoinColumn(name = "id")
    private Strand strand;

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
    @OneToOne
    @JoinColumn(unique = true)
    private Position position;

    @Override
    public CentralAssembly getThis() {
        return this;
    }

    @Override
    public Set<Position> getPositions() {
        HashSet<Position> positions = new HashSet<Position>();
        if (getPosition() != null) {
            positions.add(getPosition());
        }
        return positions;
    }

    @Override
    public Long getOperationLayer() {
        return Long.valueOf(0);
    }

    @Override
    public Double getAfterThisMilimeterDiameter() {
        try {
            return getPosition().getSupply().getMilimeterDiameter();
        } catch (NullPointerException e) {
            return Double.NaN;
        }
    }

    @Override
    public Long getProductionStep() {
        // TODO Auto-generated method stub
        return null;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public Long getId() {
        return this.id;
    }

    public CentralAssembly id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Strand getStrand() {
        return this.strand;
    }

    public void setStrand(Strand strand) {
        this.strand = strand;
    }

    public CentralAssembly strand(Strand strand) {
        this.setStrand(strand);
        return this;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public CentralAssembly position(Position position) {
        this.setPosition(position);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CentralAssembly)) {
            return false;
        }
        return id != null && id.equals(((CentralAssembly) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CentralAssembly{" +
            "id=" + getId() +
            ", productionStep=" + getProductionStep() +
            "}";
    }
}
