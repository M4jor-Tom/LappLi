package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Position.
 */
@Entity
@Table(name = "position")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "value", nullable = false)
    private Long value;

    @JsonIgnoreProperties(value = { "element", "position", "strand" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ElementSupply elementSupply;

    @JsonIgnoreProperties(value = { "bangle", "position", "strand" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private BangleSupply bangleSupply;

    @JsonIgnoreProperties(value = { "customComponent", "position", "strand" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private CustomComponentSupply customComponentSupply;

    @JsonIgnoreProperties(value = { "surfaceMaterial", "position", "strand" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private OneStudySupply oneStudySupply;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "strand", "positions" }, allowSetters = true)
    private CentralAssembly ownerCentralAssembly;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "positions", "strand" }, allowSetters = true)
    private CoreAssembly ownerCoreAssembly;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "positions", "strand" }, allowSetters = true)
    private IntersticeAssembly ownerIntersticeAssembly;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Position id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getValue() {
        return this.value;
    }

    public Position value(Long value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public ElementSupply getElementSupply() {
        return this.elementSupply;
    }

    public void setElementSupply(ElementSupply elementSupply) {
        this.elementSupply = elementSupply;
    }

    public Position elementSupply(ElementSupply elementSupply) {
        this.setElementSupply(elementSupply);
        return this;
    }

    public BangleSupply getBangleSupply() {
        return this.bangleSupply;
    }

    public void setBangleSupply(BangleSupply bangleSupply) {
        this.bangleSupply = bangleSupply;
    }

    public Position bangleSupply(BangleSupply bangleSupply) {
        this.setBangleSupply(bangleSupply);
        return this;
    }

    public CustomComponentSupply getCustomComponentSupply() {
        return this.customComponentSupply;
    }

    public void setCustomComponentSupply(CustomComponentSupply customComponentSupply) {
        this.customComponentSupply = customComponentSupply;
    }

    public Position customComponentSupply(CustomComponentSupply customComponentSupply) {
        this.setCustomComponentSupply(customComponentSupply);
        return this;
    }

    public OneStudySupply getOneStudySupply() {
        return this.oneStudySupply;
    }

    public void setOneStudySupply(OneStudySupply oneStudySupply) {
        this.oneStudySupply = oneStudySupply;
    }

    public Position oneStudySupply(OneStudySupply oneStudySupply) {
        this.setOneStudySupply(oneStudySupply);
        return this;
    }

    public CentralAssembly getOwnerCentralAssembly() {
        return this.ownerCentralAssembly;
    }

    public void setOwnerCentralAssembly(CentralAssembly centralAssembly) {
        this.ownerCentralAssembly = centralAssembly;
    }

    public Position ownerCentralAssembly(CentralAssembly centralAssembly) {
        this.setOwnerCentralAssembly(centralAssembly);
        return this;
    }

    public CoreAssembly getOwnerCoreAssembly() {
        return this.ownerCoreAssembly;
    }

    public void setOwnerCoreAssembly(CoreAssembly coreAssembly) {
        this.ownerCoreAssembly = coreAssembly;
    }

    public Position ownerCoreAssembly(CoreAssembly coreAssembly) {
        this.setOwnerCoreAssembly(coreAssembly);
        return this;
    }

    public IntersticeAssembly getOwnerIntersticeAssembly() {
        return this.ownerIntersticeAssembly;
    }

    public void setOwnerIntersticeAssembly(IntersticeAssembly intersticeAssembly) {
        this.ownerIntersticeAssembly = intersticeAssembly;
    }

    public Position ownerIntersticeAssembly(IntersticeAssembly intersticeAssembly) {
        this.setOwnerIntersticeAssembly(intersticeAssembly);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }
        return id != null && id.equals(((Position) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Position{" +
            "id=" + getId() +
            ", value=" + getValue() +
            "}";
    }
}
