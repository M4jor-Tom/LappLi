package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractOperation;
import com.muller.lappli.domain.enumeration.AssemblyMean;
import com.muller.lappli.domain.enumeration.OperationKind;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TapeLaying.
 */
@Entity
@Table(name = "tape_laying")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TapeLaying extends AbstractOperation<TapeLaying> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "operation_layer", nullable = false)
    private Long operationLayer;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "assembly_mean", nullable = false)
    private AssemblyMean assemblyMean;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "tapeKind" }, allowSetters = true)
    private Tape tape;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "coreAssemblies", "intersticeAssemblies", "tapeLayings", "sheathings", "strand", "centralAssembly", "study" },
        allowSetters = true
    )
    private StrandSupply ownerStrandSupply;

    public TapeLaying() {
        super();
        setOperationLayer(null);
        setAssemblyMean(null);
        setTape(null);
        setOwnerStrandSupply(null);
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public TapeLaying getThis() {
        return this;
    }

    @Override
    public OperationKind getOperationKind() {
        return OperationKind.TAPE_LAYING;
    }

    @Override
    public Double getMilimeterDiameterIncidency() {
        if (getTape() == null) {
            return Double.NaN;
        }

        return getTape().getMilimeterDiameterIncidency();
    }

    @Override
    public Long getProductionStep() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getProductDesignation() {
        if (getTape() == null) {
            return "";
        }

        return getTape().getDesignation();
    }

    public Long getId() {
        return this.id;
    }

    public TapeLaying id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOperationLayer() {
        return this.operationLayer;
    }

    public TapeLaying operationLayer(Long operationLayer) {
        this.setOperationLayer(operationLayer);
        return this;
    }

    public void setOperationLayer(Long operationLayer) {
        this.operationLayer = operationLayer;
    }

    public AssemblyMean getAssemblyMean() {
        return this.assemblyMean;
    }

    public TapeLaying assemblyMean(AssemblyMean assemblyMean) {
        this.setAssemblyMean(assemblyMean);
        return this;
    }

    public void setAssemblyMean(AssemblyMean assemblyMean) {
        this.assemblyMean = assemblyMean;
    }

    public Tape getTape() {
        return this.tape;
    }

    public void setTape(Tape tape) {
        this.tape = tape;
    }

    public TapeLaying tape(Tape tape) {
        this.setTape(tape);
        return this;
    }

    @Override
    public StrandSupply getOwnerStrandSupply() {
        return this.ownerStrandSupply;
    }

    public void setOwnerStrandSupply(StrandSupply strandSupply) {
        this.ownerStrandSupply = strandSupply;
    }

    public TapeLaying ownerStrandSupply(StrandSupply strandSupply) {
        this.setOwnerStrandSupply(strandSupply);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TapeLaying)) {
            return false;
        }
        return id != null && id.equals(((TapeLaying) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TapeLaying{" +
            "id=" + getId() +
            ", operationLayer=" + getOperationLayer() +
            ", assemblyMean='" + getAssemblyMean() + "'" +
            "}";
    }
}
