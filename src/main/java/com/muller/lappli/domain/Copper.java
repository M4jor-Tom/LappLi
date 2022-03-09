package com.muller.lappli.domain;

import com.muller.lappli.domain.abstracts.AbstractDomainObject;
import com.muller.lappli.domain.interfaces.Article;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Copper.
 */
@Entity
@Table(name = "copper")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Copper extends AbstractDomainObject<Copper> implements Article, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false, unique = true)
    private Long number;

    @NotNull
    @Column(name = "designation", nullable = false, unique = true)
    private String designation;

    public Copper() {
        super();
    }

    public Copper(Long number, String designation) {
        setNumber(number);
        setDesignation(designation);
    }

    public Copper(Copper copper) {
        this(Long.valueOf(copper.getNumber()), String.valueOf(copper.getDesignation()));
    }

    public Copper copy() {
        return new Copper(this).id(getId());
    }

    @Override
    public Long getArticleNumber() {
        return getNumber();
    }

    @Override
    public Copper getThis() {
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Copper id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return this.number;
    }

    public Copper number(Long number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Override
    public String getDesignation() {
        return this.designation;
    }

    public Copper designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Copper)) {
            return false;
        }
        return id != null && id.equals(((Copper) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Copper{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", designation='" + getDesignation() + "'" +
            "}";
    }
}
