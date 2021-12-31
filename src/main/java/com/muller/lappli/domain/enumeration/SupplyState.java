package com.muller.lappli.domain.enumeration;

//TODO: Use that in Supply classes,
//create AssemblySupply interface,
//...

/**
 * The SupplyState enumeration.
 */
public enum SupplyState {
    //The Supply is in that state when not spread into a Strand
    UNDIVIED,

    //The Supply is in that state when spread into a Strand, but not into a Position
    DIVIDED_UNPLACED,

    //The supply is in that state when it has a Position
    PLACED,
}
