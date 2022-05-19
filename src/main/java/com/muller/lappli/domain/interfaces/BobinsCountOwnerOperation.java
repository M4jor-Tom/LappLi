package com.muller.lappli.domain.interfaces;

@FunctionalInterface
public interface BobinsCountOwnerOperation<T extends BobinsCountOwnerOperation<T>> {
    public Long getBobinsCount();
}
