package com.muller.lappli.domain;

import com.muller.lappli.domain.enumeration.MarkingType;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LifterRunMeasure.
 */
@Entity
@Table(name = "lifter_run_measure")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LifterRunMeasure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "milimeter_diameter")
    private Double milimeterDiameter;

    @Enumerated(EnumType.STRING)
    @Column(name = "marking_type")
    private MarkingType markingType;

    @ManyToOne
    private Lifter lifter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LifterRunMeasure id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMilimeterDiameter() {
        return this.milimeterDiameter;
    }

    public LifterRunMeasure milimeterDiameter(Double milimeterDiameter) {
        this.setMilimeterDiameter(milimeterDiameter);
        return this;
    }

    public void setMilimeterDiameter(Double milimeterDiameter) {
        this.milimeterDiameter = milimeterDiameter;
    }

    public MarkingType getMarkingType() {
        return this.markingType;
    }

    public LifterRunMeasure markingType(MarkingType markingType) {
        this.setMarkingType(markingType);
        return this;
    }

    public void setMarkingType(MarkingType markingType) {
        this.markingType = markingType;
    }

    public Lifter getLifter() {
        return this.lifter;
    }

    public void setLifter(Lifter lifter) {
        this.lifter = lifter;
    }

    public LifterRunMeasure lifter(Lifter lifter) {
        this.setLifter(lifter);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LifterRunMeasure)) {
            return false;
        }
        return id != null && id.equals(((LifterRunMeasure) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LifterRunMeasure{" +
            "id=" + getId() +
            ", milimeterDiameter=" + getMilimeterDiameter() +
            ", markingType='" + getMarkingType() + "'" +
            "}";
    }
}
