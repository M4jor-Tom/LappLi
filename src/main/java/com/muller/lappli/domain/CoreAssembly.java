package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractNonCentralAssembly;
import com.muller.lappli.domain.abstracts.AbstractOperation;
import com.muller.lappli.domain.enumeration.AssemblyMean;
import com.muller.lappli.domain.enumeration.OperationKind;
import com.muller.lappli.domain.exception.ImpossibleAssemblyPresetDistributionException;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "assembly_layer", nullable = false)
    private Long assemblyLayer;

    @Column(name = "forced_mean_milimeter_component_diameter")
    private Double forcedMeanMilimeterComponentDiameter;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "supplyPositions", "coreAssemblies", "intersticeAssemblies", "sheathings", "centralAssembly", "futureStudy" },
        allowSetters = true
    )
    private Strand ownerStrand;

    @Override
    public CoreAssembly getThis() {
        return this;
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
    public Double getBeforeThisMilimeterDiameter() throws ImpossibleAssemblyPresetDistributionException {
        try {
            AbstractOperation<?> lastOperationBeforeThis = getOwnerStrand().getLastOperationBefore(this);

            if (lastOperationBeforeThis == null) {
                //If the CoreAssembly is the one at the center,
                //and there's no CentralAssembly under it
                return CalculatorManager.getCalculatorInstance().getMilimeterCentralVoidDiameter(getOwnerStrand());
            }

            return getOwnerStrand().getMilimeterDiameterBefore(this);
        } catch (NullPointerException e) {
            return Double.NaN;
        }
    }

    @Override
    public Double getMilimeterDiameterIncidency() {
        try {
            return getOwnerStrand().getSuppliedComponentsAverageMilimeterDiameter();
        } catch (NullPointerException e) {
            return Double.NaN;
        }
    }

    @Override
    public Double getDiameterAssemblyStep() {
        try {
            return getOwnerStrand().getDiameterAssemblyStep();
        } catch (NullPointerException e) {
            return Double.NaN;
        }
    }

    @Override
    public AssemblyMean getAssemblyMean() {
        try {
            return getOwnerStrand().getAssemblyMean();
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * @return the assembly void, with the supplied components'
     * average milimeter diameter as a unit
     */
    public Double getSuppliedComponentsAverageDiameterAssemblyVoid() {
        //TODO: Put assembly void formula here
        return Double.NaN;
    }

    /**
     * @return the assembly void in milimeters
     */
    public Double getMilimeterAssemblyVoid() {
        try {
            return getSuppliedComponentsAverageDiameterAssemblyVoid() * getOwnerStrand().getSuppliedComponentsAverageMilimeterDiameter();
        } catch (NullPointerException e) {
            return Double.NaN;
        }
    }

    public Long getComponentsCount() {
        try {
            return getOwnerStrand()
                .getAssemblyPresetDistributionPossibility()
                .getAssemblyPresets()
                .get(getAssemblyLayer().intValue() - 1)
                .getTotalComponentsCount();
        } catch (NullPointerException e) {
            return DomainManager.ERROR_LONG_POSITIVE_VALUE;
        } catch (IndexOutOfBoundsException e) {
            DomainManager.noticeInPrompt("The following exception is normal into an Intergation Test context");
            e.printStackTrace();
            return DomainManager.ERROR_LONG_POSITIVE_VALUE;
        }
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public Long getId() {
        return this.id;
    }

    public CoreAssembly id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getAssemblyLayer() {
        return this.assemblyLayer;
    }

    public CoreAssembly assemblyLayer(Long assemblyLayer) {
        this.setAssemblyLayer(assemblyLayer);
        return this;
    }

    public void setAssemblyLayer(Long assemblyLayer) {
        this.assemblyLayer = assemblyLayer;
    }

    public Double getForcedMeanMilimeterComponentDiameter() {
        return this.forcedMeanMilimeterComponentDiameter;
    }

    public CoreAssembly forcedMeanMilimeterComponentDiameter(Double forcedMeanMilimeterComponentDiameter) {
        this.setForcedMeanMilimeterComponentDiameter(forcedMeanMilimeterComponentDiameter);
        return this;
    }

    public void setForcedMeanMilimeterComponentDiameter(Double forcedMeanMilimeterComponentDiameter) {
        this.forcedMeanMilimeterComponentDiameter = forcedMeanMilimeterComponentDiameter;
    }

    public Strand getOwnerStrand() {
        return this.ownerStrand;
    }

    public void setOwnerStrand(Strand strand) {
        this.ownerStrand = strand;
    }

    public CoreAssembly ownerStrand(Strand strand) {
        this.setOwnerStrand(strand);
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
        return id != null && id.equals(((CoreAssembly) o).id);
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
            ", assemblyLayer=" + getAssemblyLayer() +
            ", forcedMeanMilimeterComponentDiameter=" + getForcedMeanMilimeterComponentDiameter() +
            ", componentsCount=" + getComponentsCount() +
            "}";
    }
}
