package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractMachine;
import com.muller.lappli.domain.abstracts.AbstractOperation;
import com.muller.lappli.domain.enumeration.OperationKind;
import com.muller.lappli.domain.interfaces.INonAssemblyOperation;
import com.muller.lappli.domain.interfaces.IOperation;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FlatSheathing.
 */
@Entity
@Table(name = "flat_sheathing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FlatSheathing extends AbstractOperation<FlatSheathing> implements Serializable, INonAssemblyOperation<FlatSheathing> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "operation_layer", nullable = false)
    private Long operationLayer;

    @NotNull
    @Column(name = "milimeter_width", nullable = false)
    private Double milimeterWidth;

    @NotNull
    @Column(name = "milimeter_height", nullable = false)
    private Double milimeterHeight;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "materialMarkingStatistics" }, allowSetters = true)
    private Material material;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "coreAssemblies",
            "intersticeAssemblies",
            "tapeLayings",
            "screens",
            "stripLayings",
            "plaits",
            "carrierPlaits",
            "sheathings",
            "flatSheathings",
            "continuityWireLongitLayings",
            "strand",
            "centralAssembly",
            "study",
        },
        allowSetters = true
    )
    private StrandSupply ownerStrandSupply;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public FlatSheathing() {
        super();
    }

    @Override
    public IOperation<FlatSheathing> toOperation() {
        return this;
    }

    @Override
    public FlatSheathing getThis() {
        return this;
    }

    @Override
    public AbstractMachine<?> getOperatingMachine() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double getHourPreparationTime() {
        // TODO Auto-generated method stub
        return Double.NaN;
    }

    @Override
    public Double getHourExecutionTime() {
        // TODO Auto-generated method stub
        return Double.NaN;
    }

    @Override
    public Boolean isConform() {
        if (getOwnerStrandSupply() == null) {
            return super.isConform();
        }

        return super.isConform() && getOwnerStrandSupply().couldBeFlat();
    }

    @Override
    public OperationKind getOperationKind() {
        return OperationKind.FLAT_SHEATHING;
    }

    @JsonIgnore
    @Override
    public Double getMilimeterDiameterIncidency() {
        throw new UnsupportedOperationException("FlatSheathing.getMilimeterDiameterIncidency() shall not be used");
    }

    @Override
    public String getMullerStandardizedFormatMilimeterDiameterIncidency() {
        return "";
    }

    @Override
    public Double getAfterThisMilimeterDiameter() {
        return Double.NaN;
    }

    @Override
    public Long getProductionStep() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getProductDesignation() {
        if (getMaterial() == null) {
            return null;
        }

        return getMaterial().getDesignation();
    }

    public Long getOperationLayer() {
        return this.operationLayer;
    }

    public FlatSheathing operationLayer(Long operationLayer) {
        this.setOperationLayer(operationLayer);
        return this;
    }

    public void setOperationLayer(Long operationLayer) {
        this.operationLayer = operationLayer;
    }

    public Double getMilimeterWidth() {
        return this.milimeterWidth;
    }

    public FlatSheathing milimeterWidth(Double milimeterWidth) {
        this.setMilimeterWidth(milimeterWidth);
        return this;
    }

    public void setMilimeterWidth(Double milimeterWidth) {
        this.milimeterWidth = milimeterWidth;
    }

    public Double getMilimeterHeight() {
        return this.milimeterHeight;
    }

    public FlatSheathing milimeterHeight(Double milimeterHeight) {
        this.setMilimeterHeight(milimeterHeight);
        return this;
    }

    public void setMilimeterHeight(Double milimeterHeight) {
        this.milimeterHeight = milimeterHeight;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public FlatSheathing material(Material material) {
        this.setMaterial(material);
        return this;
    }

    public StrandSupply getOwnerStrandSupply() {
        return this.ownerStrandSupply;
    }

    public void setOwnerStrandSupply(StrandSupply strandSupply) {
        this.ownerStrandSupply = strandSupply;
    }

    public FlatSheathing ownerStrandSupply(StrandSupply strandSupply) {
        this.setOwnerStrandSupply(strandSupply);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FlatSheathing)) {
            return false;
        }
        return getId() != null && getId().equals(((FlatSheathing) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FlatSheathing{" +
            "id=" + getId() +
            ", operationLayer=" + getOperationLayer() +
            ", milimeterWidth=" + getMilimeterWidth() +
            ", milimeterHeight=" + getMilimeterHeight() +
            "}";
    }
}
