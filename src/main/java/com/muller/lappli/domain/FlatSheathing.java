package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class FlatSheathing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

    public Long getId() {
        return this.id;
    }

    public FlatSheathing id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
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
        return id != null && id.equals(((FlatSheathing) o).id);
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
