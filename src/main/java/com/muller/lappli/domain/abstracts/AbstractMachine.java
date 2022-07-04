package com.muller.lappli.domain.abstracts;

import javax.persistence.*;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.*;

/**
 * A class representing machines used to produce cables
 */
@MappedSuperclass
public abstract class AbstractMachine<T extends AbstractMachine<T>> extends AbstractDomainObject<T> {

    /**
     * The index of a machine,
     * which will be used in its name.
     */
    @NotNull
    @Column(name = "jhi_index", nullable = false, unique = true)
    private Long index;

    public AbstractMachine() {
        super();
    }

    /**
     * A prefix is the begining of the name of
     * any machine of a kind
     *
     * @return a String prefix
     */
    protected abstract String getPrefix();

    /**
     * @return the full name of a machine,
     * begining by its prefix and ending by
     * its index
     */
    public String getName() {
        if (getIndex() == null) {
            return null;
        }

        String prefix = getIndex() >= 10 ? getPrefix() : getPrefix() + "0";

        return prefix + getIndex();
    }

    public Long getIndex() {
        return this.index;
    }

    public T index(Long index) {
        this.setIndex(index);
        return getThis();
    }

    public void setIndex(Long index) {
        this.index = index;
    }
}
