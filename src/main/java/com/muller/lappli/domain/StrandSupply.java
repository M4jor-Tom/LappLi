package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractDomainObject;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.domain.interfaces.Designable;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A StrandSupply.
 */
@Entity
@Table(name = "strand_supply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StrandSupply extends AbstractDomainObject<StrandSupply> implements Designable, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "apparitions", nullable = false)
    private Long apparitions;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marking_type", nullable = false)
    private MarkingType markingType;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "supplyPositions", "coreAssemblies", "intersticeAssemblies", "sheathings", "centralAssembly", "futureStudy" },
        allowSetters = true
    )
    private Strand strand;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "strands", "strandSupplies", "author" }, allowSetters = true)
    private Study study;

    @Override
    public StrandSupply getThis() {
        return this;
    }

    @Override
    public String getDesignation() {
        try {
            return getApparitions().toString() + " x " + getStrand().getDesignation();
        } catch (NullPointerException e) {
            return getApparitions() + " x [?]";
        }
    }

    public Long getSuppliedComponentsDividedCount() {
        if (getStrand() != null && getApparitions() != null) {
            return getStrand().getUndividedSuppliedComponentsCount() / getApparitions();
        }

        return DomainManager.ERROR_LONG_POSITIVE_VALUE;
    }

    public Double getSuppliedComponentsAverageMilimeterDiameter() {
        if (getStrand() != null) {
            return getStrand().getSuppliedComponentsMilimeterDiametersSum() / getSuppliedComponentsDividedCount();
        }

        return null;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public Long getId() {
        return this.id;
    }

    public StrandSupply id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApparitions() {
        return this.apparitions;
    }

    public StrandSupply apparitions(Long apparitions) {
        this.setApparitions(apparitions);
        return this;
    }

    public void setApparitions(Long apparitions) {
        this.apparitions = apparitions;
    }

    public MarkingType getMarkingType() {
        return this.markingType;
    }

    public StrandSupply markingType(MarkingType markingType) {
        this.setMarkingType(markingType);
        return this;
    }

    public void setMarkingType(MarkingType markingType) {
        this.markingType = markingType;
    }

    public String getDescription() {
        return this.description;
    }

    public StrandSupply description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Strand getStrand() {
        return this.strand;
    }

    public void setStrand(Strand strand) {
        this.strand = strand;
    }

    public StrandSupply strand(Strand strand) {
        this.setStrand(strand);
        return this;
    }

    public Study getStudy() {
        return this.study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public StrandSupply study(Study study) {
        this.setStudy(study);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StrandSupply)) {
            return false;
        }
        return id != null && id.equals(((StrandSupply) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StrandSupply{" +
            "id=" + getId() +
            ", apparitions=" + getApparitions() +
            ", markingType='" + getMarkingType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
