package com.muller.lappli.domain.interfaces;

/**
 * An operation which has an amount of
 * bobins to equip to be produced
 */
@FunctionalInterface
public interface BobinsCountOwnerOperation<T extends BobinsCountOwnerOperation<T>> {
    public Long getBobinsCount();
}
