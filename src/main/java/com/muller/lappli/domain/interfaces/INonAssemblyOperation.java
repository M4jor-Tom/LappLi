package com.muller.lappli.domain.interfaces;

/**
 * An operation which is not about assembling
 */
public interface INonAssemblyOperation<T extends INonAssemblyOperation<T>> extends INonCentralOperation<T> {}
