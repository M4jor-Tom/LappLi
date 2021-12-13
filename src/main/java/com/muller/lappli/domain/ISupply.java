package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ISupply.
 */
@Entity
@Table(name = "i_supply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ISupply implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "apparitions", nullable = false)
    private Long apparitions;

    @NotNull
    @Column(name = "milimeter_diameter", nullable = false)
    private Double milimeterDiameter;

    @NotNull
    @Column(name = "gram_per_meter_linear_mass", nullable = false)
    private Double gramPerMeterLinearMass;

    @ManyToOne
    @JsonIgnoreProperties(value = { "supplies" }, allowSetters = true)
    private Strand strand;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ISupply id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApparitions() {
        return this.apparitions;
    }

    public ISupply apparitions(Long apparitions) {
        this.setApparitions(apparitions);
        return this;
    }

    public void setApparitions(Long apparitions) {
        this.apparitions = apparitions;
    }

    public Double getMilimeterDiameter() {
        return this.milimeterDiameter;
    }

    public ISupply milimeterDiameter(Double milimeterDiameter) {
        this.setMilimeterDiameter(milimeterDiameter);
        return this;
    }

    public void setMilimeterDiameter(Double milimeterDiameter) {
        this.milimeterDiameter = milimeterDiameter;
    }

    public Double getGramPerMeterLinearMass() {
        return this.gramPerMeterLinearMass;
    }

    public ISupply gramPerMeterLinearMass(Double gramPerMeterLinearMass) {
        this.setGramPerMeterLinearMass(gramPerMeterLinearMass);
        return this;
    }

    public void setGramPerMeterLinearMass(Double gramPerMeterLinearMass) {
        this.gramPerMeterLinearMass = gramPerMeterLinearMass;
    }

    public Strand getStrand() {
        return this.strand;
    }

    public void setStrand(Strand strand) {
        this.strand = strand;
    }

    public ISupply strand(Strand strand) {
        this.setStrand(strand);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ISupply)) {
            return false;
        }
        return id != null && id.equals(((ISupply) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ISupply{" +
            "id=" + getId() +
            ", apparitions=" + getApparitions() +
            ", milimeterDiameter=" + getMilimeterDiameter() +
            ", gramPerMeterLinearMass=" + getGramPerMeterLinearMass() +
            "}";
    }
}
