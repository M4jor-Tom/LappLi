package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class CentralAssembly implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "production_step", nullable = false)
    private Long productionStep;

    @JsonIgnoreProperties(
        value = {
            "centralAssembly",
            "coreAssemblies",
            "intersticialAssemblies",
            "elementSupplies",
            "bangleSupplies",
            "customComponentSupplies",
            "oneStudySupplies",
        },
        allowSetters = true
    )
    @OneToOne(mappedBy = "centralAssembly")
    private Strand strand;

    // jhipster-needle-entity-add-field - JHipster will add fields here

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

    public Long getProductionStep() {
        return this.productionStep;
    }

    public CentralAssembly productionStep(Long productionStep) {
        this.setProductionStep(productionStep);
        return this;
    }

    public void setProductionStep(Long productionStep) {
        this.productionStep = productionStep;
    }

    public Strand getStrand() {
        return this.strand;
    }

    public void setStrand(Strand strand) {
        if (this.strand != null) {
            this.strand.setCentralAssembly(null);
        }
        if (strand != null) {
            strand.setCentralAssembly(this);
        }
        this.strand = strand;
    }

    public CentralAssembly strand(Strand strand) {
        this.setStrand(strand);
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
