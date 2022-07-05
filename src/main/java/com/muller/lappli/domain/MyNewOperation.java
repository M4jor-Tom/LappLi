package com.muller.lappli.domain;

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
 * A MyNewOperation.
 */
@Entity
@Table(name = "my_new_operation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MyNewOperation extends AbstractOperation<MyNewOperation> implements Serializable, INonAssemblyOperation<MyNewOperation> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "operation_layer", nullable = false)
    private Long operationLayer;

    @NotNull
    @Column(name = "operation_data", nullable = false)
    private Double operationData;

    @Column(name = "anonymous_my_new_component_number")
    private Long anonymousMyNewComponentNumber;

    @Column(name = "anonymous_my_new_component_designation")
    private String anonymousMyNewComponentDesignation;

    @Column(name = "anonymous_my_new_component_data")
    private Double anonymousMyNewComponentData;

    @ManyToOne
    private MyNewComponent myNewComponent;

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
            "myNewOperations",
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

    public MyNewOperation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOperationLayer() {
        return this.operationLayer;
    }

    public MyNewOperation operationLayer(Long operationLayer) {
        this.setOperationLayer(operationLayer);
        return this;
    }

    public void setOperationLayer(Long operationLayer) {
        this.operationLayer = operationLayer;
    }

    public Double getOperationData() {
        return this.operationData;
    }

    public MyNewOperation operationData(Double operationData) {
        this.setOperationData(operationData);
        return this;
    }

    public void setOperationData(Double operationData) {
        this.operationData = operationData;
    }

    public Long getAnonymousMyNewComponentNumber() {
        return this.anonymousMyNewComponentNumber;
    }

    public MyNewOperation anonymousMyNewComponentNumber(Long anonymousMyNewComponentNumber) {
        this.setAnonymousMyNewComponentNumber(anonymousMyNewComponentNumber);
        return this;
    }

    public void setAnonymousMyNewComponentNumber(Long anonymousMyNewComponentNumber) {
        this.anonymousMyNewComponentNumber = anonymousMyNewComponentNumber;
    }

    public String getAnonymousMyNewComponentDesignation() {
        return this.anonymousMyNewComponentDesignation;
    }

    public MyNewOperation anonymousMyNewComponentDesignation(String anonymousMyNewComponentDesignation) {
        this.setAnonymousMyNewComponentDesignation(anonymousMyNewComponentDesignation);
        return this;
    }

    public void setAnonymousMyNewComponentDesignation(String anonymousMyNewComponentDesignation) {
        this.anonymousMyNewComponentDesignation = anonymousMyNewComponentDesignation;
    }

    public Double getAnonymousMyNewComponentData() {
        return this.anonymousMyNewComponentData;
    }

    public MyNewOperation anonymousMyNewComponentData(Double anonymousMyNewComponentData) {
        this.setAnonymousMyNewComponentData(anonymousMyNewComponentData);
        return this;
    }

    public void setAnonymousMyNewComponentData(Double anonymousMyNewComponentData) {
        this.anonymousMyNewComponentData = anonymousMyNewComponentData;
    }

    /**
     * As {@link #getFinalMyNewComponent()} exists, this shall not be used
     * for other purposes than testing
     */
    public MyNewComponent getMyNewComponent() {
        return this.myNewComponent;
    }

    public void setMyNewComponent(MyNewComponent myNewComponent) {
        this.myNewComponent = myNewComponent;
    }

    public MyNewOperation myNewComponent(MyNewComponent myNewComponent) {
        this.setMyNewComponent(myNewComponent);
        return this;
    }

    public StrandSupply getOwnerStrandSupply() {
        return this.ownerStrandSupply;
    }

    public void setOwnerStrandSupply(StrandSupply strandSupply) {
        this.ownerStrandSupply = strandSupply;
    }

    public MyNewOperation ownerStrandSupply(StrandSupply strandSupply) {
        this.setOwnerStrandSupply(strandSupply);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyNewOperation)) {
            return false;
        }
        return id != null && id.equals(((MyNewOperation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MyNewOperation{" +
            "id=" + getId() +
            ", operationLayer=" + getOperationLayer() +
            ", operationData=" + getOperationData() +
            ", anonymousMyNewComponentNumber=" + getAnonymousMyNewComponentNumber() +
            ", anonymousMyNewComponentDesignation='" + getAnonymousMyNewComponentDesignation() + "'" +
            ", anonymousMyNewComponentData=" + getAnonymousMyNewComponentData() +
            "}";
    }

    @Override
    public MyNewOperation getThis() {
        return this;
    }

    @Override
    public IOperation<MyNewOperation> toOperation() {
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
    public OperationKind getOperationKind() {
        return OperationKind.MY_NEW_OPERATION;
    }

    @Override
    public Double getMilimeterDiameterIncidency() {
        Double milimeterDiameterIncidency = Double.NaN;
        MyNewComponent myNewComponent = getFinalMyNewComponent();

        //Check no field is null
        if (myNewComponent != null && getOperationData() != null) {
            // Let's invent a way to calculate that
            milimeterDiameterIncidency = myNewComponent.getData() * getOperationData();
        }

        return milimeterDiameterIncidency;
    }

    @Override
    public Long getProductionStep() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getProductDesignation() {
        String productDesignation = "";
        MyNewComponent myNewComponent = getFinalMyNewComponent();

        if (myNewComponent != null) {
            productDesignation = myNewComponent.getDesignation();
        }

        return productDesignation;
    }

    /**
     * The anonymous component getter, for one-time components.
     * As it is created for {@link #getFinalMyNewComponent()}
     * this shall not be used elsewhere.
     */
    private MyNewComponent getAnonmousMyNewComponent() {
        return new MyNewComponent()
            .id(null)
            .number(getAnonymousMyNewComponentNumber())
            .designation(getAnonymousMyNewComponentDesignation())
            .data(getAnonymousMyNewComponentData())
            .getThisIfConform()
            .orElse(null);
    }

    /**
     * The component to use for the operation,
     * {@link #getMyNewComponent()} shall not be used.
     */
    public MyNewComponent getFinalMyNewComponent() {
        MyNewComponent finalMyNewComponent = null;

        if (getMyNewComponent() == null) {
            finalMyNewComponent = getAnonmousMyNewComponent();
        } else {
            finalMyNewComponent = getMyNewComponent();
        }

        return finalMyNewComponent;
    }
}
