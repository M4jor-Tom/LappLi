package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.exception.PositionAlreadyHasSupplyException;
import com.muller.lappli.domain.exception.PositionAlreadyInAssemblyException;
import com.muller.lappli.domain.exception.PositionHasSeveralSupplyException;
import com.muller.lappli.domain.exception.PositionInSeveralAssemblyException;
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

    @JsonIgnoreProperties(value = { "strand", "position" }, allowSetters = true)
    @OneToOne(mappedBy = "position")
    private CentralAssembly ownerCentralAssembly;

    @ManyToOne
    @JsonIgnoreProperties(value = { "positions", "strand" }, allowSetters = true)
    private CoreAssembly ownerCoreAssembly;

    @ManyToOne
    @JsonIgnoreProperties(value = { "positions", "strand" }, allowSetters = true)
    private IntersticeAssembly ownerIntersticeAssembly;

    public Boolean isRight() {
        try {
            checkRight();
        } catch (PositionInSeveralAssemblyException | PositionHasSeveralSupplyException e) {
            return false;
        }

        return true;
    }

    public void checkRight() throws PositionInSeveralAssemblyException, PositionHasSeveralSupplyException {
        Long ownerAssemblyCount = Long.valueOf(0);
        if (isInCentralAssembly()) {
            ownerAssemblyCount++;
        }
        if (isInCoreAssembly()) {
            ownerAssemblyCount++;
        }
        if (isInIntersticeAssembly()) {
            ownerAssemblyCount++;
        }

        if (ownerAssemblyCount > Long.valueOf(1)) {
            throw new PositionInSeveralAssemblyException();
        }

        Long containedSupplyCount = Long.valueOf(0);
        if (isOfBangleSupply()) {
            containedSupplyCount++;
        }
        if (isOfCustomComponentSupply()) {
            containedSupplyCount++;
        }
        if (isOfElementSupply()) {
            containedSupplyCount++;
        }
        if (isOfOneStudySupply()) {
            containedSupplyCount++;
        }

        if (containedSupplyCount > Long.valueOf(1)) {
            throw new PositionHasSeveralSupplyException();
        }
    }

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

    public void setElementSupply(ElementSupply elementSupply) {
        if (isOfBangleSupply() || isOfCustomComponentSupply() || isOfOneStudySupply()) {
            (new PositionAlreadyHasSupplyException()).printStackTrace();
        }
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
        if (isOfCustomComponentSupply() || isOfElementSupply() || isOfOneStudySupply()) {
            (new PositionAlreadyHasSupplyException()).printStackTrace();
        }
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
        if (isOfBangleSupply() || isOfElementSupply() || isOfOneStudySupply()) {
            (new PositionAlreadyHasSupplyException()).printStackTrace();
        }
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
        if (isOfBangleSupply() || isOfCustomComponentSupply() || isOfElementSupply()) {
            (new PositionAlreadyHasSupplyException()).printStackTrace();
        }
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
        if (this.ownerCentralAssembly != null) {
            this.ownerCentralAssembly.setPosition(null);
        }
        if (centralAssembly != null) {
            centralAssembly.setPosition(this);
        }
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
        if (isInCentralAssembly() || isInIntersticeAssembly()) {
            (new PositionAlreadyInAssemblyException()).printStackTrace();
        }
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
        if (isInCentralAssembly() || isInCoreAssembly()) {
            (new PositionAlreadyInAssemblyException()).printStackTrace();
        }
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
