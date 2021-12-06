package com.muller.lappli.domain.interfaces;

import java.time.Instant;

/**
 * This interface must be implemented into domain edition classes
 * to edit domain objects of the type parameter class
 */
public interface IEdition<C extends Commitable<C>> {
    /**
     * @return the Instant when the edition has been created
     */
    public Instant getEditionInstant();

    /**
     * Updates a domain object with the implementation fields
     *
     * @param commitable the domain object to be updated
     * @return the edited domain object
     */
    public C update(C commitable);
}
