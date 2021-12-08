package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractAssemblableAtom;
import com.muller.lappli.domain.enumeration.Color;
import com.muller.lappli.domain.interfaces.Article;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Element.
 */
@Entity
@Table(name = "element")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Element extends AbstractAssemblableAtom implements Article, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false, unique = true)
    private Long number;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "color", nullable = false)
    private Color color;

    @ManyToOne(optional = false)
    @NotNull
    //@JsonIgnoreProperties(value = { "copper"/*, "insulationMaterial"*/ }, allowSetters = true)
    private ElementKind elementKind;

    /*public Element() {
        this(null, Color.NONE, new ElementKind());
    }

    public Element(Long number, Color color, ElementKind elementKind) {
        setNumber(number);
        setColor(color);
        setElementKind(elementKind);
    }*/

    @Override
    public Double getMilimeterDiameter() {
        return getElementKind().getMilimeterDiameter();
    }

    @Override
    public Double getGramPerMeterLinearMass() {
        return getElementKind().getGramPerMeterLinearMass();
    }

    @Override
    public Long getArticleNumber() {
        return getNumber();
    }

    @Override
    public String getDesignation() {
        return getElementKind().getDesignation();
    }

    public String getNumberWithDesignationWithColor() {
        return getNumber() + " - " + getDesignationWithColor();
    }

    public String getDesignationWithColor() {
        return getElementKind().getDesignation() + " " + getColor().getDesignation();
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Element id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return this.number;
    }

    public Element number(Long number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Color getColor() {
        return this.color;
    }

    public Element color(Color color) {
        this.setColor(color);
        return this;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ElementKind getElementKind() {
        return this.elementKind;
    }

    public void setElementKind(ElementKind elementKind) {
        this.elementKind = elementKind;
    }

    public Element elementKind(ElementKind elementKind) {
        this.setElementKind(elementKind);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Element)) {
            return false;
        }
        return id != null && id.equals(((Element) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Element{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", color='" + getColor() + "'" +
            "}";
    }
}
