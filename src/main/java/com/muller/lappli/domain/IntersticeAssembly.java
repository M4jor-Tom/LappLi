package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractNonCentralAssembly;
import com.muller.lappli.domain.enumeration.AssemblyMean;
import com.muller.lappli.domain.enumeration.OperationKind;
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

    @NotNull
    @Column(name = "interstice_layer", nullable = false)
    private Long intersticeLayer;

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
            "futureStudy",
        },
        allowSetters = true
    )
    private Strand ownerStrand;

    @Override
    public IntersticeAssembly getThis() {
        return this;
    }

    @Override
    public OperationKind getOperationKind() {
        return OperationKind.INTERSTICE_ASSEMBLY;
    }

    @Override
    public Double getDiameterAssemblyStep() {
        try {
            return getOwnerStrand().getLastCoreAssembly().getDiameterAssemblyStep();
        } catch (NullPointerException e) {
            return Double.NaN;
        }
    }

    @Override
    public AssemblyMean getAssemblyMean() {
        try {
            return getOwnerStrand().getLastCoreAssembly().getAssemblyMean();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Long getAssemblyLayer() {
        try {
            return getOwnerStrand().getLastCoreAssembly().getAssemblyLayer();
        } catch (NullPointerException e) {
            return DomainManager.ERROR_LONG_POSITIVE_VALUE;
        }
    }

    @Override
    public Double getMilimeterDiameterIncidency() {
        return 0.0;
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

    public IntersticeAssembly id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIntersticeLayer() {
        return this.intersticeLayer;
    }

    public IntersticeAssembly intersticeLayer(Long intersticeLayer) {
        this.setIntersticeLayer(intersticeLayer);
        return this;
    }

    public void setIntersticeLayer(Long intersticeLayer) {
        this.intersticeLayer = intersticeLayer;
    }

    public Strand getOwnerStrand() {
        return this.ownerStrand;
    }

    public void setOwnerStrand(Strand strand) {
        this.ownerStrand = strand;
    }

    public IntersticeAssembly ownerStrand(Strand strand) {
        this.setOwnerStrand(strand);
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
            ", intersticeLayer=" + getIntersticeLayer() +
            "}";
    }
}
