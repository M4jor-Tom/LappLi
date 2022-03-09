package com.muller.lappli.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tape.
 */
@Entity
@Table(name = "tape")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tape implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false, unique = true)
    private Long number;

    @NotNull
    @Column(name = "designation", nullable = false, unique = true)
    private String designation;

    @NotNull
    @Column(name = "milimeter_width", nullable = false)
    private Double milimeterWidth;

    @NotNull
    @Column(name = "milimeter_diameter_incidency", nullable = false)
    private Double milimeterDiameterIncidency;

    @ManyToOne(optional = false)
    @NotNull
    private TapeKind tapeKind;

    public Tape() {
        super();
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tape id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return this.number;
    }

    public Tape number(Long number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getDesignation() {
        return this.designation;
    }

    public Tape designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getMilimeterWidth() {
        return this.milimeterWidth;
    }

    public Tape milimeterWidth(Double milimeterWidth) {
        this.setMilimeterWidth(milimeterWidth);
        return this;
    }

    public void setMilimeterWidth(Double milimeterWidth) {
        this.milimeterWidth = milimeterWidth;
    }

    public Double getMilimeterDiameterIncidency() {
        return this.milimeterDiameterIncidency;
    }

    public Tape milimeterDiameterIncidency(Double milimeterDiameterIncidency) {
        this.setMilimeterDiameterIncidency(milimeterDiameterIncidency);
        return this;
    }

    public void setMilimeterDiameterIncidency(Double milimeterDiameterIncidency) {
        this.milimeterDiameterIncidency = milimeterDiameterIncidency;
    }

    public TapeKind getTapeKind() {
        return this.tapeKind;
    }

    public void setTapeKind(TapeKind tapeKind) {
        this.tapeKind = tapeKind;
    }

    public Tape tapeKind(TapeKind tapeKind) {
        this.setTapeKind(tapeKind);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tape)) {
            return false;
        }
        return id != null && id.equals(((Tape) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tape{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", designation='" + getDesignation() + "'" +
            ", milimeterWidth=" + getMilimeterWidth() +
            ", milimeterDiameterIncidency=" + getMilimeterDiameterIncidency() +
            "}";
    }
}
