package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractDomainObject;
import com.muller.lappli.domain.interfaces.Article;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Material.
 */
@Entity
@Table(name = "material")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Material extends AbstractDomainObject<Material> implements Article, Serializable, Cloneable {

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
    @Column(name = "kilogram_per_cube_meter_volumic_density", nullable = false)
    private Double kilogramPerCubeMeterVolumicDensity;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "material")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "material" }, allowSetters = true)
    private Set<MaterialMarkingStatistic> materialMarkingStatistics = new HashSet<>();

    public Material() {
        super();
        setMaterialMarkingStatistics(new HashSet<>());
    }

    public Material(
        Long number,
        String designation,
        Double kilogramPerCubeMeterVolumicDensity,
        Set<MaterialMarkingStatistic> materialMarkingStatistics
    ) {
        setNumber(number);
        setDesignation(designation);
        setMaterialMarkingStatistics(materialMarkingStatistics);
        setKilogramPerCubeMeterVolumicDensity(kilogramPerCubeMeterVolumicDensity);
    }

    protected Material(Material material) {
        this(
            Long.valueOf(material.getNumber()),
            String.valueOf(material.getDesignation()),
            Double.valueOf(material.getKilogramPerCubeMeterVolumicDensity()),
            new HashSet<MaterialMarkingStatistic>(material.getMaterialMarkingStatistics())
        );
    }

    @Override
    public Material getThis() {
        return this;
    }

    public Object copy() {
        return new Material(this).id(getId());
    }

    @Override
    public Long getArticleNumber() {
        return getNumber();
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Material id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return this.number;
    }

    public Material number(Long number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Override
    public String getDesignation() {
        return this.designation;
    }

    public Material designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getKilogramPerCubeMeterVolumicDensity() {
        return this.kilogramPerCubeMeterVolumicDensity;
    }

    public Material kilogramPerCubeMeterVolumicDensity(Double kilogramPerCubeMeterVolumicDensity) {
        this.setKilogramPerCubeMeterVolumicDensity(kilogramPerCubeMeterVolumicDensity);
        return this;
    }

    public void setKilogramPerCubeMeterVolumicDensity(Double kilogramPerCubeMeterVolumicDensity) {
        this.kilogramPerCubeMeterVolumicDensity = kilogramPerCubeMeterVolumicDensity;
    }

    public Set<MaterialMarkingStatistic> getMaterialMarkingStatistics() {
        return this.materialMarkingStatistics;
    }

    public void setMaterialMarkingStatistics(Set<MaterialMarkingStatistic> materialMarkingStatistics) {
        if (this.materialMarkingStatistics != null) {
            this.materialMarkingStatistics.forEach(i -> i.setMaterial(null));
        }
        if (materialMarkingStatistics != null) {
            materialMarkingStatistics.forEach(i -> i.setMaterial(this));
        }
        this.materialMarkingStatistics = materialMarkingStatistics;
    }

    public Material materialMarkingStatistics(Set<MaterialMarkingStatistic> materialMarkingStatistics) {
        this.setMaterialMarkingStatistics(materialMarkingStatistics);
        return this;
    }

    public Material addMaterialMarkingStatistics(MaterialMarkingStatistic materialMarkingStatistic) {
        this.materialMarkingStatistics.add(materialMarkingStatistic);
        materialMarkingStatistic.setMaterial(this);
        return this;
    }

    public Material removeMaterialMarkingStatistics(MaterialMarkingStatistic materialMarkingStatistic) {
        this.materialMarkingStatistics.remove(materialMarkingStatistic);
        materialMarkingStatistic.setMaterial(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Material)) {
            return false;
        }
        return id != null && id.equals(((Material) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Material{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", designation='" + getDesignation() + "'" +
            ", kilogramPerCubeMeterVolumicDensity=" + getKilogramPerCubeMeterVolumicDensity() +
            "}";
    }
}
