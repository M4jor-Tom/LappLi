package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.interfaces.Assemblable;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractAssemblableAtom<T> extends AbstractCableAtom<T> implements Assemblable {}
