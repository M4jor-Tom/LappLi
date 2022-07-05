package com.muller.lappli.domain;

import com.muller.lappli.domain.abstracts.AbstractLiftedSupply;
import com.muller.lappli.domain.abstracts.AbstractMachine;
import com.muller.lappli.domain.enumeration.MarkingTechnique;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.domain.exception.UnknownSupplyException;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Lifter.
 * A machine to check in production time any data of
 * {@link com.muller.lappli.domain.abstracts.AbstractLiftedSupply}
 */
@Entity
@Table(name = "lifter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Lifter extends AbstractMachine<Lifter> implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "minimum_milimeter_diameter", nullable = false)
    private Double minimumMilimeterDiameter;

    @NotNull
    @Column(name = "maximum_milimeter_diameter", nullable = false)
    private Double maximumMilimeterDiameter;

    @NotNull
    @Column(name = "supports_spirally_colored_marking_type", nullable = false)
    private Boolean supportsSpirallyColoredMarkingType;

    @NotNull
    @Column(name = "supports_longitudinally_colored_marking_type", nullable = false)
    private Boolean supportsLongitudinallyColoredMarkingType;

    @NotNull
    @Column(name = "supports_numbered_marking_type", nullable = false)
    private Boolean supportsNumberedMarkingType;

    @NotNull
    @Column(name = "supports_ink_jet_marking_technique", nullable = false)
    private Boolean supportsInkJetMarkingTechnique;

    @NotNull
    @Column(name = "supports_rsd_marking_technique", nullable = false)
    private Boolean supportsRsdMarkingTechnique;

    public Lifter() {
        super();
    }

    @Override
    public Lifter getThis() {
        return this;
    }

    @Override
    protected String getPrefix() {
        return "MR";
    }

    public Boolean supportsSupply(AbstractLiftedSupply<?> abstractLiftedSupply) {
        if (abstractLiftedSupply instanceof CustomComponentSupply) {
            return supportsCustomComponentSupply((CustomComponentSupply) abstractLiftedSupply);
        } else if (abstractLiftedSupply instanceof ElementSupply) {
            return supportsElementSupply((ElementSupply) abstractLiftedSupply);
        } else if (abstractLiftedSupply instanceof BangleSupply) {
            return supportsBangleSupply((BangleSupply) abstractLiftedSupply);
        } else if (abstractLiftedSupply instanceof OneStudySupply) {
            return supportsOneStudySupply((OneStudySupply) abstractLiftedSupply);
        } else if (abstractLiftedSupply instanceof MyNewComponentSupply) {
            return supportsMyNewComponentSupply((MyNewComponentSupply) abstractLiftedSupply);
        }

        (new UnknownSupplyException(abstractLiftedSupply.toString())).printStackTrace();
        return false;
    }

    public Boolean supportsCustomComponentSupply(CustomComponentSupply customComponentSupply) {
        return (
            supportsMarkingType(customComponentSupply.getMarkingType()) &&
            supportsMilimeterDiameter(customComponentSupply.getMilimeterDiameter()) &&
            supportsMarkingTechnique(customComponentSupply.getMarkingTechnique())
        );
    }

    public Boolean supportsElementSupply(ElementSupply elementSupply) {
        return (
            supportsMarkingType(elementSupply.getMarkingType()) &&
            supportsMilimeterDiameter(elementSupply.getMilimeterDiameter()) &&
            supportsMarkingTechnique(elementSupply.getMarkingTechnique())
        );
    }

    public Boolean supportsBangleSupply(BangleSupply bangleSupply) {
        return (supportsMilimeterDiameter(bangleSupply.getMilimeterDiameter()));
    }

    public Boolean supportsOneStudySupply(OneStudySupply oneStudySupply) {
        return (
            supportsMarkingType(oneStudySupply.getMarkingType()) &&
            supportsMilimeterDiameter(oneStudySupply.getMilimeterDiameter()) &&
            supportsMarkingTechnique(oneStudySupply.getMarkingTechnique())
        );
    }

    public Boolean supportsMyNewComponentSupply(MyNewComponentSupply myNewComponentSupply) {
        return (
            supportsMarkingType(myNewComponentSupply.getMarkingType()) &&
            supportsMilimeterDiameter(myNewComponentSupply.getMilimeterDiameter()) &&
            supportsMarkingTechnique(myNewComponentSupply.getMarkingTechnique())
        );
    }

    public Boolean supportsMilimeterDiameter(Double milimeterDiameter) {
        return milimeterDiameter > getMinimumMilimeterDiameter() && milimeterDiameter < getMaximumMilimeterDiameter();
    }

    public Boolean supportsMarkingType(MarkingType markingType) {
        switch (markingType) {
            case LIFTING:
            case RINGY_COLORED:
                return true;
            case SPIRALLY_COLORED:
                return getSupportsSpirallyColoredMarkingType();
            case LONGITUDINALLY_COLORED:
                return getSupportsLongitudinallyColoredMarkingType();
            case NUMBERED:
                return getSupportsNumberedMarkingType();
        }

        return false;
    }

    public Boolean supportsMarkingTechnique(MarkingTechnique markingTechnique) {
        switch (markingTechnique) {
            case NONE_SUITABLE:
                return false;
            case NONE:
                return true;
            case INK_JET:
                return getSupportsInkJetMarkingTechnique();
            case RSD:
                return getSupportsRsdMarkingTechnique();
        }

        return false;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Double getMinimumMilimeterDiameter() {
        return this.minimumMilimeterDiameter;
    }

    public Lifter minimumMilimeterDiameter(Double minimumMilimeterDiameter) {
        this.setMinimumMilimeterDiameter(minimumMilimeterDiameter);
        return this;
    }

    public void setMinimumMilimeterDiameter(Double minimumMilimeterDiameter) {
        this.minimumMilimeterDiameter = minimumMilimeterDiameter;
    }

    public Double getMaximumMilimeterDiameter() {
        return this.maximumMilimeterDiameter;
    }

    public Lifter maximumMilimeterDiameter(Double maximumMilimeterDiameter) {
        this.setMaximumMilimeterDiameter(maximumMilimeterDiameter);
        return this;
    }

    public void setMaximumMilimeterDiameter(Double maximumMilimeterDiameter) {
        this.maximumMilimeterDiameter = maximumMilimeterDiameter;
    }

    public Boolean getSupportsSpirallyColoredMarkingType() {
        return this.supportsSpirallyColoredMarkingType;
    }

    public Lifter supportsSpirallyColoredMarkingType(Boolean supportsSpirallyColoredMarkingType) {
        this.setSupportsSpirallyColoredMarkingType(supportsSpirallyColoredMarkingType);
        return this;
    }

    public void setSupportsSpirallyColoredMarkingType(Boolean supportsSpirallyColoredMarkingType) {
        this.supportsSpirallyColoredMarkingType = supportsSpirallyColoredMarkingType;
    }

    public Boolean getSupportsLongitudinallyColoredMarkingType() {
        return this.supportsLongitudinallyColoredMarkingType;
    }

    public Lifter supportsLongitudinallyColoredMarkingType(Boolean supportsLongitudinallyColoredMarkingType) {
        this.setSupportsLongitudinallyColoredMarkingType(supportsLongitudinallyColoredMarkingType);
        return this;
    }

    public void setSupportsLongitudinallyColoredMarkingType(Boolean supportsLongitudinallyColoredMarkingType) {
        this.supportsLongitudinallyColoredMarkingType = supportsLongitudinallyColoredMarkingType;
    }

    public Boolean getSupportsNumberedMarkingType() {
        return this.supportsNumberedMarkingType;
    }

    public Lifter supportsNumberedMarkingType(Boolean supportsNumberedMarkingType) {
        this.setSupportsNumberedMarkingType(supportsNumberedMarkingType);
        return this;
    }

    public void setSupportsNumberedMarkingType(Boolean supportsNumberedMarkingType) {
        this.supportsNumberedMarkingType = supportsNumberedMarkingType;
    }

    public Boolean getSupportsInkJetMarkingTechnique() {
        return this.supportsInkJetMarkingTechnique;
    }

    public Lifter supportsInkJetMarkingTechnique(Boolean supportsInkJetMarkingTechnique) {
        this.setSupportsInkJetMarkingTechnique(supportsInkJetMarkingTechnique);
        return this;
    }

    public void setSupportsInkJetMarkingTechnique(Boolean supportsInkJetMarkingTechnique) {
        this.supportsInkJetMarkingTechnique = supportsInkJetMarkingTechnique;
    }

    public Boolean getSupportsRsdMarkingTechnique() {
        return this.supportsRsdMarkingTechnique;
    }

    public Lifter supportsRsdMarkingTechnique(Boolean supportsRsdMarkingTechnique) {
        this.setSupportsRsdMarkingTechnique(supportsRsdMarkingTechnique);
        return this;
    }

    public void setSupportsRsdMarkingTechnique(Boolean supportsRsdMarkingTechnique) {
        this.supportsRsdMarkingTechnique = supportsRsdMarkingTechnique;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lifter)) {
            return false;
        }
        return getId() != null && getId().equals(((Lifter) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lifter{" +
            "id=" + getId() +
            ", index=" + getIndex() +
            ", minimumMilimeterDiameter=" + getMinimumMilimeterDiameter() +
            ", maximumMilimeterDiameter=" + getMaximumMilimeterDiameter() +
            ", supportsSpirallyColoredMarkingType='" + getSupportsSpirallyColoredMarkingType() + "'" +
            ", supportsLongitudinallyColoredMarkingType='" + getSupportsLongitudinallyColoredMarkingType() + "'" +
            ", supportsNumberedMarkingType='" + getSupportsNumberedMarkingType() + "'" +
            ", supportsInkJetMarkingTechnique='" + getSupportsInkJetMarkingTechnique() + "'" +
            ", supportsRsdMarkingTechnique='" + getSupportsRsdMarkingTechnique() + "'" +
            "}";
    }
}
