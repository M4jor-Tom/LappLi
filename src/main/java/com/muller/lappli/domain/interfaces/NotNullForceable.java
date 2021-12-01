package com.muller.lappli.domain.interfaces;

public interface NotNullForceable<C extends NotNullForceable<C>> {
    C forceNotNull();
}
