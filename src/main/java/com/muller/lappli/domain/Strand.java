package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.exception.NoIntersticeAvailableException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Strand.
 */
@Entity
@Table(name = "strand")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Strand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @OneToMany(mappedBy = "strand", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "strand" }, allowSetters = true)
    private Set<CoreAssembly> coreAssemblies = new HashSet<>();

    @OneToMany(mappedBy = "strand", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "strand" }, allowSetters = true)
    private Set<IntersticeAssembly> intersticialAssemblies = new HashSet<>();

    @OneToMany(mappedBy = "strand", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "strand" }, allowSetters = true)
    private Set<ElementSupply> elementSupplies = new HashSet<>();

    @OneToMany(mappedBy = "strand", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "strand" }, allowSetters = true)
    private Set<BangleSupply> bangleSupplies = new HashSet<>();

    @OneToMany(mappedBy = "strand", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "strand" }, allowSetters = true)
    private Set<CustomComponentSupply> customComponentSupplies = new HashSet<>();

    @OneToMany(mappedBy = "strand", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "strand" }, allowSetters = true)
    private Set<OneStudySupply> oneStudySupplies = new HashSet<>();

    @JsonIgnoreProperties(value = { "strand" }, allowSetters = true)
    @OneToOne(mappedBy = "strand")
    private CentralAssembly centralAssembly;

    public CoreAssembly getLastCoreAssembly() {
        CoreAssembly lastCoreAssembly = null;

        for (CoreAssembly coreAssembly : getCoreAssemblies()) {
            lastCoreAssembly = coreAssembly;
        }

        return lastCoreAssembly;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Strand id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return "[DESIGNATION OF " + getId() + "]";
    }

    public Set<CoreAssembly> getCoreAssemblies() {
        return this.coreAssemblies;
    }

    public void setCoreAssemblies(Set<CoreAssembly> coreAssemblies) {
        if (this.coreAssemblies != null) {
            this.coreAssemblies.forEach(i -> i.setStrand(null));
        }
        if (coreAssemblies != null) {
            coreAssemblies.forEach(i -> i.setStrand(this));
        }
        this.coreAssemblies = coreAssemblies;
    }

    public Strand coreAssemblies(Set<CoreAssembly> coreAssemblies) {
        this.setCoreAssemblies(coreAssemblies);
        return this;
    }

    public Strand addCoreAssemblies(CoreAssembly coreAssembly) {
        this.coreAssemblies.add(coreAssembly);
        coreAssembly.setStrand(this);
        return this;
    }

    public Strand removeCoreAssemblies(CoreAssembly coreAssembly) {
        this.coreAssemblies.remove(coreAssembly);
        coreAssembly.setStrand(null);
        return this;
    }

    public Set<IntersticeAssembly> getIntersticialAssemblies() {
        return this.intersticialAssemblies;
    }

    public void setIntersticialAssemblies(Set<IntersticeAssembly> intersticeAssemblies) throws NoIntersticeAvailableException {
        if (getCoreAssemblies() == null) {
            throw new NoIntersticeAvailableException("No CoreAssembly found in strand");
        }
        if (this.intersticialAssemblies != null) {
            this.intersticialAssemblies.forEach(i -> i.setStrand(null));
        }
        if (intersticeAssemblies != null) {
            intersticeAssemblies.forEach(i -> i.setStrand(this));
        }
        this.intersticialAssemblies = intersticeAssemblies;
    }

    public Strand intersticialAssemblies(Set<IntersticeAssembly> intersticeAssemblies) throws NoIntersticeAvailableException {
        this.setIntersticialAssemblies(intersticeAssemblies);
        return this;
    }

    public Strand addIntersticialAssemblies(IntersticeAssembly intersticeAssembly) {
        this.intersticialAssemblies.add(intersticeAssembly);
        intersticeAssembly.setStrand(this);
        return this;
    }

    public Strand removeIntersticialAssemblies(IntersticeAssembly intersticeAssembly) {
        this.intersticialAssemblies.remove(intersticeAssembly);
        intersticeAssembly.setStrand(null);
        return this;
    }

    public Set<ElementSupply> getElementSupplies() {
        return this.elementSupplies;
    }

    public void setElementSupplies(Set<ElementSupply> elementSupplies) {
        if (this.elementSupplies != null) {
            this.elementSupplies.forEach(i -> i.setStrand(null));
        }
        if (elementSupplies != null) {
            elementSupplies.forEach(i -> i.setStrand(this));
        }
        this.elementSupplies = elementSupplies;
    }

    public Strand elementSupplies(Set<ElementSupply> elementSupplies) {
        this.setElementSupplies(elementSupplies);
        return this;
    }

    public Strand addElementSupplies(ElementSupply elementSupply) {
        this.elementSupplies.add(elementSupply);
        elementSupply.setStrand(this);
        return this;
    }

    public Strand removeElementSupplies(ElementSupply elementSupply) {
        this.elementSupplies.remove(elementSupply);
        elementSupply.setStrand(null);
        return this;
    }

    public Set<BangleSupply> getBangleSupplies() {
        return this.bangleSupplies;
    }

    public void setBangleSupplies(Set<BangleSupply> bangleSupplies) {
        if (this.bangleSupplies != null) {
            this.bangleSupplies.forEach(i -> i.setStrand(null));
        }
        if (bangleSupplies != null) {
            bangleSupplies.forEach(i -> i.setStrand(this));
        }
        this.bangleSupplies = bangleSupplies;
    }

    public Strand bangleSupplies(Set<BangleSupply> bangleSupplies) {
        this.setBangleSupplies(bangleSupplies);
        return this;
    }

    public Strand addBangleSupplies(BangleSupply bangleSupply) {
        this.bangleSupplies.add(bangleSupply);
        bangleSupply.setStrand(this);
        return this;
    }

    public Strand removeBangleSupplies(BangleSupply bangleSupply) {
        this.bangleSupplies.remove(bangleSupply);
        bangleSupply.setStrand(null);
        return this;
    }

    public Set<CustomComponentSupply> getCustomComponentSupplies() {
        return this.customComponentSupplies;
    }

    public void setCustomComponentSupplies(Set<CustomComponentSupply> customComponentSupplies) {
        if (this.customComponentSupplies != null) {
            this.customComponentSupplies.forEach(i -> i.setStrand(null));
        }
        if (customComponentSupplies != null) {
            customComponentSupplies.forEach(i -> i.setStrand(this));
        }
        this.customComponentSupplies = customComponentSupplies;
    }

    public Strand customComponentSupplies(Set<CustomComponentSupply> customComponentSupplies) {
        this.setCustomComponentSupplies(customComponentSupplies);
        return this;
    }

    public Strand addCustomComponentSupplies(CustomComponentSupply customComponentSupply) {
        this.customComponentSupplies.add(customComponentSupply);
        customComponentSupply.setStrand(this);
        return this;
    }

    public Strand removeCustomComponentSupplies(CustomComponentSupply customComponentSupply) {
        this.customComponentSupplies.remove(customComponentSupply);
        customComponentSupply.setStrand(null);
        return this;
    }

    public Set<OneStudySupply> getOneStudySupplies() {
        return this.oneStudySupplies;
    }

    public void setOneStudySupplies(Set<OneStudySupply> oneStudySupplies) {
        if (this.oneStudySupplies != null) {
            this.oneStudySupplies.forEach(i -> i.setStrand(null));
        }
        if (oneStudySupplies != null) {
            oneStudySupplies.forEach(i -> i.setStrand(this));
        }
        this.oneStudySupplies = oneStudySupplies;
    }

    public Strand oneStudySupplies(Set<OneStudySupply> oneStudySupplies) {
        this.setOneStudySupplies(oneStudySupplies);
        return this;
    }

    public Strand addOneStudySupplies(OneStudySupply oneStudySupply) {
        this.oneStudySupplies.add(oneStudySupply);
        oneStudySupply.setStrand(this);
        return this;
    }

    public Strand removeOneStudySupplies(OneStudySupply oneStudySupply) {
        this.oneStudySupplies.remove(oneStudySupply);
        oneStudySupply.setStrand(null);
        return this;
    }

    public CentralAssembly getCentralAssembly() {
        return this.centralAssembly;
    }

    public void setCentralAssembly(CentralAssembly centralAssembly) {
        if (this.centralAssembly != null) {
            this.centralAssembly.setStrand(null);
        }
        if (centralAssembly != null) {
            centralAssembly.setStrand(this);
        }
        this.centralAssembly = centralAssembly;
    }

    public Strand centralAssembly(CentralAssembly centralAssembly) {
        this.setCentralAssembly(centralAssembly);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Strand)) {
            return false;
        }
        return id != null && id.equals(((Strand) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Strand{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            "}";
    }
}
