package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.enumeration.MarkingTechnique;
import com.muller.lappli.domain.enumeration.MarkingType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MaterialMarkingStatistic.
 */
@Entity
@Table(name = "material_marking_statistic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MaterialMarkingStatistic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marking_type", nullable = false)
    private MarkingType markingType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marking_technique", nullable = false)
    private MarkingTechnique markingTechnique;

    @NotNull
    @Column(name = "meter_per_hour_speed", nullable = false)
    private Long meterPerHourSpeed;

    @ManyToOne
    @JsonIgnoreProperties(value = { "materialMarkingStatistics" }, allowSetters = true)
    private Material material;

    public MaterialMarkingStatistic() {
        this(MarkingType.LIFTING, MarkingTechnique.NONE, null, new Material());
    }

    public MaterialMarkingStatistic(MarkingType markingType, MarkingTechnique markingTechnique, Long meterPerHourSpeed, Material material) {
        setMarkingType(markingType);
        setMarkingTechnique(markingTechnique);
        setMeterPerHourSpeed(meterPerHourSpeed);
        setMaterial(material);
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MaterialMarkingStatistic id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MarkingType getMarkingType() {
        return this.markingType;
    }

    public MaterialMarkingStatistic markingType(MarkingType markingType) {
        this.setMarkingType(markingType);
        return this;
    }

    public void setMarkingType(MarkingType markingType) {
        this.markingType = markingType;
    }

    public MarkingTechnique getMarkingTechnique() {
        if (!getMarkingType().equals(MarkingType.NUMBERED)) {
            return MarkingTechnique.NONE;
        }

        return this.markingTechnique;
    }

    public MaterialMarkingStatistic markingTechnique(MarkingTechnique markingTechnique) {
        this.setMarkingTechnique(markingTechnique);
        return this;
    }

    public void setMarkingTechnique(MarkingTechnique markingTechnique) {
        if (getMarkingType().equals(MarkingType.NUMBERED)) {
            this.markingTechnique = markingTechnique;

            if (markingTechnique.equals(MarkingTechnique.NONE)) {
                (new Exception("NoneMarkingTechniqueForMarkingTypeNumbered")).printStackTrace();
            }
        } else {
            this.markingTechnique = MarkingTechnique.NONE;
        }
    }

    public Long getMeterPerHourSpeed() {
        return this.meterPerHourSpeed;
    }

    public MaterialMarkingStatistic meterPerHourSpeed(Long meterPerHourSpeed) {
        this.setMeterPerHourSpeed(meterPerHourSpeed);
        return this;
    }

    public void setMeterPerHourSpeed(Long meterPerHourSpeed) {
        this.meterPerHourSpeed = meterPerHourSpeed;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public MaterialMarkingStatistic material(Material material) {
        this.setMaterial(material);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaterialMarkingStatistic)) {
            return false;
        }
        return id != null && id.equals(((MaterialMarkingStatistic) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaterialMarkingStatistic{" +
            "id=" + getId() +
            ", markingType='" + getMarkingType() + "'" +
            ", markingTechnique='" + getMarkingTechnique() + "'" +
            ", meterPerHourSpeed=" + getMeterPerHourSpeed() +
            "}";
    }
}
