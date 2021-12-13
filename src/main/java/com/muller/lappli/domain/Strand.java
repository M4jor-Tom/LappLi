package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.enumeration.OperationType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Strand.
 */
@Entity
@Table(name = "strand")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Strand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "designation", nullable = false)
    private String designation;

    @Enumerated(EnumType.STRING)
    @Column(name = "housing_operation_type")
    private OperationType housingOperationType;

    @OneToMany(mappedBy = "strand")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "strand" }, allowSetters = true)
    private Set<ISupply> supplies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Strand id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return this.designation;
    }

    public Strand designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public OperationType getHousingOperationType() {
        return this.housingOperationType;
    }

    public Strand housingOperationType(OperationType housingOperationType) {
        this.setHousingOperationType(housingOperationType);
        return this;
    }

    public void setHousingOperationType(OperationType housingOperationType) {
        this.housingOperationType = housingOperationType;
    }

    public Set<ISupply> getSupplies() {
        return this.supplies;
    }

    public void setSupplies(Set<ISupply> iSupplies) {
        if (this.supplies != null) {
            this.supplies.forEach(i -> i.setStrand(null));
        }
        if (iSupplies != null) {
            iSupplies.forEach(i -> i.setStrand(this));
        }
        this.supplies = iSupplies;
    }

    public Strand supplies(Set<ISupply> iSupplies) {
        this.setSupplies(iSupplies);
        return this;
    }

    public Strand addSupplies(ISupply iSupply) {
        this.supplies.add(iSupply);
        iSupply.setStrand(this);
        return this;
    }

    public Strand removeSupplies(ISupply iSupply) {
        this.supplies.remove(iSupply);
        iSupply.setStrand(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Strand)) {
            return false;
        }
        return id != null && id.equals(((Strand) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Strand{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", housingOperationType='" + getHousingOperationType() + "'" +
            "}";
    }
}
