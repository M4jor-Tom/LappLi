package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractNonCentralAssembly;
import com.muller.lappli.domain.enumeration.AssemblyMean;
import com.muller.lappli.domain.enumeration.OperationKind;
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
public class CoreAssembly extends AbstractNonCentralAssembly<CoreAssembly> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "assembly_layer", nullable = false)
    private Long assemblyLayer;

    @NotNull
    @Column(name = "diameter_assembly_step", nullable = false)
    private Double diameterAssemblyStep;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "assembly_mean", nullable = false)
    private AssemblyMean assemblyMean;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "coreAssemblies",
            "intersticeAssemblies",
            "sheathings",
            "elementSupplies",
            "bangleSupplies",
            "customComponentSupplies",
            "oneStudySupplies",
            "centralAssembly",
            "lastCoreAssembly",
        },
        allowSetters = true
    )
    private Strand ownerStrand;

    @Override
    public CoreAssembly getThis() {
        return this;
    }

    @Override
    public OperationKind getOperationKind() {
        return OperationKind.CORE_ASSEMBLY;
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

    public CoreAssembly id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getAssemblyLayer() {
        return this.assemblyLayer;
    }

    public CoreAssembly assemblyLayer(Long assemblyLayer) {
        this.setAssemblyLayer(assemblyLayer);
        return this;
    }

    public void setAssemblyLayer(Long assemblyLayer) {
        this.assemblyLayer = assemblyLayer;
    }

    public Double getDiameterAssemblyStep() {
        return this.diameterAssemblyStep;
    }

    public CoreAssembly diameterAssemblyStep(Double diameterAssemblyStep) {
        this.setDiameterAssemblyStep(diameterAssemblyStep);
        return this;
    }

    public void setDiameterAssemblyStep(Double diameterAssemblyStep) {
        this.diameterAssemblyStep = diameterAssemblyStep;
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

    public Strand getOwnerStrand() {
        return this.ownerStrand;
    }

    public void setOwnerStrand(Strand strand) {
        this.ownerStrand = strand;
    }

    public CoreAssembly ownerStrand(Strand strand) {
        this.setOwnerStrand(strand);
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
            ", assemblyLayer=" + getAssemblyLayer() +
            ", productionStep=" + getProductionStep() +
            ", diameterAssemblyStep=" + getDiameterAssemblyStep() +
            ", assemblyMean='" + getAssemblyMean() + "'" +
            "}";
    }
}
