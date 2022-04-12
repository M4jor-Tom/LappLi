package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractNonCentralAssembly;
import com.muller.lappli.domain.enumeration.AssemblyMean;
import com.muller.lappli.domain.enumeration.OperationKind;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CoreAssembly.
 */
@Entity
@Table(name = "core_assembly")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CoreAssembly extends AbstractNonCentralAssembly<CoreAssembly> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "coreAssemblies", "intersticeAssemblies", "tapeLayings", "sheathings", "strand", "centralAssembly", "study" },
        allowSetters = true
    )
    private StrandSupply ownerStrandSupply;

    public CoreAssembly() {
        super();
    }

    @Override
    public CoreAssembly getThis() {
        return this;
    }

    @Override
    public Boolean isConform() {
        if (getOwnerStrandSupply() == null) {
            return false;
        }

        return super.isConform() && getOwnerStrandSupply().isConform();
    }

    @Override
    public OperationKind getOperationKind() {
        return OperationKind.CORE_ASSEMBLY;
    }

    @Override
    public Long getProductionStep() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double getBeforeThisMilimeterDiameter() {
        if (getOwnerStrandSupply() == null) {
            return Double.NaN;
        } else if (getOwnerStrandSupply().getLastOperationBefore(this) == null) {
            //If the CoreAssembly is the one at the center,
            //and there's no CentralAssembly under it
            return CalculatorManager.getCalculatorInstance().getMilimeterCentralVoidDiameter(getOwnerStrandSupply());
        }

        return getOwnerStrandSupply().getMilimeterDiameterBefore(this);
    }

    @Override
    public Double getMilimeterDiameterIncidency() {
        if (getOwnerStrandSupply() == null) {
            return Double.NaN;
        } else if (getOwnerStrandSupply().getStrand() == null) {
            return Double.NaN;
        }

        return 2 * getOwnerStrandSupply().getStrand().getSuppliedComponentsAverageMilimeterDiameter();
    }

    @Override
    public Double getDiameterAssemblyStep() {
        if (getOwnerStrandSupply() == null) {
            return Double.NaN;
        }

        return getOwnerStrandSupply().getDiameterAssemblyStep();
    }

    @Override
    public AssemblyMean getAssemblyMean() {
        if (getOwnerStrandSupply() == null) {
            return null;
        }

        return getOwnerStrandSupply().getAssemblyMean();
    }

    /**
     * @return the assembly void, with the supplied components'
     * average milimeter diameter as a unit
     */
    public Double getSuppliedComponentsAverageDiameterAssemblyVoid() {
        try {
            return CalculatorManager
                .getCalculatorInstance()
                .getSuppliedComponentsAverageDiameterAssemblyVoid(getOwnerStrandSupply(), getAssemblyCountUnderThisPlus1() - 1);
        } catch (NullPointerException e) {} catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return Double.NaN;
    }

    /**
     * @return the assembly void in milimeters
     */
    public Double getMilimeterAssemblyVoid() {
        if (getOwnerStrandSupply() == null) {
            return Double.NaN;
        } else if (getOwnerStrandSupply().getStrand() == null) {
            return Double.NaN;
        }

        return (
            getSuppliedComponentsAverageDiameterAssemblyVoid() *
            getOwnerStrandSupply().getStrand().getSuppliedComponentsAverageMilimeterDiameter()
        );
    }

    /**
     * @return the standardized {@link String} value of
     * {@link CoreAssembly#getMilimeterAssemblyVoid}
     */
    public String getMullerStandardizedFormatMilimeterAssemblyVoid() {
        return DomainManager.mullerStandardizedFormat(getMilimeterAssemblyVoid());
    }

    @Override
    public Long getComponentsCount() {
        AssemblyPreset suggestedAssemblyPreset = suggestAssemblyPreset();

        if (suggestedAssemblyPreset == null) {
            return DomainManager.ERROR_LONG_POSITIVE_VALUE;
        }

        return suggestedAssemblyPreset.getTotalComponentsCount();
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public StrandSupply getOwnerStrandSupply() {
        return this.ownerStrandSupply;
    }

    public void setOwnerStrandSupply(StrandSupply strandSupply) {
        this.ownerStrandSupply = strandSupply;
    }

    public CoreAssembly ownerStrandSupply(StrandSupply strandSupply) {
        this.setOwnerStrandSupply(strandSupply);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoreAssembly)) {
            return false;
        }
        return getId() != null && getId().equals(((CoreAssembly) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CoreAssembly{" +
            "id=" + getId() +
            ", operationLayer=" + getOperationLayer() +
            ", forcedMeanMilimeterComponentDiameter=" + getForcedMeanMilimeterComponentDiameter() +
            ", componentsCount=" + getComponentsCount() +
            ", beforeThisMilimeterDiameter=" + getBeforeThisMilimeterDiameter() +
            ", milimeterDiameterIncidency=" + getMilimeterDiameterIncidency() +
            ", milimeterAssemblyVoid=" + getMilimeterAssemblyVoid() +
            "}";
    }
}
