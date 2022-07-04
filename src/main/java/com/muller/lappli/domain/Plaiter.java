package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractMachine;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Plaiter.
 * A machine which is capable of making
 * {@link com.muller.lappli.domain.CarrierPlait} operations.
 *
 * In the future, this class must be used to make
 * {@link com.muller.lappli.domain.Plait} operations as well.
 */
@Entity
@Table(name = "plaiter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Plaiter extends AbstractMachine<Plaiter> implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Min(value = 0L)
    @Column(name = "total_bobins_count", nullable = false)
    private Long totalBobinsCount;

    @OneToMany(mappedBy = "plaiter")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "plaiter" }, allowSetters = true)
    private Set<PlaiterConfiguration> plaiterConfigurations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Plaiter() {
        super();
    }

    public Double getBobinCapacity() {
        return CalculatorManager.getCalculatorInstance().getPlaiterBobinCapacity(this);
    }

    @Override
    public Plaiter getThis() {
        return this;
    }

    @Override
    protected String getPrefix() {
        return "TR";
    }

    public Long getTotalBobinsCount() {
        return this.totalBobinsCount;
    }

    public Plaiter totalBobinsCount(Long totalBobinsCount) {
        this.setTotalBobinsCount(totalBobinsCount);
        return this;
    }

    public void setTotalBobinsCount(Long totalBobinsCount) {
        this.totalBobinsCount = totalBobinsCount;
    }

    public Set<PlaiterConfiguration> getPlaiterConfigurations() {
        return this.plaiterConfigurations;
    }

    public void setPlaiterConfigurations(Set<PlaiterConfiguration> plaiterConfigurations) {
        if (this.plaiterConfigurations != null) {
            this.plaiterConfigurations.forEach(i -> i.setPlaiter(null));
        }
        if (plaiterConfigurations != null) {
            plaiterConfigurations.forEach(i -> i.setPlaiter(this));
        }
        this.plaiterConfigurations = plaiterConfigurations;
    }

    public Plaiter plaiterConfigurations(Set<PlaiterConfiguration> plaiterConfigurations) {
        this.setPlaiterConfigurations(plaiterConfigurations);
        return this;
    }

    public Plaiter addPlaiterConfigurations(PlaiterConfiguration plaiterConfiguration) {
        this.plaiterConfigurations.add(plaiterConfiguration);
        plaiterConfiguration.setPlaiter(this);
        return this;
    }

    public Plaiter removePlaiterConfigurations(PlaiterConfiguration plaiterConfiguration) {
        this.plaiterConfigurations.remove(plaiterConfiguration);
        plaiterConfiguration.setPlaiter(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plaiter)) {
            return false;
        }
        return getId() != null && getId().equals(((Plaiter) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Plaiter{" +
            "id=" + getId() +
            ", index=" + getIndex() +
            ", totalBobinsCount=" + getTotalBobinsCount() +
            "}";
    }
}
