package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.interfaces.AssemblableComponent;
import javax.persistence.MappedSuperclass;

/**
 * This class represents AbstractCableries which are assemblables
 */
@MappedSuperclass
public abstract class AbstractAssemblableAtom<T> extends AbstractCableAtom<T> implements AssemblableComponent {

    public AbstractAssemblableAtom() {
        super();
    }
}
