package com.muller.lappli.domain.abstracts;

import javax.persistence.MappedSuperclass;

/**
 * This class represents AbstractCableries which are Atoms, unlike Composites
 */
@MappedSuperclass
public abstract class AbstractCableAtom<T> extends AbstractCablery<T> {

    public AbstractCableAtom() {}
}
