package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PlaiterConfiguration.
 */
@Entity
@Table(name = "plaiter_configuration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlaiterConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Min(value = 0L)
    @Column(name = "used_bobins_count", nullable = false)
    private Long usedBobinsCount;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "plaiterConfigurations" }, allowSetters = true)
    private Plaiter plaiter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlaiterConfiguration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsedBobinsCount() {
        return this.usedBobinsCount;
    }

    public PlaiterConfiguration usedBobinsCount(Long usedBobinsCount) {
        this.setUsedBobinsCount(usedBobinsCount);
        return this;
    }

    public void setUsedBobinsCount(Long usedBobinsCount) {
        this.usedBobinsCount = usedBobinsCount;
    }

    public Plaiter getPlaiter() {
        return this.plaiter;
    }

    public void setPlaiter(Plaiter plaiter) {
        this.plaiter = plaiter;
    }

    public PlaiterConfiguration plaiter(Plaiter plaiter) {
        this.setPlaiter(plaiter);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlaiterConfiguration)) {
            return false;
        }
        return id != null && id.equals(((PlaiterConfiguration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaiterConfiguration{" +
            "id=" + getId() +
            ", usedBobinsCount=" + getUsedBobinsCount() +
            "}";
    }
}
