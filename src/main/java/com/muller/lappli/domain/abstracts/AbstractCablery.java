package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.interfaces.CylindricComponent;
import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractCablery<T> extends AbstractDomainObject<T> implements CylindricComponent {}
