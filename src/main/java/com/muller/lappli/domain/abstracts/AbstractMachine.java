package com.muller.lappli.domain.abstracts;

import javax.persistence.*;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.*;

@MappedSuperclass
public abstract class AbstractMachine<T extends AbstractMachine<T>> extends AbstractDomainObject<T> {

    @NotNull
    @Column(name = "jhi_index", nullable = false, unique = true)
    private Long index;

    public AbstractMachine() {
        super();
    }

    protected abstract String getPrefix();

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
