package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.enumeration.MarkingTechnique;
import com.muller.lappli.domain.enumeration.MarkingType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ElementSupply.
 */
@Entity
@Table(name = "element_supply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ElementSupply implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Long UNITY_QUANTITY = Long.valueOf(1000);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "apparitions", nullable = false)
    private Long apparitions;

    @Column(name = "forced_marking")
    private String forcedMarking;

    @Enumerated(EnumType.STRING)
    @Column(name = "marking_type")
    private MarkingType markingType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "elementKind" }, allowSetters = true)
    private Element element;

    @Transient
    private List<Lifter> bestLifterList;

    public String getBestLiftersNames() {
        String names = "";

        for (Lifter lifter : getBestLifterList()) {
            names = names + lifter.getName() + " ";
        }

        return names;
    }

    public Long getQuantity() {
        return ElementSupply.UNITY_QUANTITY * getApparitions();
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ElementSupply id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApparitions() {
        return this.apparitions;
    }

    public ElementSupply apparitions(Long apparitions) {
        this.setApparitions(apparitions);
        return this;
    }

    public void setApparitions(Long apparitions) {
        this.apparitions = apparitions;
    }

    public String getForcedMarking() {
        return this.forcedMarking;
    }

    public ElementSupply forcedMarking(String forcedMarking) {
        this.setForcedMarking(forcedMarking);
        return this;
    }

    public void setForcedMarking(String forcedMarking) {
        this.forcedMarking = forcedMarking;
    }

    public MarkingType getMarkingType() {
        return this.markingType;
    }

    public ElementSupply markingType(MarkingType markingType) {
        this.setMarkingType(markingType);
        return this;
    }

    public void setMarkingType(MarkingType markingType) {
        this.markingType = markingType;
    }

    public Element getElement() {
        return this.element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public ElementSupply element(Element element) {
        this.setElement(element);
        return this;
    }

    public List<Lifter> getBestLifterList() {
        return bestLifterList;
    }

    public void setBestLifterList(List<Lifter> bestLifterList) {
        this.bestLifterList = bestLifterList;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ElementSupply)) {
            return false;
        }
        return id != null && id.equals(((ElementSupply) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ElementSupply{" +
            "id=" + getId() +
            ", apparitions=" + getApparitions() +
            ", forcedMarking='" + getForcedMarking() + "'" +
            ", markingType='" + getMarkingType() + "'" +
            "}";
    }
}
