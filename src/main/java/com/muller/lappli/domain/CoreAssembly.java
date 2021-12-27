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
 * A CoreAssembly.
 */
@Entity
@Table(name = "core_assembly")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CoreAssembly extends AbstractNonCentralAssembly<CoreAssembly> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "assembly_step", nullable = false)
    private Double assemblyStep;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "assembly_mean", nullable = false)
    private AssemblyMean assemblyMean;

    @OneToMany(mappedBy = "ownerCoreAssembly")
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
            "lastCoreAssembly",
        },
        allowSetters = true
    )
    private Strand strand;

    @Override
    public String getDesignation() {
        return getStrand().getDesignation();
    }

    @Override
    public CoreAssembly getThis() {
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CoreAssembly id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Double getAssemblyStep() {
        return this.assemblyStep;
    }

    public CoreAssembly assemblyStep(Double assemblyStep) {
        this.setAssemblyStep(assemblyStep);
        return this;
    }

    public void setAssemblyStep(Double assemblyStep) {
        this.assemblyStep = assemblyStep;
    }

    @Override
    public AssemblyMean getAssemblyMean() {
        return this.assemblyMean;
    }

    public CoreAssembly assemblyMean(AssemblyMean assemblyMean) {
        this.setAssemblyMean(assemblyMean);
        return this;
    }

    public void setAssemblyMean(AssemblyMean assemblyMean) {
        this.assemblyMean = assemblyMean;
    }

    public Set<Position> getPositions() {
        return this.positions;
    }

    public void setPositions(Set<Position> positions) {
        if (this.positions != null) {
            this.positions.forEach(i -> i.setOwnerCoreAssembly(null));
        }
        if (positions != null) {
            positions.forEach(i -> i.setOwnerCoreAssembly(this));
        }
        this.positions = positions;
    }

    public CoreAssembly positions(Set<Position> positions) {
        this.setPositions(positions);
        return this;
    }

    public CoreAssembly addPositions(Position position) {
        this.positions.add(position);
        position.setOwnerCoreAssembly(this);
        return this;
    }

    public CoreAssembly removePositions(Position position) {
        this.positions.remove(position);
        position.setOwnerCoreAssembly(null);
        return this;
    }

    public Strand getStrand() {
        return this.strand;
    }

    public void setStrand(Strand strand) {
        this.strand = strand;
    }

    public CoreAssembly strand(Strand strand) {
        this.setStrand(strand);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoreAssembly)) {
            return false;
        }
        return id != null && id.equals(((CoreAssembly) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CoreAssembly{" +
            "id=" + getId() +
            ", productionStep=" + getProductionStep() +
            ", assemblyStep=" + getAssemblyStep() +
            ", assemblyMean='" + getAssemblyMean() + "'" +
            "}";
    }
}
