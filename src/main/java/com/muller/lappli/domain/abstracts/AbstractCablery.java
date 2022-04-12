package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.interfaces.CylindricComponent;
import javax.persistence.*;

/**
 * This class represents AbstractDomainObjects which are CylindricComponents
 */
@MappedSuperclass
public abstract class AbstractCablery<T extends AbstractCablery<T>> extends AbstractDomainObject<T> implements CylindricComponent {

    public AbstractCablery() {
        super();
    }
}
