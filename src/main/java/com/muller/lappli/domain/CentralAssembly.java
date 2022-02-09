package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractAssembly;
import com.muller.lappli.domain.enumeration.OperationKind;
import com.muller.lappli.domain.interfaces.ISupplyPositionOwner;
import java.io.Serializable;
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
public class CentralAssembly extends AbstractAssembly<CentralAssembly> implements ISupplyPositionOwner, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @JsonIgnoreProperties(
        value = { "supplyPositions", "coreAssemblies", "intersticeAssemblies", "sheathings", "centralAssembly", "futureStudy" },
        allowSetters = true
    )
    @OneToOne(optional = false)
    @NotNull
    @MapsId
    @JoinColumn(name = "id")
    private Strand ownerStrand;

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
    @OneToOne
    @JoinColumn(unique = true)
    private SupplyPosition supplyPosition;

    @Override
    public CentralAssembly getThis() {
        return this;
    }

    @Override
    public OperationKind getOperationKind() {
        return OperationKind.CENTRAL_ASSEMBLY;
    }

    @Override
    public Long getOperationLayer() {
        return Long.valueOf(0);
    }

    @Override
    public Double getAfterThisMilimeterDiameter() {
        return getMilimeterDiameterIncidency();
    }

    @Override
    public Long getProductionStep() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getProductDesignation() {
        return "";
    }

    @Override
    public Long getAssemblyLayer() {
        return Long.valueOf(0);
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

    public Strand getOwnerStrand() {
        return this.ownerStrand;
    }

    public void setOwnerStrand(Strand strand) {
        this.ownerStrand = strand;
    }

    public CentralAssembly ownerStrand(Strand strand) {
        this.setOwnerStrand(strand);
        return this;
    }

    public SupplyPosition getSupplyPosition() {
        return this.supplyPosition;
    }

    public void setSupplyPosition(SupplyPosition supplyPosition) {
        this.supplyPosition = supplyPosition;
    }

    public CentralAssembly supplyPosition(SupplyPosition supplyPosition) {
        this.setSupplyPosition(supplyPosition);
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
