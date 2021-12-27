package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.exception.PositionAlreadyHasSupplyException;
import com.muller.lappli.domain.exception.PositionAlreadyInAssemblyException;
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

    @ManyToOne
    @JsonIgnoreProperties(value = { "strand", "positions" }, allowSetters = true)
    private CentralAssembly ownerCentralAssembly;

    @ManyToOne
    @JsonIgnoreProperties(value = { "positions", "strand" }, allowSetters = true)
    private CoreAssembly ownerCoreAssembly;

    @ManyToOne
    @JsonIgnoreProperties(value = { "positions", "strand" }, allowSetters = true)
    private IntersticeAssembly ownerIntersticeAssembly;

    public Boolean isOfElementSupply() {
        return getElementSupply() != null;
    }

    public Boolean isOfBangleSupply() {
        return getBangleSupply() != null;
    }

    public Boolean isOfCustomComponentSupply() {
        return getCustomComponentSupply() != null;
    }

    public Boolean isOfOneStudySupply() {
        return getOneStudySupply() != null;
    }

    public Boolean isInCentralAssembly() {
        return getOwnerCentralAssembly() != null;
    }

    public Boolean isInCoreAssembly() {
        return getOwnerCoreAssembly() != null;
    }

    public Boolean isInIntersticeAssembly() {
        return getOwnerIntersticeAssembly() != null;
    }

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

    public void setElementSupply(ElementSupply elementSupply) throws PositionAlreadyHasSupplyException {
        if (isOfBangleSupply() || isOfCustomComponentSupply() || isOfOneStudySupply()) {
            throw new PositionAlreadyHasSupplyException();
        }
        this.elementSupply = elementSupply;
    }

    public Position elementSupply(ElementSupply elementSupply) throws PositionAlreadyHasSupplyException {
        this.setElementSupply(elementSupply);
        return this;
    }

    public Position forceElementSupply(ElementSupply elementSupply) {
        try {
            setElementSupply(elementSupply);
        } catch (PositionAlreadyHasSupplyException e) {
            e.printStackTrace();
        }
        return this;
    }

    public BangleSupply getBangleSupply() {
        return this.bangleSupply;
    }

    public void setBangleSupply(BangleSupply bangleSupply) throws PositionAlreadyHasSupplyException {
        if (isOfCustomComponentSupply() || isOfElementSupply() || isOfOneStudySupply()) {
            throw new PositionAlreadyHasSupplyException();
        }
        this.bangleSupply = bangleSupply;
    }

    public Position bangleSupply(BangleSupply bangleSupply) throws PositionAlreadyHasSupplyException {
        this.setBangleSupply(bangleSupply);
        return this;
    }

    public Position forceBangleSupply(BangleSupply bangleSupply) {
        try {
            setBangleSupply(bangleSupply);
        } catch (PositionAlreadyHasSupplyException e) {
            e.printStackTrace();
        }
        return this;
    }

    public CustomComponentSupply getCustomComponentSupply() {
        return this.customComponentSupply;
    }

    public void setCustomComponentSupply(CustomComponentSupply customComponentSupply) throws PositionAlreadyHasSupplyException {
        if (isOfBangleSupply() || isOfElementSupply() || isOfOneStudySupply()) {
            throw new PositionAlreadyHasSupplyException();
        }
        this.customComponentSupply = customComponentSupply;
    }

    public Position customComponentSupply(CustomComponentSupply customComponentSupply) throws PositionAlreadyHasSupplyException {
        this.setCustomComponentSupply(customComponentSupply);
        return this;
    }

    public Position forceCustomComponentSupply(CustomComponentSupply customComponentSupply) {
        try {
            setCustomComponentSupply(customComponentSupply);
        } catch (PositionAlreadyHasSupplyException e) {
            e.printStackTrace();
        }
        return this;
    }

    public OneStudySupply getOneStudySupply() {
        return this.oneStudySupply;
    }

    public void setOneStudySupply(OneStudySupply oneStudySupply) throws PositionAlreadyHasSupplyException {
        if (isOfBangleSupply() || isOfCustomComponentSupply() || isOfElementSupply()) {
            throw new PositionAlreadyHasSupplyException();
        }
        this.oneStudySupply = oneStudySupply;
    }

    public Position oneStudySupply(OneStudySupply oneStudySupply) throws PositionAlreadyHasSupplyException {
        this.setOneStudySupply(oneStudySupply);
        return this;
    }

    public Position forceOneStudySupply(OneStudySupply oneStudySupply) {
        try {
            setOneStudySupply(oneStudySupply);
        } catch (PositionAlreadyHasSupplyException e) {
            e.printStackTrace();
        }
        return this;
    }

    public CentralAssembly getOwnerCentralAssembly() {
        return this.ownerCentralAssembly;
    }

    public void setOwnerCentralAssembly(CentralAssembly centralAssembly) throws PositionAlreadyInAssemblyException {
        if (isInCoreAssembly() || isInIntersticeAssembly()) {
            throw new PositionAlreadyInAssemblyException();
        }
        this.ownerCentralAssembly = centralAssembly;
    }

    public Position ownerCentralAssembly(CentralAssembly centralAssembly) throws PositionAlreadyInAssemblyException {
        this.setOwnerCentralAssembly(centralAssembly);
        return this;
    }

    public Position forceOwnerCentralAssembly(CentralAssembly centralAssembly) {
        try {
            setOwnerCentralAssembly(centralAssembly);
            setOwnerCoreAssembly(null);
            setOwnerIntersticeAssembly(null);
        } catch (PositionAlreadyInAssemblyException e) {
            e.printStackTrace();
        }

        return this;
    }

    public CoreAssembly getOwnerCoreAssembly() {
        return this.ownerCoreAssembly;
    }

    public void setOwnerCoreAssembly(CoreAssembly coreAssembly) throws PositionAlreadyInAssemblyException {
        if (isInCentralAssembly() || isInIntersticeAssembly()) {
            throw new PositionAlreadyInAssemblyException();
        }
        this.ownerCoreAssembly = coreAssembly;
    }

    public Position ownerCoreAssembly(CoreAssembly coreAssembly) throws PositionAlreadyInAssemblyException {
        this.setOwnerCoreAssembly(coreAssembly);
        return this;
    }

    public Position forceOwnerCoreAssembly(CoreAssembly coreAssembly) {
        try {
            setOwnerCentralAssembly(null);
            setOwnerCoreAssembly(coreAssembly);
            setOwnerIntersticeAssembly(null);
        } catch (PositionAlreadyInAssemblyException e) {
            e.printStackTrace();
        }

        return this;
    }

    public IntersticeAssembly getOwnerIntersticeAssembly() {
        return this.ownerIntersticeAssembly;
    }

    public void setOwnerIntersticeAssembly(IntersticeAssembly intersticeAssembly) throws PositionAlreadyInAssemblyException {
        if (isInCentralAssembly() || isInCoreAssembly()) {
            throw new PositionAlreadyInAssemblyException();
        }
        this.ownerIntersticeAssembly = intersticeAssembly;
    }

    public Position ownerIntersticeAssembly(IntersticeAssembly intersticeAssembly) throws PositionAlreadyInAssemblyException {
        this.setOwnerIntersticeAssembly(intersticeAssembly);
        return this;
    }

    public Position forceOwnerIntersticeAssembly(IntersticeAssembly intersticeAssembly) {
        try {
            setOwnerCentralAssembly(null);
            setOwnerCoreAssembly(null);
            setOwnerIntersticeAssembly(intersticeAssembly);
        } catch (PositionAlreadyInAssemblyException e) {
            e.printStackTrace();
        }

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
