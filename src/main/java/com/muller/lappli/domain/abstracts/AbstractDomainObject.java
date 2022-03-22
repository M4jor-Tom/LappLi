package com.muller.lappli.domain.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.MappedSuperclass;

/**
 * This class represents Objects in the project's domain
 * @param T the type of the daughter class
 */
@MappedSuperclass
public abstract class AbstractDomainObject<T> {

    public AbstractDomainObject() {
        super();
    }

    /**
     * @return the object at its concretest state
     */
    @JsonIgnore
    public abstract T getThis();

    /**
     * @return the Id of the object in the database
     */
    public abstract Long getId();
}
