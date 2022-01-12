package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractOperation;
import com.muller.lappli.domain.enumeration.OperationKind;
import com.muller.lappli.domain.enumeration.SheathingKind;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Sheathing.
 */
@Entity
@Table(name = "sheathing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sheathing extends AbstractOperation<Sheathing> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "thickness", nullable = false)
    private Double thickness;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sheathing_kind", nullable = false)
    private SheathingKind sheathingKind;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "materialMarkingStatistics" }, allowSetters = true)
    private Material material;

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
    public Double getMilimeterDiameterIncidency() {
        if (getThickness() == null) {
            return Double.NaN;
        }

        return getThickness() * 2.0;
    }

    @Override
    public Long getProductionStep() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Sheathing getThis() {
        return this;
    }

    @Override
    public OperationKind getOperationKind() {
        return OperationKind.SHEATHING;
    }

    @Override
    public String getProductDesignation() {
        try {
            return getMaterial().getDesignation();
        } catch (NullPointerException e) {
            return "";
        }
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sheathing id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getThickness() {
        return this.thickness;
    }

    public Sheathing thickness(Double thickness) {
        this.setThickness(thickness);
        return this;
    }

    public void setThickness(Double thickness) {
        this.thickness = thickness;
    }

    public SheathingKind getSheathingKind() {
        return this.sheathingKind;
    }

    public Sheathing sheathingKind(SheathingKind sheathingKind) {
        this.setSheathingKind(sheathingKind);
        return this;
    }

    public void setSheathingKind(SheathingKind sheathingKind) {
        this.sheathingKind = sheathingKind;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Sheathing material(Material material) {
        this.setMaterial(material);
        return this;
    }

    public Strand getOwnerStrand() {
        return this.ownerStrand;
    }

    public void setOwnerStrand(Strand strand) {
        this.ownerStrand = strand;
    }

    public Sheathing ownerStrand(Strand strand) {
        this.setOwnerStrand(strand);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sheathing)) {
            return false;
        }
        return id != null && id.equals(((Sheathing) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sheathing{" +
            "id=" + getId() +
            ", thickness=" + getThickness() +
            ", sheathingKind='" + getSheathingKind() + "'" +
            "}";
    }
}
