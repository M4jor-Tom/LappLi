package com.muller.lappli.domain.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Optional;

/**
 * An object which can be stored in the database
 */
public interface IDomainObject<T extends IDomainObject<T>> {
    public Optional<T> getThisIfConform();

    /**
     * @return the Id of the object in the database
     */
    public Long getId();

    public T id(Long id);

    public void setId(Long id);

    /**
     * @return the object at its concretest state
     */
    @JsonIgnore
    public T getThis();

    public Boolean isConform();
}
