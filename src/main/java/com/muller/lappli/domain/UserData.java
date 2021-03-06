package com.muller.lappli.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.abstracts.AbstractDomainObject;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserData.
 * That class allows Users to store studies.
 * Other adds to the {@link com.muller.lappli.domain.User}
 * must be done here.
 */
@Entity
@Table(name = "user_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserData extends AbstractDomainObject<UserData> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne(optional = false)
    @NotNull
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @OneToMany(mappedBy = "author")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "strandSupplies" }, allowSetters = true)
    private Set<Study> studies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UserData() {
        super();
        setStudies(new HashSet<>());
    }

    @Override
    public UserData getThis() {
        return this;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public UserData id(Long id) {
        this.setId(id);
        return this;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserData user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Study> getStudies() {
        return this.studies;
    }

    public void setStudies(Set<Study> studies) {
        if (this.studies != null) {
            this.studies.forEach(i -> i.setAuthor(null));
        }
        if (studies != null) {
            studies.forEach(i -> i.setAuthor(this));
        }
        this.studies = studies;
    }

    public UserData studies(Set<Study> studies) {
        this.setStudies(studies);
        return this;
    }

    public UserData addStudies(Study study) {
        this.studies.add(study);
        study.setAuthor(this);
        return this;
    }

    public UserData removeStudies(Study study) {
        this.studies.remove(study);
        study.setAuthor(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserData)) {
            return false;
        }
        return id != null && id.equals(((UserData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserData{" +
            "id=" + getId() +
            "}";
    }
}
