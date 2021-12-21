package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractMarkedLiftedSupply;
import com.muller.lappli.domain.enumeration.Color;
import com.muller.lappli.domain.enumeration.MarkingType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OneStudySupply.
 */
@Entity
@Table(name = "one_study_supply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OneStudySupply extends AbstractMarkedLiftedSupply implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "apparitions")
    private Long apparitions;

    @Column(name = "number")
    private Long number;

    @Column(name = "designation")
    private String designation;

    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marking_type", nullable = false)
    private MarkingType markingType;

    @NotNull
    @Column(name = "gram_per_meter_linear_mass", nullable = false)
    private Double gramPerMeterLinearMass;

    @NotNull
    @Column(name = "milimeter_diameter", nullable = false)
    private Double milimeterDiameter;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "surface_color", nullable = false)
    private Color surfaceColor;

    @ManyToOne(optional = false)
    @NotNull
    //@JsonIgnoreProperties(value = { "materialMarkingStatistics" }, allowSetters = true)
    private Material surfaceMaterial;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "elementSupplies", "bangleSupplies", "customComponentSupplies", "oneStudySupplies" },
        allowSetters = true
    )
    private Strand strand;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OneStudySupply id(Long id) {
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

    public OneStudySupply apparitions(Long apparitions) {
        this.setApparitions(apparitions);
        return this;
    }

    public void setApparitions(Long apparitions) {
        this.apparitions = apparitions;
    }

    public Long getNumber() {
        return this.number;
    }

    public OneStudySupply number(Long number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getDesignation() {
        return this.designation;
    }

    public OneStudySupply designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return this.description;
    }

    public OneStudySupply description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public MarkingType getMarkingType() {
        return this.markingType;
    }

    public OneStudySupply markingType(MarkingType markingType) {
        this.setMarkingType(markingType);
        return this;
    }

    public void setMarkingType(MarkingType markingType) {
        this.markingType = markingType;
    }

    public Double getGramPerMeterLinearMass() {
        return this.gramPerMeterLinearMass;
    }

    public OneStudySupply gramPerMeterLinearMass(Double gramPerMeterLinearMass) {
        this.setGramPerMeterLinearMass(gramPerMeterLinearMass);
        return this;
    }

    public void setGramPerMeterLinearMass(Double gramPerMeterLinearMass) {
        this.gramPerMeterLinearMass = gramPerMeterLinearMass;
    }

    @Override
    public Double getMilimeterDiameter() {
        return this.milimeterDiameter;
    }

    public OneStudySupply milimeterDiameter(Double milimeterDiameter) {
        this.setMilimeterDiameter(milimeterDiameter);
        return this;
    }

    public void setMilimeterDiameter(Double milimeterDiameter) {
        this.milimeterDiameter = milimeterDiameter;
    }

    @Override
    public Color getSurfaceColor() {
        return this.surfaceColor;
    }

    public OneStudySupply surfaceColor(Color surfaceColor) {
        this.setSurfaceColor(surfaceColor);
        return this;
    }

    public void setSurfaceColor(Color surfaceColor) {
        this.surfaceColor = surfaceColor;
    }

    @Override
    public Material getSurfaceMaterial() {
        return this.surfaceMaterial;
    }

    public void setSurfaceMaterial(Material material) {
        this.surfaceMaterial = material;
    }

    public OneStudySupply surfaceMaterial(Material material) {
        this.setSurfaceMaterial(material);
        return this;
    }

    public Strand getStrand() {
        return this.strand;
    }

    public void setStrand(Strand strand) {
        this.strand = strand;
    }

    public OneStudySupply strand(Strand strand) {
        this.setStrand(strand);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OneStudySupply)) {
            return false;
        }
        return id != null && id.equals(((OneStudySupply) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OneStudySupply{" +
            "id=" + getId() +
            ", apparitions=" + getApparitions() +
            ", number=" + getNumber() +
            ", designation='" + getDesignation() + "'" +
            ", description='" + getDescription() + "'" +
            ", markingType='" + getMarkingType() + "'" +
            ", gramPerMeterLinearMass=" + getGramPerMeterLinearMass() +
            ", milimeterDiameter=" + getMilimeterDiameter() +
            ", surfaceColor='" + getSurfaceColor() + "'" +
            "}";
    }
}