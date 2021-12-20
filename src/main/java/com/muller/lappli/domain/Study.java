package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Study implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private Long number;

    @NotNull
    @Column(name = "creation_instant", nullable = false)
    private Instant creationInstant;

    @OneToMany(mappedBy = "study")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "study" }, allowSetters = true)
    private Set<StrandSupply> strandSupplies = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "studies" }, allowSetters = true)
    private UserData author;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Study id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Instant getCreationInstant() {
        return this.creationInstant;
    }

    public Study creationInstant(Instant creationInstant) {
        this.setCreationInstant(creationInstant);
        return this;
    }

    public void setCreationInstant(Instant creationInstant) {
        this.creationInstant = creationInstant;
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
        this.strandSupplies = strandSupplies;
    }

    public Study strandSupplies(Set<StrandSupply> strandSupplies) {
        this.setStrandSupplies(strandSupplies);
        return this;
    }

    public Study addStrandSupplies(StrandSupply strandSupply) {
        this.strandSupplies.add(strandSupply);
        strandSupply.setStudy(this);
        return this;
    }

    public Study removeStrandSupplies(StrandSupply strandSupply) {
        this.strandSupplies.remove(strandSupply);
        strandSupply.setStudy(null);
        return this;
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
        return id != null && id.equals(((Study) o).id);
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
            ", creationInstant='" + getCreationInstant() + "'" +
            "}";
    }
}
