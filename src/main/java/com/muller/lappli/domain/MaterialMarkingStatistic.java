package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.enumeration.MarkingTechnique;
import com.muller.lappli.domain.enumeration.MarkingType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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
    @Column(name = "meter_per_second_speed", nullable = false)
    private Long meterPerSecondSpeed;

    @OneToMany(mappedBy = "materialMarkingStatisticList")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "materialMarkingStatisticList" }, allowSetters = true)
    private Set<Material> materials = new HashSet<>();

    public MaterialMarkingStatistic() {
        this(MarkingType.LIFTING, MarkingTechnique.NONE, null, new HashSet<>());
    }

    public MaterialMarkingStatistic(
        MarkingType markingType,
        MarkingTechnique markingTechnique,
        Long meterPerSecondSpeed,
        Set<Material> materials
    ) {
        setMarkingType(markingType);
        setMarkingTechnique(markingTechnique);
        setMeterPerSecondSpeed(meterPerSecondSpeed);
        setMaterials(materials);
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

    public Long getMeterPerSecondSpeed() {
        return this.meterPerSecondSpeed;
    }

    public MaterialMarkingStatistic meterPerSecondSpeed(Long meterPerSecondSpeed) {
        this.setMeterPerSecondSpeed(meterPerSecondSpeed);
        return this;
    }

    public void setMeterPerSecondSpeed(Long meterPerSecondSpeed) {
        this.meterPerSecondSpeed = meterPerSecondSpeed;
    }

    public Set<Material> getMaterials() {
        return this.materials;
    }

    public void setMaterials(Set<Material> materials) {
        if (this.materials != null) {
            this.materials.forEach(i -> i.setMaterialMarkingStatisticList(null));
        }
        if (materials != null) {
            materials.forEach(i -> i.setMaterialMarkingStatisticList(this));
        }
        this.materials = materials;
    }

    public MaterialMarkingStatistic materials(Set<Material> materials) {
        this.setMaterials(materials);
        return this;
    }

    public MaterialMarkingStatistic addMaterial(Material material) {
        this.materials.add(material);
        material.setMaterialMarkingStatisticList(this);
        return this;
    }

    public MaterialMarkingStatistic removeMaterial(Material material) {
        this.materials.remove(material);
        material.setMaterialMarkingStatisticList(null);
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
            ", meterPerSecondSpeed=" + getMeterPerSecondSpeed() +
            "}";
    }
}
