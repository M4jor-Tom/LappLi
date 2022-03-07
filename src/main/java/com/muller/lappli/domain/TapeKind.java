package com.muller.lappli.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TapeKind.
 */
@Entity
@Table(name = "tape_kind")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TapeKind implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "target_covering_rate", nullable = false)
    private Double targetCoveringRate;

    @NotNull
    @Column(name = "designation", nullable = false, unique = true)
    private String designation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TapeKind id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTargetCoveringRate() {
        return this.targetCoveringRate;
    }

    public TapeKind targetCoveringRate(Double targetCoveringRate) {
        this.setTargetCoveringRate(targetCoveringRate);
        return this;
    }

    public void setTargetCoveringRate(Double targetCoveringRate) {
        this.targetCoveringRate = targetCoveringRate;
    }

    public String getDesignation() {
        return this.designation;
    }

    public TapeKind designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TapeKind)) {
            return false;
        }
        return id != null && id.equals(((TapeKind) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TapeKind{" +
            "id=" + getId() +
            ", targetCoveringRate=" + getTargetCoveringRate() +
            ", designation='" + getDesignation() + "'" +
            "}";
    }
}
