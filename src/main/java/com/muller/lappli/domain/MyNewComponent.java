package com.muller.lappli.domain;

import com.muller.lappli.domain.abstracts.AbstractDomainObject;
import com.muller.lappli.domain.enumeration.CylindricComponentKind;
import com.muller.lappli.domain.interfaces.Article;
import com.muller.lappli.domain.interfaces.CylindricComponent;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MyNewComponent.
 */
@Entity
@Table(name = "my_new_component")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MyNewComponent extends AbstractDomainObject<MyNewComponent> implements Serializable, CylindricComponent, Article {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "number", nullable = false, unique = true)
    private Long number;

    @NotNull
    @Column(name = "designation", nullable = false, unique = true)
    private String designation;

    @NotNull
    @Column(name = "data", nullable = false)
    private Double data;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getNumber() {
        return this.number;
    }

    public MyNewComponent number(Long number) {
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

    public MyNewComponent designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getData() {
        return this.data;
    }

    public MyNewComponent data(Double data) {
        this.setData(data);
        return this;
    }

    public void setData(Double data) {
        this.data = data;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyNewComponent)) {
            return false;
        }
        return getId() != null && getId().equals(((MyNewComponent) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MyNewComponent{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", designation='" + getDesignation() + "'" +
            ", data=" + getData() +
            "}";
    }

    @Override
    public MyNewComponent getThis() {
        return this;
    }

    @Override
    public Double getMilimeterDiameter() {
        // [TODO] Either calculate this from other fields,
        // [TODO] either store it by specifing it in jdl file
        return Double.NaN;
    }

    @Override
    public Double getGramPerMeterLinearMass() {
        // [TODO] Either calculate this from other fields,
        // [TODO] either store it by specifing it in jdl file
        return null;
    }

    @Override
    public Boolean isUtility() {
        // [TODO] Set to true if it's a component made for some
        // [TODO] Electrical/Thermal/Transfert purpose
        // [TODO] Set to false if it's a component made for
        // [TODO] Mecanical/Geometrical purposes only such as Bangles
        return null;
    }

    @Override
    public CylindricComponentKind getCylindricComponentKind() {
        return CylindricComponentKind.MY_NEW_COMPONENT;
    }

    @Override
    public Long getArticleNumber() {
        return getNumber();
    }
}
