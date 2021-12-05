package com.muller.lappli.domain.interfaces;

import java.time.Instant;

public interface IEdition<C extends Commitable<C>> {
    public Instant getEditionInstant();

    public C update(C commitable);
}
