package com.muller.lappli.domain;

import com.muller.lappli.domain.abstracts.AbstractLiftedSupply;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BangleSupply.
 */
@Entity
@Table(name = "bangle_supply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BangleSupply extends AbstractLiftedSupply implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "apparitions", nullable = false)
    private Long apparitions;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false)
    @NotNull
    private Bangle bangle;

    public BangleSupply() {
        this(null, "", new Bangle());
    }

    public BangleSupply(Long apparitions, String description, Bangle bangle) {
        setApparitions(apparitions);
        setDescription(description);
        setBangle(bangle);
    }

    @Override
    public Double getHourPreparationTime() {
        return Double.NaN;
    }

    @Override
    public Double getHourExecutionTime() {
        return Double.NaN;
    }

    @Override
    public Double getMeterPerSecondSpeed() {
        return Double.NaN;
    }

    @Override
    public Double getMilimeterDiameter() {
        return getBangle().getMilimeterDiameter();
    }

    @Override
    public Double getGramPerMeterLinearMass() {
        return getBangle().getGramPerMeterLinearMass();
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BangleSupply id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getApparitions() {
        return this.apparitions;
    }

    public BangleSupply apparitions(Long apparitions) {
        this.setApparitions(apparitions);
        return this;
    }

    public void setApparitions(Long apparitions) {
        this.apparitions = apparitions;
    }

    public String getDescription() {
        return this.description;
    }

    public BangleSupply description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bangle getBangle() {
        return this.bangle;
    }

    public void setBangle(Bangle bangle) {
        this.bangle = bangle;
    }

    public BangleSupply bangle(Bangle bangle) {
        this.setBangle(bangle);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BangleSupply)) {
            return false;
        }
        return id != null && id.equals(((BangleSupply) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BangleSupply{" +
            "id=" + getId() +
            ", apparitions=" + getApparitions() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
