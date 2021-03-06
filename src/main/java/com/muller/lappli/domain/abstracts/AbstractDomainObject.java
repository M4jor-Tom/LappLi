package com.muller.lappli.domain.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.muller.lappli.domain.interfaces.IDomainObject;
import java.util.Optional;
import javax.persistence.*;
import javax.persistence.MappedSuperclass;

/**
 * This class represents Objects in the project's domain
 * @param T the type of the daughter class
 */
@MappedSuperclass
public abstract class AbstractDomainObject<T extends AbstractDomainObject<T>> implements IDomainObject<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public AbstractDomainObject() {
        super();
    }

    /**
     * @return true if the DomainObject has consistent data
     */
    @Override
    @JsonProperty("isConform")
    public Boolean isConform() {
        return true;
    }

    /**
     * @return an Optional containing
     * the class instance if isConform() == true
     */
    @JsonIgnore
    public Optional<T> getThisIfConform() {
        if (!isConform()) {
            return Optional.empty();
        }

        return Optional.of(getThis());
    }

    /**
     * @return the Id of the object in the database
     */
    public Long getId() {
        return this.id;
    }

    public T id(Long id) {
        this.setId(id);
        return getThis();
    }

    public void setId(Long id) {
        this.id = id;
    }
}
