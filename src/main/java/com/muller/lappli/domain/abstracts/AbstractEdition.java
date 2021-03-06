package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.interfaces.Commitable;
import java.time.Instant;
import javax.persistence.MappedSuperclass;

/**
 * This abstract class must be extends by domain edition classes
 * to edit domain objects of the type parameter class
 *
 * @param C the type of edited objects
 */
@MappedSuperclass
@Deprecated
public abstract class AbstractEdition<C extends Commitable<C>> extends AbstractDomainObject<AbstractEdition<C>> {

    /**
     * AbstractEdition constructor
     * Sets AbstractEdition.editionInstant to now
     */
    public AbstractEdition() {
        super();
        setEditionInstant(Instant.now());
    }

    @Override
    public Boolean isConform() {
        return true;
    }

    /**
     * @return the edited Commitable instance
     */
    public abstract C getEditedCommitable();

    /**
     * @return the Instant when the edition has been created
     */
    public abstract Instant getEditionInstant();

    /**
     * @param instant when the edition has been created
     */
    protected abstract void setEditionInstant(Instant instant);

    /**
     * Updates a domain object with the implementation fields
     *
     * @param commitable the domain object to be updated
     * @return the edited domain object
     */
    public abstract C update(C commitable);

    /**
     * Sets the edition time to now
     *
     * @return the edition entity as AbstractEdition
     */
    public AbstractEdition<C> nowEditionTime() {
        setEditionInstant(Instant.now());
        return this;
    }

    /**
     * Sets the edition time to Instant.EPOCH (1970-01-01 00:00:00)
     *
     * @return the edition entity as AbstractEdition
     */
    public AbstractEdition<C> epochEditionTime() {
        setEditionInstant(Instant.EPOCH);
        return this;
    }
}
