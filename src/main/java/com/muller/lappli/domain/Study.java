package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractDomainObject;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Study.
 */
@Entity
@Table(name = "study")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Study extends AbstractDomainObject<Study> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "number")
    private Long number;

    @NotNull
    @Column(name = "last_edition_instant", nullable = false)
    private Instant lastEditionInstant;

    @OneToMany(mappedBy = "futureStudy", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "supplyPositions", "coreAssemblies", "intersticeAssemblies", "sheathings", "centralAssembly", "futureStudy" },
        allowSetters = true
    )
    private Set<Strand> strands = new HashSet<>();

    @OneToMany(mappedBy = "study", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "study" }, allowSetters = true)
    private Set<StrandSupply> strandSupplies = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "studies" }, allowSetters = true)
    private UserData author;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public Study getThis() {
        return this;
    }

    public Study() {
        super();
        actualize();
        setAuthor(new UserData());
        setStrands(new HashSet<>());
        setStrandSupplies(new HashSet<>());
    }

    public Boolean isAuthored() {
        return getAuthor().getId() != null;
    }

    public Study actualize() {
        return lastEditionInstant(Instant.now());
    }

    public Long getNumber() {
        return this.number;
    }

    public Study number(Long number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Instant getLastEditionInstant() {
        return this.lastEditionInstant;
    }

    private Study lastEditionInstant(Instant lastEditionInstant) {
        this.setLastEditionInstant(lastEditionInstant);
        return this;
    }

    private void setLastEditionInstant(Instant lastEditionInstant) {
        this.lastEditionInstant = lastEditionInstant;
    }

    public Set<Strand> getStrands() {
        return this.strands;
    }

    public void setStrands(Set<Strand> strands) {
        if (this.strands != null) {
            this.strands.forEach(i -> i.setFutureStudy(null));
        }
        if (strands != null) {
            strands.forEach(i -> i.setFutureStudy(this));
        }
        this.strands = strands;
    }

    public Study strands(Set<Strand> strands) {
        this.setStrands(strands);
        return this;
    }

    public Study addStrands(Strand strand) {
        this.strands.add(strand);
        strand.setFutureStudy(this);
        return this;
    }

    public Study removeStrands(Strand strand) {
        this.strands.remove(strand);
        strand.setFutureStudy(null);
        return this;
    }

    public Set<StrandSupply> getStrandSupplies() {
        return this.strandSupplies;
    }

    public void setStrandSupplies(Set<StrandSupply> strandSupplies) {
        if (this.strandSupplies != null) {
            this.strandSupplies.forEach(i -> i.setStudy(null));
        }
        if (strandSupplies != null) {
            strandSupplies.forEach(i -> i.setStudy(this));
        }
        actualize().strandSupplies = strandSupplies;
    }

    public Study strandSupplies(Set<StrandSupply> strandSupplies) {
        this.setStrandSupplies(strandSupplies);
        return this;
    }

    public Study addStrandSupplies(StrandSupply strandSupply) {
        this.strandSupplies.add(strandSupply);
        strandSupply.setStudy(this);
        return actualize();
    }

    public Study removeStrandSupplies(StrandSupply strandSupply) {
        this.strandSupplies.remove(strandSupply);
        strandSupply.setStudy(null);
        return actualize();
    }

    public UserData getAuthor() {
        return this.author;
    }

    public void setAuthor(UserData userData) {
        this.author = userData;
    }

    public Study author(UserData userData) {
        this.setAuthor(userData);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Study)) {
            return false;
        }
        return getId() != null && getId().equals(((Study) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Study{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", lastEditionInstant='" + getLastEditionInstant() + "'" +
            "}";
    }
}
