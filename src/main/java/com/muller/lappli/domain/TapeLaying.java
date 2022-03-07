package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.enumeration.AssemblyMean;
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
public class TapeLaying implements Serializable {

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

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
