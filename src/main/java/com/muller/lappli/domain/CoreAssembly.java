package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.enumeration.AssemblyMean;
import java.io.Serializable;
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
public class CoreAssembly implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "production_step", nullable = false)
    private Long productionStep;

    @NotNull
    @Column(name = "assembly_step", nullable = false)
    private Double assemblyStep;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "assembly_mean", nullable = false)
    private AssemblyMean assemblyMean;

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

    public Long getProductionStep() {
        return this.productionStep;
    }

    public CoreAssembly productionStep(Long productionStep) {
        this.setProductionStep(productionStep);
        return this;
    }

    public void setProductionStep(Long productionStep) {
        this.productionStep = productionStep;
    }

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
