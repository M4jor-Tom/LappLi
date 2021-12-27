package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractNonCentralAssembly;
import com.muller.lappli.domain.enumeration.AssemblyMean;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A IntersticeAssembly.
 */
@Entity
@Table(name = "interstice_assembly")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IntersticeAssembly extends AbstractNonCentralAssembly<IntersticeAssembly> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "ownerIntersticeAssembly")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Position> positions = new HashSet<>();

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
    public IntersticeAssembly getThis() {
        return this;
    }

    @Override
    public Double getAssemblyStep() {
        try {
            return getStrand().getLastCoreAssembly().getAssemblyStep();
        } catch (NullPointerException e) {
            return Double.NaN;
        }
    }

    @Override
    public AssemblyMean getAssemblyMean() {
        try {
            return getStrand().getLastCoreAssembly().getAssemblyMean();
        } catch (NullPointerException e) {
            return null;
        }
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IntersticeAssembly id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Position> getPositions() {
        return this.positions;
    }

    public void setPositions(Set<Position> positions) {
        if (this.positions != null) {
            this.positions.forEach(i -> i.setOwnerIntersticeAssembly(null));
        }
        if (positions != null) {
            positions.forEach(i -> i.setOwnerIntersticeAssembly(this));
        }
        this.positions = positions;
    }

    public IntersticeAssembly positions(Set<Position> positions) {
        this.setPositions(positions);
        return this;
    }

    public IntersticeAssembly addPosition(Position position) {
        this.positions.add(position);
        position.setOwnerIntersticeAssembly(this);
        return this;
    }

    public IntersticeAssembly removePosition(Position position) {
        this.positions.remove(position);
        position.setOwnerIntersticeAssembly(null);
        return this;
    }

    public Strand getStrand() {
        return this.strand;
    }

    public void setStrand(Strand strand) {
        this.strand = strand;
    }

    public IntersticeAssembly strand(Strand strand) {
        this.setStrand(strand);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntersticeAssembly)) {
            return false;
        }
        return id != null && id.equals(((IntersticeAssembly) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntersticeAssembly{" +
            "id=" + getId() +
            ", productionStep=" + getProductionStep() +
            "}";
    }
}
