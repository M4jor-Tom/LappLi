package com.muller.lappli.domain;

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
public class MyNewComponent implements Serializable {

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

    @NotNull
    @Column(name = "data", nullable = false)
    private Double data;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MyNewComponent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        return id != null && id.equals(((MyNewComponent) o).id);
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
}
