package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractDomainObject;
import com.muller.lappli.domain.abstracts.AbstractSupply;
import com.muller.lappli.domain.interfaces.ISupplyPositionOwner;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SupplyPosition.
 */
@Entity
@Table(name = "supply_position")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SupplyPosition extends AbstractDomainObject<SupplyPosition> implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "supply_apparitions_usage", nullable = false)
    private Long supplyApparitionsUsage;

    @JsonIgnoreProperties(value = { "ownerStrandSupply", "supplyPosition" }, allowSetters = true)
    @OneToOne(mappedBy = "supplyPosition")
    private CentralAssembly ownerCentralAssembly;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ownerSupplyPositions" }, allowSetters = true)
    private ElementSupply elementSupply;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ownerSupplyPositions" }, allowSetters = true)
    private BangleSupply bangleSupply;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ownerSupplyPositions" }, allowSetters = true)
    private CustomComponentSupply customComponentSupply;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ownerSupplyPositions" }, allowSetters = true)
    private OneStudySupply oneStudySupply;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ownerSupplyPositions" }, allowSetters = true)
    private StrandSupply strandSupply;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "ownerSupplyPositions",
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
            "centralAssembly",
            "study",
            "bangleSupplies",
            "elementSupplies",
            "customComponentSupplies",
            "oneStudySupplies",
        },
        allowSetters = true
    )
    private Strand ownerStrand;

    @ManyToOne
    @JsonIgnoreProperties(value = { "supplyPositions", "ownerStrandSupply" }, allowSetters = true)
    private IntersticeAssembly ownerIntersticeAssembly;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public SupplyPosition() {
        super();
    }

    @Override
    public SupplyPosition getThis() {
        return this;
    }

    @Override
    public Boolean isConform() {
        return getSupplyApparitionsUsage() != null && isOwnerLegit() && isSupplyLegit();
    }

    /**
     * Checks the SupplyPosition's owner data is'nt corrupted
     *
     * @return true if only 0 or 1 owner is set
     */
    public Boolean isOwnerLegit() {
        return DomainManager.trueCountIs0Or1(
            getOwnerCentralAssembly() != null,
            getOwnerStrand() != null,
            getOwnerIntersticeAssembly() != null
        );
    }

    /**
     * Checks the SupplyPosition's supply data is'nt corrupted
     *
     * @return true if only 0 or 1 supply is set
     */
    public Boolean isSupplyLegit() {
        return DomainManager.trueCountIs0Or1(
            getBangleSupply() != null,
            getCustomComponentSupply() != null,
            getElementSupply() != null,
            getOneStudySupply() != null
        );
    }

    /**
     * @return the SupplyPosition owner
     */
    @JsonIgnore/*Properties(
        value = {
            "supplyPosition",
            "supplyPositions",
            "coreAssemblies",
            "intersticeAssemblies",
            "sheathings",
            "centralAssembly",
            "futureStudy",
            "bangleSupplies",
            "elementSupplies",
            "customComponentSupplies",
            "oneStudySupplies",
        },
        allowSetters = true
    )*/
    public ISupplyPositionOwner getOwner() {
        if (getOwnerStrand() != null) {
            return getOwnerStrand();
        } else if (getOwnerCentralAssembly() != null) {
            return getOwnerCentralAssembly();
        } else if (getOwnerIntersticeAssembly() != null) {
            return getOwnerIntersticeAssembly();
        }

        return null;
    }

    /**
     * @return the owned Supply
     */
    public AbstractSupply<?> getSupply() {
        //[COMPONENT_KIND]
        if (getBangleSupply() != null) {
            return getBangleSupply();
        } else if (getCustomComponentSupply() != null) {
            return getCustomComponentSupply();
        } else if (getElementSupply() != null) {
            return getElementSupply();
        } else if (getOneStudySupply() != null) {
            return getOneStudySupply();
        }

        return null;
    }

    public SupplyPosition supply(AbstractSupply<?> supply) {
        setSupply(supply);
        return this;
    }

    @JsonIgnore
    public void setSupply(AbstractSupply<?> supply) {
        //[COMPONENT_KIND]
        setBangleSupply(null);
        setCustomComponentSupply(null);
        setElementSupply(null);
        setOneStudySupply(null);

        if (supply == null) {
            return;
        } else switch (supply.getCylindricComponent().getCylindricComponentKind()) {
            case BANGLE:
                setBangleSupply((BangleSupply) supply);
                break;
            case CUSTOM_COMPONENT:
                setCustomComponentSupply((CustomComponentSupply) supply);
                break;
            case ELEMENT:
                setElementSupply((ElementSupply) supply);
                break;
            case ONE_STUDY:
                setOneStudySupply((OneStudySupply) supply);
                break;
            default:
                break;
        }
    }

    public Long getSupplyApparitionsUsage() {
        return this.supplyApparitionsUsage;
    }

    public SupplyPosition supplyApparitionsUsage(Long supplyApparitionsUsage) {
        this.setSupplyApparitionsUsage(supplyApparitionsUsage);
        return this;
    }

    public void setSupplyApparitionsUsage(Long supplyApparitionsUsage) {
        this.supplyApparitionsUsage = supplyApparitionsUsage;
    }

    public CentralAssembly getOwnerCentralAssembly() {
        return this.ownerCentralAssembly;
    }

    public void setOwnerCentralAssembly(CentralAssembly centralAssembly) {
        if (this.ownerCentralAssembly != null) {
            this.ownerCentralAssembly.setSupplyPosition(null);
        }
        if (centralAssembly != null) {
            centralAssembly.setSupplyPosition(this);
        }
        this.ownerCentralAssembly = centralAssembly;
    }

    public SupplyPosition ownerCentralAssembly(CentralAssembly centralAssembly) {
        this.setOwnerCentralAssembly(centralAssembly);
        return this;
    }

    public ElementSupply getElementSupply() {
        return this.elementSupply;
    }

    public void setElementSupply(ElementSupply elementSupply) {
        this.elementSupply = elementSupply;
    }

    public SupplyPosition elementSupply(ElementSupply elementSupply) {
        this.setElementSupply(elementSupply);
        return this;
    }

    public BangleSupply getBangleSupply() {
        return this.bangleSupply;
    }

    public void setBangleSupply(BangleSupply bangleSupply) {
        this.bangleSupply = bangleSupply;
    }

    public SupplyPosition bangleSupply(BangleSupply bangleSupply) {
        this.setBangleSupply(bangleSupply);
        return this;
    }

    public CustomComponentSupply getCustomComponentSupply() {
        return this.customComponentSupply;
    }

    public void setCustomComponentSupply(CustomComponentSupply customComponentSupply) {
        this.customComponentSupply = customComponentSupply;
    }

    public SupplyPosition customComponentSupply(CustomComponentSupply customComponentSupply) {
        this.setCustomComponentSupply(customComponentSupply);
        return this;
    }

    public OneStudySupply getOneStudySupply() {
        return this.oneStudySupply;
    }

    public void setOneStudySupply(OneStudySupply oneStudySupply) {
        this.oneStudySupply = oneStudySupply;
    }

    public SupplyPosition oneStudySupply(OneStudySupply oneStudySupply) {
        this.setOneStudySupply(oneStudySupply);
        return this;
    }

    public StrandSupply getStrandSupply() {
        return this.strandSupply;
    }

    public void setStrandSupply(StrandSupply strandSupply) {
        this.strandSupply = strandSupply;
    }

    public SupplyPosition strandSupply(StrandSupply strandSupply) {
        this.setStrandSupply(strandSupply);
        return this;
    }

    public Strand getOwnerStrand() {
        return this.ownerStrand;
    }

    public void setOwnerStrand(Strand strand) {
        this.ownerStrand = strand;
    }

    public SupplyPosition ownerStrand(Strand strand) {
        this.setOwnerStrand(strand);
        return this;
    }

    public IntersticeAssembly getOwnerIntersticeAssembly() {
        return this.ownerIntersticeAssembly;
    }

    public void setOwnerIntersticeAssembly(IntersticeAssembly intersticeAssembly) {
        this.ownerIntersticeAssembly = intersticeAssembly;
    }

    public SupplyPosition ownerIntersticeAssembly(IntersticeAssembly intersticeAssembly) {
        this.setOwnerIntersticeAssembly(intersticeAssembly);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SupplyPosition)) {
            return false;
        }
        return getId() != null && getId().equals(((SupplyPosition) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupplyPosition{" +
            "id=" + getId() +
            ", supplyApparitionsUsage=" + getSupplyApparitionsUsage() +
            ", supply=" + getSupply() +
            "}";
    }
}
